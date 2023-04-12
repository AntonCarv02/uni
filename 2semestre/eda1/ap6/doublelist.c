#include "doublelist.h"
#include <stdlib.h>
#include "fatal.h"
#include <stdbool.h>

struct Node
{
    ElementType Element;
    Position Next;
    Position Prev;
};
struct DList{
    Position head;
    Position last;
    int size;
};

List CreateList(List L)
{
    if (L != NULL)
        DeleteList(L);
    else
    {   L = malloc(sizeof(struct List));
        L1 = malloc(sizeof(struct Node));
        L2 = malloc(sizeof(struct Node));
        L->head = L1;
        L->last = L2;
        
        if ((L == NULL))
            FatalError("Out of memory!");

        L->head->Next = L->last;
        L->last->Prev = L->head;
        L->last->Next = NULL;
        L->head->Prev = NULL;

    }
    return L;
}


bool IsEmptyList( List L ){
    return L->head->Next == L->last;
}


bool IsLast( Position P, List L ){
    return p.
}



Position Find( ElementType X, List L ){

}


Position FindPrevious( ElementType X, List L ){

}



void Insert( ElementType X, List L, Position P ){

}


void Delete( ElementType X, List L ){

}


void DeleteList( List L ){

}



Position Header( List L ){

}


Position First( List L ){

}


Position Advance( Position P ){

}


ElementType Retrieve( Position P ){

}


void PrintList(List L){

}

int main(int argc, char const *argv[])
{
    List L = CreateList(NULL);
    Position last = malloc(sizeof(struct Node));

    L->Next = last;
    L->Prev = NULL;
    last->Next = NULL;
    last->Next = L;



    return 0;
}
