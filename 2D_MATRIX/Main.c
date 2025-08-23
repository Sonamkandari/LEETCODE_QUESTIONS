
#include <stdio.h>
#include <string.h>

int isAStar(char str[]) {
    for (int i = 0; str[i] != '\0'; i++) {
        if (str[i] != 'a' && str[i] != '\n')
            return 0;
    }
    return 1;
}

int main() {
    char str[100];

    printf("Enter a string: ");
    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = '\0';

    if (strcmp(str, "abb") == 0) {
        printf("Accepted: Matches 'abb'\n");
    }
    else if (isAStar(str)) {
        printf("Accepted: Matches 'a*'\n");
    }
    else {
        printf("Rejected: Does not match 'a*' or 'abb'\n");
    }

    return 0;
}

