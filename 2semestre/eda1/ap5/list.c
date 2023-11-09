#include "list.h"
#include <stdlib.h>
#include "fatal.h"
#include <stdbool.h>

struct Node
{
    ElementType Element;
    Position Next;
};

List CreateList(List L)
{
    if (L != NULL)
        DeleteList(L);
    else
    {
        L = malloc(sizeof(struct Node));
        if (L == NULL)
            FatalError("Out of memory!");
        L->Next = NULL;
    }
    return L;
}

bool IsEmptyList(List L)
{

    return L->Next == NULL;
}


bool IsLast(Position P, List L)
{

    return P->Next == NULL;
}

Position Find(ElementType X, List L)
{

    Position temp = L->Next;

    while (temp != NULL)
    {

        if (temp->Element == X)
        {
            return temp;
        }
        temp = temp->Next;
    }
    return NULL;
}

Position FindPrevious(ElementType X, List L)
{
    Position temp = L->Next;

    while (!IsLast(temp,L))
    {

        if (temp->Next->Element == X)
        {
            return temp;
        }
        temp = temp->Next;
    }
}

void Insert(ElementType X, List L, Position P)
{
    Position new = malloc(sizeof(struct Node));

    
    new->Element = X;
    new->Next = P->Next;
    P->Next = new;
}

void Delete(ElementType X, List L)
{
    Position P = FindPrevious(X,L), delete;
    
    delete=P->Next;
    P->Next = delete->Next;
    free(delete);
    
    //?????
    
}

void DeleteList(List L)
{
    Position temp = L->Next;

    while(temp!=NULL){

        L->Next=temp->Next;
        free(temp);
        temp = L->Next;
    }
}

Position Header(List L)
{
    return L;
}

Position First(List L)
{

    return L->Next;
}

Position Advance(Position P)
{

    return P->Next;
}

ElementType Retrieve(Position P)
{

    return P->Element;
}

void PrintList(List L)
{
    Position temp = L->Next;
    while (temp!=NULL)
    {
        printf("%d", temp->Element);
        temp = temp->Next;
    }
    
    
}
void q2(List l){
    Position f,n,r;

    f=First(l);
    n=Advance(f);
    while (n!=NULL) 
    {
        r=Advance(n);
        n->Next=f;
        f=n;
        n=r;
    }

    First(l)->Next=NULL;
    l->Next=f;
    
}

int main(int argc, char const *argv[])
{
    List l=CreateList(l);
    Insert(40,l,First(l));
    Insert(21, l, Advance(First(l)));
    Insert(10,l,Advance(Advance(First(l))));
    Insert(6, l, Advance(Advance(Advance(First(l)))));
    Insert(1,l,Advance(Advance(Advance(Advance(First(l))))));
    PrintList(l);
    q2(l);
    PrintList(l);
    return 0;
}

