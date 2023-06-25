#include "lists.h"



struct Node
{
    ElementType Element[17];
    int coord[2][17];
    Position Next;
};



List CreateList()
{   
    List L;
    L = malloc(sizeof(struct Node));
    if(L == NULL){
        FatalErro("Out of memory");
    }
    L->Next=NULL;
    return L;
}




int IsEmpty(List L)
{
    return L->Next == NULL;
}



int IsLast(Position P)
{
    return P->Next == NULL;
}



Position FindList(ElementType X[17], List L)
{
    Position P;

    P = L->Next;
    while (P != NULL && strcmp(P->Element ,X)==0)
        P = P->Next;

    return P;
}



void Delete(ElementType X[17], List L)
{
    Position P, TmpCell;

    P = FindPrevious(X, L);

    if (!IsLast(P)) 
    {                  
        TmpCell = P->Next;
        P->Next = TmpCell->Next; 
        free(TmpCell);
    }
}


Position
FindPrevious(ElementType X[17], List L)
{
    Position P;

    P = L;
    while (P->Next != NULL && strcmp(P->Next->Element ,X)==0)
        P = P->Next;

    return P;
}


void InsertList(ElementType X[17], int coord[2][17], List L, Position P)
{
    Position TmpCell;
    
    TmpCell = malloc(sizeof(struct Node));
    if (TmpCell == NULL)
        FatalErro("Out of space!!!");

    strcpy( TmpCell->Element , X);
    
    
    for (int i = 0; i < 2; i++)
    {
        for (int j = 0; j < strlen(X); j++) {   
              
            TmpCell->coord[i][j] = coord[i][j];     
        }  
    }    

    TmpCell->Next = P->Next;
    P->Next = TmpCell;
}

void DeleteList(List L)
{
    Position P, Tmp;

    P = L->Next; /* Header assumed */
    L->Next = NULL;
    while (P != NULL)
    {
        Tmp = P->Next;
        free(P);
        P = Tmp;
    }
}


void PrintList(List L){
    Position temp = L->Next;
    while (temp!=NULL)
    {
        printf("%s:", temp->Element);
        for (int i = 0; i<strlen(temp->Element); i++)
        {   

            char character=temp->Element[i];
            
            printf(" %c(%d,%d)",character,temp->coord[0][i],temp->coord[1][i]);
            
            if((i+1<strlen(temp->Element))){
                printf(" ->");
            }
        }
        printf("\n");
        temp = temp->Next;
        
    }
    
}


Position
Header(List L)
{
    return L;
}

Position
First(List L)
{
    return L->Next;
}

Position
Advance(Position P)
{
    return P->Next;
}

ElementType
RetrieveList(Position P)
{
    return *P->Element;
}