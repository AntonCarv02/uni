//
//  Complete o programa para que seja possível, ler uma
//  expressão em infix e fazer a conversão para postfix
//  usando o algoritmo dexcrito nas aulas teóricas
//
//
//  Created by Ligia Ferreira on 12/03/2021.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fatal.h"
#include <ctype.h>
#include "stackarchar.h"


int priority(char c){
	int i=0;
	switch (c){
		case ('('):
			i=3;
			break;
		case ('*'):
		case ('/'):
			i=2;
			break;
		case '+':
		case '-':
			i=1;
			break;
    	default:
    		Error("Something wrong");
	}
	return i;
}


char *ler_input(){
    static char name[100];
    printf("Os tokens devem aprearecer separados por espaço \n");
    
    printf("Exemplo ( 2 + 6 ) * 10 \n");
    printf("infix : ");
    scanf("%[^\n]s",name);
    return name;
}


int isNumber(char *token){
   if(isdigit(token[0]) || token[0]=='-' && isdigit(token[1]))
   		return 1;
   return 0;
    
}

char *tostring(char x){
    static char toReturn[2];
    toReturn[0]=x;
    toReturn[1]='\0';
    return toReturn;
}

// usar strtok para tokenizar a string input
// usar strcat pata concatenar strings

void conversion(char input[], char output[]){
    
    char* token;
    output[0]='\0';
    token=strtok(input," ");
    Stack S=CreateStack(100);
    while (token !=NULL){
        printf("token %s\n",token);
        if(isNumber(token)){
        	strcat(output,token);
        	strcat(output," ");
        }
        else {
        	if(token[0]==')'){
        		while(Top(S)!='(' ){
        			strcat(output,tostring(Pop(S)));
        			strcat(output," ");
        		}
        		Pop(S);
        	}
        	else{
        		if(IsEmptyStack(S)||priority(token[0])>priority(Top(S)))
        			Push(token[0],S);
        		else{
        			while (!IsEmptyStack(S)&& Top(S)!='('&& priority(Top(S))>=priority(token[0])){
        				strcat(output,tostring(Pop(S)));
        				strcat(output," ");
        			}
        			Push(token[0],S);
        		}
        		 
        	}
        }	
        token=strtok(NULL," ");
    }
    while (!IsEmptyStack(S)){
    	strcat(output,tostring(Pop(S)));
        strcat(output," ");
    }
    DisposeStack(S);
}

int main(int argc, const char * argv[]) {
  
    char* infix;
    
    infix=ler_input();
    char postfix[strlen(infix)];
    conversion(infix, postfix);
    printf("postfix-> %s\n",postfix);
    
    
    return 0;
}
