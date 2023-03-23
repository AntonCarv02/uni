#include "queue.h"
#include "stackar.h"
#include <stdio.h>

void Entrada(ElementType car, Queue q);
ElementType Saida(Queue q);
void F(Queue q1, Stack s);
void G(Stack s, Queue q2);


int main(int argc, char const *argv[])
{
    Queue a=CreateQueue(5),c=CreateQueue(5);
    Stack b = CreateStack(5);

    for (int i = 1; i < 5; i++)
    {
        Entrada(i,a);
    }
    for (int i = 0; i < 4; i++)
    {
        F(a,b);
    }
    for (int i = 0; i < 4; i++)
    {
        G(b,c);
    }
    for (int i = 0; i < 4; i++)
    {
        printf("%d",Saida(c));
    }
    return 0;
}


void Entrada(ElementType car, Queue q){
    Enqueue(car, q);
}

ElementType Saida(Queue q){
    return Dequeue(q);
}

void F(Queue q1, Stack s){
    Push(Dequeue(q1), s);
}

void G( Stack s, Queue q2){

    Enqueue(Pop(s), q2);
}