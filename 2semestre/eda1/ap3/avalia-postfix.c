//
//  main.c
//  StackArrayInt
//
//  Created by Ligia Ferreira on 20/02/2022.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stackarfloat.h"
#include <ctype.h>
#include "fatal.h"



int isOperator(char x, char operators[] ){
    int i=0;
    char ch=operators[i];
    while(ch!='\0'){
        if(ch==x)
            return 1;
        i++;
        ch=operators[i];
    }
    
    return 0;
}

float do_operation(char op, float op1, float op2){
    if(op=='+')
        return op1 + op2;
    else if (op=='-')
            return op1 - op2;
        else if (op =='*')
                return op1 * op2;
            else if (op=='/')
                    return op1/op2;
                else
                     Error("Invalid operation!");
    
}


float avalia(char exp[]){
    Stack S=CreateStack(100);
    const char s[2] = " ";
    char *token;
   
   
    /* get the first token */
    token = strtok(exp, s);
  
    while( token != NULL ) {
        //printf("token =-%s \n",token);
        if(isdigit(token[0]) || (token[0]=='-' && isdigit(token[1]))){
            float n=atof(token);
            printf("Valor convertido %.2f\n",n);
            Push(n,S);
            printf("PUSH(%.2f,S)\n",n);
            //PrintStack(S);
        }
        else
            if(strlen(token)==1 && isOperator(token[0],"/+-*")){
                float v2=Pop(S);
                printf("retirado %.2f=POP(S)\n",v2);
                float v1=Pop(S);
                printf("retirado %.2f=POP(S)\n",v1);
                float value= do_operation(token[0],v1,v2);
                printf("calculado %.2f %s %.2f = %.2f \n",v1,token,v2,value);
                Push(value,S);
                printf("PUSH(%.2f,S)\n",value);
            }
        token = strtok(NULL, s);
    }
    if(IsEmptyStack(S))
        Error("stack is empty");
    else{
        float x=Pop(S);
        if(!IsEmptyStack(S))
            Error("wrong expression");
        else
            return x;
           
        }

    
}
char * ler_exp_postfix(){
    static char name[100];
   
    printf("postfix : ");
    scanf("%[^\n]s",name);
    return name;
}


int main(int argc, const char * argv[]) {
    // insert code here...
    char * exp1;
    exp1 = ler_exp_postfix();
    char exp2[100];
    strcpy(exp2, exp1);
    printf("Lido %s \n", exp1);
    
    printf("conversão do input %s\n",exp1);
    
    printf(" %s = %.2f\n",exp2,avalia(exp1));
    return 0;
}
