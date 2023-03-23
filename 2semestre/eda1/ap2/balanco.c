//
//  balanco.c
//  stacks
//
//  Created by Ligia Ferreira on 22/02/2023.
//
#include <stdio.h>
#include "stackar.h"

int éParentesisAbrir(char t){
   if(t == '('){
    return 1;
   }
   return 0;
}

int éParentesisFechar(char t){
     if(t == ')'){
    return 1;
   }
   return 0;
}

int concordam(char a, char b){
   
   if(((a == '(')&&(b==')'))||((a == '[')&&(b==']'))||((a == '{')&&(b=='}'))){
    return 1;
   }
   return 0;
    
}

int verifica_balanco(char *exp){
    Stack s = CreateStack(sizeof(exp));

    for(int i=0; i< (s->Capacity); i++){
        
    }
}

int main(){
    char exp[]="[()] {} {[()()]()}";
    int x=verifica_balanco(exp);
    if(x==0)
        printf("A expresssão \"%s\" não está balanceada!\n",exp);
    else
        printf("A expressão \"%s\" está balanceada!\n",exp);
    
}
