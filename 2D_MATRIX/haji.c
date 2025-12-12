/* mini_compiler_arith.c

Simple mini-compiler for arithmetic expressions in C.
- Lexical analysis (tokens: numbers, identifiers, + - * / ( ) )
- Recursive-descent parser building an AST
- Intermediate three-address code (TAC) generation

Build: gcc -std=c99 -O2 -o mini_compiler_arith mini_compiler_arith.c
Run:   ./mini_compiler_arith

Notes:
- This is a learning tool; error handling is simple but informative.
- Variables (identifiers) are treated as symbols and used directly in TAC.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

/* ---------- Lexer ---------- */
typedef enum {
    T_EOF, T_NUMBER, T_ID,
    T_PLUS, T_MINUS, T_TIMES, T_DIV,
    T_LPAREN, T_RPAREN,
    T_UNKNOWN
} TokenType;

typedef struct {
    TokenType type;
    char *lexeme;    // for IDs and numbers (string form)
    int value;       // numeric value if NUMBER
    int pos;
} Token;

char *input = NULL;
int input_pos = 0;
int input_len = 0;

Token *make_token(TokenType t, const char *lex, int val, int pos) {
    Token *tk = malloc(sizeof(Token));
    tk->type = t;
    tk->pos = pos;
    if (lex) tk->lexeme = strdup(lex); else tk->lexeme = NULL;
    tk->value = val;
    return tk;
}

void free_token(Token *t) {
    if (!t) return;
    if (t->lexeme) free(t->lexeme);
    free(t);
}

Token *next_token() {
    while (input_pos < input_len && isspace((unsigned char)input[input_pos])) input_pos++;
    if (input_pos >= input_len) return make_token(T_EOF, "", 0, input_pos);
    int start = input_pos;
    char c = input[input_pos];
    if (isdigit((unsigned char)c)) {
        long val = 0;
        int i = 0;
        char buf[64];
        while (input_pos < input_len && isdigit((unsigned char)input[input_pos])) {
            if (i < (int)sizeof(buf)-1) buf[i++] = input[input_pos];
            val = val*10 + (input[input_pos]-'0');
            input_pos++;
        }
        buf[i] = '\0';
        return make_token(T_NUMBER, buf, (int)val, start);
    }
    if (isalpha((unsigned char)c) || c == '_') {
        int i = 0; char buf[128];
        while (input_pos < input_len && (isalnum((unsigned char)input[input_pos]) || input[input_pos]=='_')) {
            if (i < (int)sizeof(buf)-1) buf[i++] = input[input_pos];
            input_pos++;
        }
        buf[i] = '\0';
        return make_token(T_ID, buf, 0, start);
    }
    input_pos++;
    switch (c) {
        case '+': return make_token(T_PLUS, "+", 0, start);
        case '-': return make_token(T_MINUS, "-", 0, start);
        case '*': return make_token(T_TIMES, "*", 0, start);
        case '/': return make_token(T_DIV, "/", 0, start);
        case '(' : return make_token(T_LPAREN, "(", 0, start);
        case ')' : return make_token(T_RPAREN, ")", 0, start);
        default:
            {
                char s[2] = {c, '\0'};
                return make_token(T_UNKNOWN, s, 0, start);
            }
    }
}

/* ---------- AST ---------- */
typedef enum { N_NUMBER, N_VAR, N_BINOP, N_UNOP } NodeKind;

typedef struct AST {
    NodeKind kind;
    union {
        int number;      // for N_NUMBER
        char *name;      // for N_VAR
        struct { struct AST *left; char op; struct AST *right; } binop; // N_BINOP
        struct { char op; struct AST *node; } unop; // N_UNOP
    } u;
} AST;

AST *new_number(int v) {
    AST *n = malloc(sizeof(AST)); n->kind = N_NUMBER; n->u.number = v; return n;
}
AST *new_var(const char *name) {
    AST *n = malloc(sizeof(AST)); n->kind = N_VAR; n->u.name = strdup(name); return n;
}
AST *new_binop(AST *l, char op, AST *r) {
    AST *n = malloc(sizeof(AST)); n->kind = N_BINOP; n->u.binop.left = l; n->u.binop.op = op; n->u.binop.right = r; return n;
}
AST *new_unop(char op, AST *node) {
    AST *n = malloc(sizeof(AST)); n->kind = N_UNOP; n->u.unop.op = op; n->u.unop.node = node; return n;
}

void free_ast(AST *t) {
    if (!t) return;
    if (t->kind == N_VAR) free(t->u.name);
    if (t->kind == N_BINOP) { free_ast(t->u.binop.left); free_ast(t->u.binop.right); }
    if (t->kind == N_UNOP) { free_ast(t->u.unop.node); }
    free(t);
}

/* ---------- Parser (recursive-descent) ---------- */
Token *curtok = NULL;
Token *peektok = NULL;

void advance() {
    if (curtok) free_token(curtok);
    curtok = peektok;
    peektok = next_token();
}

void expect(TokenType t) {
    if (curtok->type != t) {
        fprintf(stderr, "Parse error: expected token %d at pos %d but got %d ('%s')\n", t, curtok->pos, curtok->type, curtok->lexeme ? curtok->lexeme : "");
        exit(1);
    }
}

AST *parse_expr();
AST *parse_term();
AST *parse_factor();

AST *parse_expr() {
    AST *node = parse_term();
    while (curtok->type == T_PLUS || curtok->type == T_MINUS) {
        char op = (curtok->type == T_PLUS) ? '+' : '-';
        advance();
        AST *rhs = parse_term();
        node = new_binop(node, op, rhs);
    }
    return node;
}

AST *parse_term() {
    AST *node = parse_factor();
    while (curtok->type == T_TIMES || curtok->type == T_DIV) {
        char op = (curtok->type == T_TIMES) ? '*' : '/';
        advance();
        AST *rhs = parse_factor();
        node = new_binop(node, op, rhs);
    }
    return node;
}

AST *parse_factor() {
    if (curtok->type == T_PLUS) {
        advance();
        AST *node = parse_factor();
        return new_unop('+', node);
    }
    if (curtok->type == T_MINUS) {
        advance();
        AST *node = parse_factor();
        return new_unop('-', node);
    }
    if (curtok->type == T_NUMBER) {
        int v = curtok->value;
        // lexeme also contains string; we use value
        advance();
        return new_number(v);
    }
    if (curtok->type == T_ID) {
        char *name = strdup(curtok->lexeme);
        advance();
        AST *n = new_var(name);
        free(name);
        return n;
    }
    if (curtok->type == T_LPAREN) {
        advance();
        AST *node = parse_expr();
        if (curtok->type != T_RPAREN) {
            fprintf(stderr, "Parse error: missing ')' at pos %d\n", curtok->pos);
            exit(1);
        }
        advance();
        return node;
    }
    fprintf(stderr, "Parse error: unexpected token '%s' at pos %d\n", curtok->lexeme ? curtok->lexeme : "", curtok->pos);
    exit(1);
}

/* ---------- Pretty print AST ---------- */
void print_ast(AST *t, int indent) {
    for (int i=0;i<indent;i++) putchar(' ');
    if (!t) { printf("<null>\n"); return; }
    switch (t->kind) {
        case N_NUMBER: printf("Number(%d)\n", t->u.number); break;
        case N_VAR: printf("Var(%s)\n", t->u.name); break;
        case N_UNOP: printf("UnaryOp(%c)\n", t->u.unop.op); print_ast(t->u.unop.node, indent+2); break;
        case N_BINOP: printf("BinOp(%c)\n", t->u.binop.op); print_ast(t->u.binop.left, indent+2); print_ast(t->u.binop.right, indent+2); break;
    }
}

/* ---------- TAC Generation ---------- */
typedef struct {
    char dst[64];
    char op[8]; // "=", or operator in middle
    char a[64];
    char b[64];
    int is_bin; // 1 if binary, 0 if assignment
} Instr;

Instr *instrs = NULL;
int instr_count = 0;
int instr_capacity = 0;

void emit_bin(const char *dst, const char *a, char op, const char *b) {
    if (instr_count >= instr_capacity) {
        instr_capacity = instr_capacity ? instr_capacity*2 : 64;
        instrs = realloc(instrs, sizeof(Instr)*instr_capacity);
    }
    Instr *ins = &instrs[instr_count++];
    strcpy(ins->dst, dst);
    sprintf(ins->op, "%c", op);
    strcpy(ins->a, a);
    strcpy(ins->b, b);
    ins->is_bin = 1;
}

void emit_assign(const char *dst, const char *val) {
    if (instr_count >= instr_capacity) {
        instr_capacity = instr_capacity ? instr_capacity*2 : 64;
        instrs = realloc(instrs, sizeof(Instr)*instr_capacity);
    }
    Instr *ins = &instrs[instr_count++];
    strcpy(ins->dst, dst);
    strcpy(ins->op, "=");
    strcpy(ins->a, val);
    ins->b[0] = '\0';
    ins->is_bin = 0;
}

int temp_counter = 0;
char *make_temp() {
    char buf[32];
    temp_counter++;
    snprintf(buf, sizeof(buf), "t%d", temp_counter);
    return strdup(buf);
}

// gen returns a string (heap allocated) representing where the result is stored (temp or var name)
char *gen_tac(AST *t) {
    if (!t) return strdup("<err>");
    if (t->kind == N_NUMBER) {
        char buf[64]; snprintf(buf, sizeof(buf), "%d", t->u.number);
        char *tmp = make_temp();
        emit_assign(tmp, buf);
        return tmp;
    }
    if (t->kind == N_VAR) {
        return strdup(t->u.name);
    }
    if (t->kind == N_UNOP) {
        char *operand = gen_tac(t->u.unop.node);
        char *tmp = make_temp();
        if (t->u.unop.op == '-') {
            // tmp = 0 - operand  (to represent unary minus)
            emit_bin(tmp, "0", '-', operand);
        } else {
            // unary plus: tmp = operand
            emit_assign(tmp, operand);
        }
        free(operand);
        return tmp;
    }
    if (t->kind == N_BINOP) {
        char *L = gen_tac(t->u.binop.left);
        char *R = gen_tac(t->u.binop.right);
        char *tmp = make_temp();
        emit_bin(tmp, L, t->u.binop.op, R);
        free(L); free(R);
        return tmp;
    }
    return strdup("<err>");
}

/* ---------- Utilities ---------- */
void print_tokens_full(const char *s) {
    // temporary scan to show tokens
    int saved_pos = input_pos;
    input_pos = 0;
    Token *t;
    printf("-- Tokens --\n");
    while (1) {
        t = next_token();
        if (t->type == T_EOF) { free_token(t); break; }
        printf("%3d: type=%d lex='%s' pos=%d", saved_pos, t->type, t->lexeme ? t->lexeme : "", t->pos);
        printf("\n");
        free_token(t);
    }
    input_pos = saved_pos;
}

/* ---------- Main ---------- */
int main(int argc, char **argv) {
    char buf[4096];
    printf("Enter expression:\n");
    if (!fgets(buf, sizeof(buf), stdin)) return 0;
    // strip newline
    size_t L = strlen(buf); if (L && buf[L-1]=='\n') buf[L-1] = '\0';
    input = strdup(buf);
    input_len = strlen(input);
    input_pos = 0;

    // prime tokens
    curtok = NULL;
    peektok = next_token();
    advance(); // curtok now first token

    // Print tokens (simple loop)
    printf("\n-- Lexed Tokens (type, lexeme, pos) --\n");
    // To print we must re-lex since we've advanced; easiest is to re-scan separately
    print_tokens_full(input);

    // Parse
    AST *root = parse_expr();
    if (curtok->type != T_EOF) {
        fprintf(stderr, "Parse error: unexpected token after expression at pos %d ('%s')\n", curtok->pos, curtok->lexeme ? curtok->lexeme : "");
        return 1;
    }

    printf("\n-- AST --\n");
    print_ast(root, 0);

    // Generate TAC
    char *result = gen_tac(root);

    printf("\n-- Three-Address Code (TAC) --\n");
    for (int i=0;i<instr_count;i++) {
        Instr *ins = &instrs[i];
        if (ins->is_bin) {
            printf("%s = %s %s %s\n", ins->dst, ins->a, ins->op, ins->b);
        } else {
            printf("%s = %s\n", ins->dst, ins->a);
        }
    }

    printf("\nResult in %s\n", result);

    // Cleanup
    free(result);
    free_ast(root);
    if (instrs) free(instrs);
    if (curtok) free_token(curtok);
    if (peektok) free_token(peektok);
    free(input);
    return 0;
}