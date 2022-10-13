#include <stdbool.h>

typedef  int ElementType;


#ifndef _HashLin_H
#define _HashLin_H

typedef unsigned int Index;
typedef Index Position;

struct HashTbl;
typedef struct HashTbl *LinHashTable;

LinHashTable InitializeTable( int TableSize );
void DestroyTable( LinHashTable H );
Position ProcPos( ElementType Key, LinHashTable H );
bool Find(ElementType Key, LinHashTable H );
void Insert( ElementType Key, LinHashTable H );
ElementType Retrieve( Position P, LinHashTable H );
LinHashTable Rehash( LinHashTable H );
void PrintTable(LinHashTable H);
float LoadFactor(LinHashTable H);

/* Routines such as Delete are MakeEmpty are omitted */

#endif  /* _HashLin_H */