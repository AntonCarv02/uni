//
//  Complete o programa para que seja possível, lendo uma expressão
//  em postfix do tipo 12 -5 * calcular o seu valor
//  Exemplos
//  10 5 3 + 4 * - 13 2 3 4 - * - / = -1.47
//  9 -5 3 * / 2 - 8 15 6 4 / - - * = 14.30

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stackarfloat.h"
#include <ctype.h>
#include "fatal.h"

int isDelimiter(char x, char delimiters[])
{
    int i = 0;
    char ch = delimiters[i];
    while (ch != '\0')
    {
        if (ch == x)
            return 1;
        i++;
        ch = delimiters[i];
    }

    return 0;
}

float do_operation(char op, float op1, float op2)
{
    // tobe done
    int res = 0;
    if ((op == '-'))
    {
        res = op1 - op2;
    }
    else if ((op == '*'))
    {

        res = op1 * op2;
    }
    else if (op == '+')
    {
        res = op1 + op2;
    }
    else if (op == '/')
    {

        res = op1 / op2;
    }

    return res;
}

// usar atof para converter string em float
float avalia(char exp[])
{
    Stack S = CreateStack(100);
    const char s[2] = " ";
    char *token;

    /* get the first token */
    token = strtok(exp, s);

    while (token != NULL)
    {
        printf("token = %s \n", token);
        // algoritmo avaliação postfix aqui


        if (!isDelimiter(token, "+*-/"))
        {
            Push(atof(token), S);
        }else {
            float res= do_operation(token, Pop(S),Pop(S));
            Push(res,S);
        }


        token = strtok(NULL, s);
    }
    if (IsEmptyStack(S))
        Error("stack is empty");
    else
    {
        float x = Pop(S);
        if (!IsEmptyStack(S))
            Error("wrong expression");
        else
            return x;
    }
}
char *ler_exp_postfix()
{
    static char name[100];

    printf("postfix : ");
    scanf("%[^\n]s", name);
    return name;
}

int main(int argc, const char *argv[])
{
    // insert code here...
    char *exp1;
    exp1 = ler_exp_postfix();
    char exp2[100];
    strcpy(exp2, exp1);
    printf("Lido %s \n", exp1);

    printf("conversão do input %s\n", exp1);

    printf(" %s = %.2f\n", exp2, avalia(exp1));
    return 0;
}
