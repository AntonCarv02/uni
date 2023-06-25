#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#define FatalErro( Str )   fprintf( stderr, "%s\n", Str ), exit( 1 )

typedef char ElementType;


struct Node;
typedef struct Node *PtrToNode;
typedef PtrToNode Position;
typedef Position List;

List CreateList();
int IsEmpty(List L);
int IsLast(Position P);
Position FindList(ElementType X[17], List L);
void Delete(ElementType X[17], List L);
Position FindPrevious(ElementType X[17], List L);
void InsertList(ElementType X[17],int coord[2][17], List L, Position P);
void DeleteList(List L);
Position Header(List L);
Position First(List L);
Position Advance(Position P);
ElementType RetrieveList(Position P);
void PrintList(List L);


