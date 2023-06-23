#include <stdlib.h>
#include <stdio.h>
#define FatalErro( Str )   fprintf( stderr, "%s\n", Str ), exit( 1 )

typedef char ElementType;


struct Node;
typedef struct Node *PtrToNode;
typedef PtrToNode List;
typedef PtrToNode Position;

List CreateList();
int IsEmpty(List L);
int IsLast(Position P, List L);
Position FindList(ElementType X, List L);
void Delete(ElementType X, List L);
Position FindPrevious(ElementType X, List L);
void InsertList(ElementType X, List L, Position P);
void DeleteList(List L);
Position Header(List L);
Position First(List L);
Position Advance(Position P);
ElementType RetrieveList(Position P);
void PrintList(List L);


