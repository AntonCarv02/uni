//
//  balanco.c
//  stacks
//
//  Created by Ligia Ferreira on 22/02/2023.
//
#include <stdio.h>
#include "stackarchar.h"

int éParentesisAbrir(char t){
    return t=='('|| t=='{' || t=='[';
}

int éParentesisFechar(char t){
    return t==')'|| t=='}' || t==']';
}

int concordam(char a, char b){
    switch (a) {
        case '(':
            return b==')';
            break;
        case '[':
            return b==']';
            break;
        case '{':
            return b=='}';
            break;
        default:
            return 0;
            break;
    }
    
}

int verifica_balanco(char *exp){
    int i=0;
    Stack S=CreateStack(20);
    while(exp[i]!='\0'){
        char t=exp[i++];
        if(éParentesisAbrir(t)){
            Push(t,S);
            PrintStackChar(S);
        }
        else
            if (éParentesisFechar(t)){
                if(IsEmptyStack(S))
                    return 0;
                else
                    if (!concordam(Pop(S),t))
                        return 0;
                
            }
        
        
        }
    if(IsEmptyStack(S))
        return 1;
    else
        return 0;
}

int main(){
    char exp[]="[()] {} {[()()]()}";
    int x=verifica_balanco(exp);
    if(x==0)
        printf("A expresssão \"%s\" não está balanceada!\n",exp);
    else
        printf("A expressão \"%s\" está balanceada!\n",exp);
    
}
