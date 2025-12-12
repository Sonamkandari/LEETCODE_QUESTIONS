/* icg_long_working.c
   Intermediate Code Generator (working, long version)

   Features:
   - Tokenizes identifiers (multichar), integers (multidigit), operators + - * /, parentheses, assignment '='
   - Uses shunting-yard to convert infix -> postfix
   - Generates:
       * Three-address code (TAC) as temporaries t1, t2, ...
       * Quadruple table (op, arg1, arg2, result)
       * Triple table (indexed triples). When printing triple args that refer to temporaries produced by previous triples,
         they are shown as (index).
   - No dynamic memory (uses fixed arrays); portable C (C89/C99 compatible).
*/

#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX_TOKEN 512
#define MAX_TOKLEN 64
#define MAX_POSTFIX 512
#define MAX_QUADS 1024
#define MAX_TRIPLES 1024
#define MAX_STACK 1024

/* Token types */
typedef enum { TOK_ID, TOK_NUM, TOK_OP, TOK_LPAREN, TOK_RPAREN, TOK_ASSIGN, TOK_END } TokType;

typedef struct {
    TokType type;
    char text[MAX_TOKLEN];
} Token;

/* Quadruple */
typedef struct {
    char op[8];
    char arg1[64];
    char arg2[64];
    char result[64];
} Quad;

/* Triple */
typedef struct {
    char op[8];
    char arg1[64];
    char arg2[64];
} Triple;

/* Globals */
Token tokens[MAX_TOKEN];
int token_count = 0;

char postfix[MAX_POSTFIX][MAX_TOKLEN];
int postfix_len = 0;

Quad quads[MAX_QUADS];
int qcount = 0;

Triple triples[MAX_TRIPLES];
int tcount = 0;

/* mapping temp name -> triple index (for printing triple references) */
typedef struct { char name[64]; int idx; } TempMap;
TempMap tempmap[MAX_TRIPLES];
int tempmap_count = 0;

/* generate temp name: t1, t2, ... (stored into buffer) */
void make_temp(char *buf, int buflen) {
    static int tc = 1;
    snprintf(buf, buflen, "t%d", tc++);
}

/* record map from temp name to triple index */
void record_tempmap(const char *temp, int tri_idx) {
    if (tempmap_count < MAX_TRIPLES) {
        strncpy(tempmap[tempmap_count].name, temp, sizeof(tempmap[tempmap_count].name)-1);
        tempmap[tempmap_count].idx = tri_idx;
        tempmap_count++;
    }
}

/* find triple index for a temp name, or -1 */
int find_tempmap(const char *name) {
    int i;
    for (i = 0; i < tempmap_count; ++i) {
        if (strcmp(tempmap[i].name, name) == 0) return tempmap[i].idx;
    }
    return -1;
}

/* tokenizer: fills tokens[] and returns count */
void tokenize(const char *s) {
    int i = 0, pos = 0;
    token_count = 0;
    while (s[pos]) {
        if (isspace((unsigned char)s[pos])) { pos++; continue; }
        if (isalpha((unsigned char)s[pos]) || s[pos] == '_') {
            int j = 0;
            while ((isalnum((unsigned char)s[pos]) || s[pos] == '_') && j < MAX_TOKLEN-1) {
                tokens[token_count].text[j++] = s[pos++];
            }
            tokens[token_count].text[j] = '\0';
            tokens[token_count].type = TOK_ID;
            token_count++;
            continue;
        }
        if (isdigit((unsigned char)s[pos])) {
            int j = 0;
            while (isdigit((unsigned char)s[pos]) && j < MAX_TOKLEN-1) {
                tokens[token_count].text[j++] = s[pos++];
            }
            tokens[token_count].text[j] = '\0';
            tokens[token_count].type = TOK_NUM;
            token_count++;
            continue;
        }
        /* operators and punctuation */
        char c = s[pos++];
        if (c == '(') { tokens[token_count].type = TOK_LPAREN; strcpy(tokens[token_count].text, "("); token_count++; continue; }
        if (c == ')') { tokens[token_count].type = TOK_RPAREN; strcpy(tokens[token_count].text, ")"); token_count++; continue; }
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            tokens[token_count].type = TOK_OP;
            tokens[token_count].text[0] = c; tokens[token_count].text[1] = '\0';
            token_count++; continue;
        }
        if (c == '=') { tokens[token_count].type = TOK_ASSIGN; strcpy(tokens[token_count].text, "="); token_count++; continue; }
        /* unknown char -> skip */
    }
    tokens[token_count].type = TOK_END;
    tokens[token_count].text[0] = '\0';
}

/* operator precedence */
int prec_op(const char *op) {
    if (strcmp(op, "+") == 0 || strcmp(op, "-") == 0) return 1;
    if (strcmp(op, "*") == 0 || strcmp(op, "/") == 0) return 2;
    return 0;
}

/* shunting yard: convert tokens[start..] into postfix[]; returns postfix_len or -1 on error */
int shunting_yard(int start) {
    Token opstack[MAX_STACK];
    int op_top = 0; /* op_top is count (0 = empty) */
    int outi = 0;
    int pos = start;

    while (tokens[pos].type != TOK_END) {
        Token tk = tokens[pos];
        if (tk.type == TOK_ID || tk.type == TOK_NUM) {
            strncpy(postfix[outi++], tk.text, MAX_TOKLEN-1);
        } else if (tk.type == TOK_OP) {
            while (op_top > 0 && opstack[op_top-1].type == TOK_OP) {
                /* left associative */
                if (prec_op(opstack[op_top-1].text) >= prec_op(tk.text)) {
                    strncpy(postfix[outi++], opstack[--op_top].text, MAX_TOKLEN-1);
                } else break;
            }
            opstack[op_top++] = tk;
        } else if (tk.type == TOK_LPAREN) {
            opstack[op_top++] = tk;
        } else if (tk.type == TOK_RPAREN) {
            int found = 0;
            while (op_top > 0) {
                Token top = opstack[--op_top];
                if (top.type == TOK_LPAREN) { found = 1; break; }
                strncpy(postfix[outi++], top.text, MAX_TOKLEN-1);
            }
            if (!found) { fprintf(stderr, "Error: mismatched parentheses\n"); return -1; }
        } else {
            /* stop at assignment or end of input */
            break;
        }
        pos++;
        if (outi >= MAX_POSTFIX - 1) { fprintf(stderr, "Error: postfix overflow\n"); return -1; }
    }

    while (op_top > 0) {
        Token top = opstack[--op_top];
        if (top.type == TOK_LPAREN || top.type == TOK_RPAREN) { fprintf(stderr, "Error: mismatched parentheses\n"); return -1; }
        strncpy(postfix[outi++], top.text, MAX_TOKLEN-1);
    }
    postfix[outi][0] = '\0';
    postfix_len = outi;
    return outi;
}

/* generate code from postfix tokens; assign_to may be NULL or target identifier */
void gen_from_postfix(const char *assign_to) {
    char stack[MAX_STACK][64];
    int top = 0;

    int i;
    for (i = 0; i < postfix_len; ++i) {
        char *tk = postfix[i];
        if (tk[0] == 0) break;
        if ((tk[0] == '+' || tk[0] == '-' || tk[0] == '*' || tk[0] == '/') && tk[1] == '\0') {
            if (top < 2) { fprintf(stderr, "Error: insufficient operands for operator '%s'\n", tk); return; }
            char right[64], left[64];
            strncpy(right, stack[--top], sizeof(right)-1); right[sizeof(right)-1]=0;
            strncpy(left,  stack[--top], sizeof(left)-1);  left[sizeof(left)-1]=0;

            char temp[64]; make_temp(temp, sizeof(temp));

            /* Emit quadruple */
            if (qcount < MAX_QUADS) {
                strncpy(quads[qcount].op, tk, sizeof(quads[qcount].op)-1);
                strncpy(quads[qcount].arg1, left, sizeof(quads[qcount].arg1)-1);
                strncpy(quads[qcount].arg2, right, sizeof(quads[qcount].arg2)-1);
                strncpy(quads[qcount].result, temp, sizeof(quads[qcount].result)-1);
                qcount++;
            } else {
                fprintf(stderr, "Error: quad overflow\n"); return;
            }

            /* Emit triple and record mapping temp->triple index */
            if (tcount < MAX_TRIPLES) {
                strncpy(triples[tcount].op, tk, sizeof(triples[tcount].op)-1);
                strncpy(triples[tcount].arg1, left, sizeof(triples[tcount].arg1)-1);
                strncpy(triples[tcount].arg2, right, sizeof(triples[tcount].arg2)-1);
                record_tempmap(temp, tcount);
                tcount++;
            } else {
                fprintf(stderr, "Error: triple overflow\n"); return;
            }

            /* push temp */
            strncpy(stack[top++], temp, sizeof(stack[top-1])-1);
        } else {
            /* operand: push as-is */
            strncpy(stack[top++], tk, sizeof(stack[top-1])-1);
        }
    }

    if (top != 1) {
        fprintf(stderr, "Error: postfix evaluation ended with %d items (expected 1)\n", top);
        return;
    }
    char result[64];
    strncpy(result, stack[0], sizeof(result)-1);

    /* If assignment target provided, emit final assignment quad & triple */
    if (assign_to && assign_to[0]) {
        if (qcount < MAX_QUADS) {
            strncpy(quads[qcount].op, "=", sizeof(quads[qcount].op)-1);
            strncpy(quads[qcount].arg1, result, sizeof(quads[qcount].arg1)-1);
            quads[qcount].arg2[0] = '\0';
            strncpy(quads[qcount].result, assign_to, sizeof(quads[qcount].result)-1);
            qcount++;
        }
        if (tcount < MAX_TRIPLES) {
            strncpy(triples[tcount].op, "=", sizeof(triples[tcount].op)-1);
            strncpy(triples[tcount].arg1, result, sizeof(triples[tcount].arg1)-1);
            triples[tcount].arg2[0] = '\0';
            tcount++;
        }
    }
}

/* printing functions */
void print_TAC_from_quads() {
    int i;
    printf("\n--- Three Address Code (TAC) ---\n");
    for (i = 0; i < qcount; ++i) {
        if (strcmp(quads[i].op, "=") == 0) {
            printf("%s = %s\n", quads[i].result, quads[i].arg1);
        } else {
            printf("%s = %s %s %s\n", quads[i].result, quads[i].arg1, quads[i].op, quads[i].arg2);
        }
    }
}

void print_quads() {
    int i;
    printf("\n--- Quadruples ---\n");
    printf("Idx\tOp\tArg1\tArg2\tResult\n");
    for (i = 0; i < qcount; ++i) {
        printf("%d\t%s\t%s\t%s\t%s\n", i+1,
               quads[i].op[0] ? quads[i].op : "-",
               quads[i].arg1[0] ? quads[i].arg1 : "-",
               quads[i].arg2[0] ? quads[i].arg2 : "-",
               quads[i].result[0] ? quads[i].result : "-");
    }
}

void print_triples() {
    int i;
    printf("\n--- Triples ---\n");
    printf("Idx\tOp\tArg1\tArg2\n");
    for (i = 0; i < tcount; ++i) {
        char a1[128], a2[128];
        int idx;
        /* if arg1 is a temp produced by a triple, show (index) */
        idx = find_tempmap(triples[i].arg1);
        if (idx >= 0) snprintf(a1, sizeof(a1), "(%d)", idx+1); else snprintf(a1, sizeof(a1), "%s", triples[i].arg1[0] ? triples[i].arg1 : "-");
        idx = find_tempmap(triples[i].arg2);
        if (idx >= 0) snprintf(a2, sizeof(a2), "(%d)", idx+1); else snprintf(a2, sizeof(a2), "%s", triples[i].arg2[0] ? triples[i].arg2 : "-");
        printf("%d\t%s\t%s\t%s\n", i+1, triples[i].op[0] ? triples[i].op : "-", a1, a2);
    }
}

/* trim leading/trailing whitespace */
void trim(char *s) {
    int a = 0, b = (int)strlen(s)-1;
    while (s[a] && isspace((unsigned char)s[a])) a++;
    while (b >= a && isspace((unsigned char)s[b])) b--;
    int j = 0;
    for ( ; a <= b; ++a) s[j++] = s[a];
    s[j] = '\0';
}

int main(void) {
    char line[1024];
    printf("Enter an assignment or expression (e.g. a = b + 3*(c - d) ):\n> ");
    if (!fgets(line, sizeof(line), stdin)) return 0;
    /* remove newline and trim */
    line[strcspn(line, "\n")] = '\0';
    trim(line);
    if (line[0] == '\0') return 0;

    /* tokenize */
    tokenize(line);

    /* find assignment token if any */
    int assign_pos = -1;
    int i;
    for (i = 0; i < token_count; ++i) {
        if (tokens[i].type == TOK_ASSIGN) { assign_pos = i; break; }
    }

    char target[128];
    target[0] = '\0';
    int expr_start = 0;
    if (assign_pos > 0) {
        /* take first ID before '=' as assignment target (trim spaces handled by tokenizer) */
        int found = 0;
        for (i = 0; i < assign_pos; ++i) {
            if (tokens[i].type == TOK_ID) { strncpy(target, tokens[i].text, sizeof(target)-1); found = 1; break; }
        }
        if (!found) { fprintf(stderr, "Error: invalid assignment target\n"); return 1; }
        expr_start = assign_pos + 1;
    } else {
        expr_start = 0;
    }

    /* convert to postfix */
    if (shunting_yard(expr_start) < 0) return 1;

    /* generate code from postfix */
    gen_from_postfix(target[0] ? target : NULL);

    /* print results */
    print_TAC_from_quads();
    print_quads();
    print_triples();

    return 0;
}
