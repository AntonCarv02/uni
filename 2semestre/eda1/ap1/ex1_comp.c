#include "complexo.h"
#include <stdio.h>
#include <stdlib.h>

struct ComplexoStruct
{
    float r, i;
};


Complexo CreateComplexo(float r, float i){

    Complexo c = {r,i};

    return c;
}


void print(Complexo x){
    if(x.i>=0){

        printf("%.01f + %.01fi\n", x.r,x.i);
    }else
    printf("%.01f %.01fi\n", x.r,x.i);
}


Complexo soma(Complexo x, Complexo y){

    Complexo c = CreateComplexo((x.r+y.r),(x.i+y.i));
    
    return c;
}


Complexo mult(Complexo x, Complexo y){

    Complexo c = CreateComplexo((x.r*y.r - x.i*y.i),(x.r*y.i + x.i*y.r));

    return c;
}


Complexo sub(Complexo x, Complexo y){
    
    Complexo c = CreateComplexo(x.r-y.r,x.i-y.i);
    
    return c;
}



Complexo divisao(Complexo x, Complexo y){
    
    Complexo c;
    
    float denominator = (y.r * y.r) + (y.i * y.i);
    c.r = ((x.r * y.r) + (x.i * y.i)) / denominator;
    c.i = ((x.i * y.r) - (x.r * y.i)) / denominator;
    
    return c;
}



Complexo conjugado(Complexo c){
    
    
    c.i=-c.i;

    return c;
}


int main(){
    
    Complexo c1 = CreateComplexo(3, 2);
    Complexo c2 = CreateComplexo(1, -4);
    
    printf("c1 = ");
    print(c1);
    printf("c2 = ");
    print(c2);
    
    Complexo c3 = soma(c1, c2);
    printf("c1 + c2 = ");
    print(c3);
    
    Complexo c4 = sub(c1, c2);
    printf("c1 - c2 = ");
    print(c4);
    
    Complexo c5 = mult(c1, c2);
    printf("c1 * c2 = ");
    print(c5);
    
    Complexo c6 = divisao(c1, c2);
    printf("c1 / c2 = ");
    print(c6);
    
    Complexo c7 = conjugado(c1);
    printf("conjugado de c1 = ");
    print(c7);
    
    return 0;
}