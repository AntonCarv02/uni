//
//  Complete o programa para que seja possível, ler uma
//  expressão em infix e fazer a conversão para postfix
//  usando o algoritmo descrito nas aulas teóricas
//
//
//  Created by Ligia Ferreira on 12/03/2021.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fatal.h"
#include <ctype.h>

#include "stackarfloat.h"

int priority(char c)
{
    // to be done
    if ((c == '-') || (c == '+'))
    {
        return 1;
    }
    else if ((c == '*') || (c == '/'))
    {
        return 2;
    }
    else if ((c == '(') || (c == ')'))
    {
        return 3;
    }

    return 0;
}

char *ler_input()
{
    static char name[100];
    printf("Os tokens devem aprearecer separados por espaço \n");

    printf("Exemplo ( 2 + 6 ) * 10 \n");
    printf("infix : ");
    scanf("%[^\n]s", name);
    return name;
}

int isNumber(char *token)
{
    int i = 0;
    while (token[i] != '\0')
    {
        if (!isdigit(token[i]))
        {

            return 0;
        }
        i++;
    }
    return 1;
}

char *tostring(char x)
{
    static char toReturn[2];
    toReturn[0] = x;
    toReturn[1] = '\0';
    return toReturn;
}

// usar strtok para tokenizar a string input
// usar strcat pata concatenar strings

void conversion(char input[], char output[])
{
    Stack s = CreateStack(100);
    char *token;
    output[0] = '\0';
    token = strtok(input, " ");
    while (token != NULL)

    {
        printf("token %s\n", token);
        // algoritmo aqui...

        if (isNumber(token))
        {
            strcat(output, token);
        }
        else
        {
            if (token == ')')
            {
                while (Top(s) != '(')
                {
                    strcat(output, Pop(s));
                }
                Pop(s);
            }
            else
            {
                if (priority(token) > priority(Top(s)))
                {
                    Push(atof(token), s);
                }
                else
                {
                    while((Top(s)!='(')||(priority(Top(s))>=priority(token))){
                        strcat(output, Pop(s));
                    }
                    Push(atof(token), s);
                }
            }
        }

        token = strtok(NULL, " ");
    }

    while (!IsEmpty(s))
    {
        strcat(output, Pop(s));
    }
}

int main(int argc, const char *argv[])
{

    char *infix;

    infix = ler_input();
    char postfix[strlen(infix)];
    conversion(infix, postfix);
    printf("postfix-> %s\n", postfix);

    return 0;
}
