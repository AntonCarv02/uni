
#include "hash.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define Error( Str )        FatalError( Str )
#define FatalError( Str )   fprintf( stderr, "%s\n", Str ), exit( 1 )


#define MinTableSize (10)

enum KindOfEntry { Legitimate, Empty, Deleted };

struct HashEntry
{
    ElementType     Element;
    enum KindOfEntry Info;
};

typedef struct HashEntry Cell;

/* Cell *TheCells will be an array of */
/* HashEntry cells, allocated later */
struct HashTbl
{   int Ocupados;
    int TableSize;
    Cell *TheCells;
};

/* Return next prime; assume N >= 10 */

static int
NextPrime( int N )
{
   return 1;
}

/* Hash function for integers */
Index Hash( ElementType Key, int TableSize ){
    return Key % TableSize;
}

LinHashTable InitializeTable( int TableSize )
{
    LinHashTable H;
    int i;

    if( TableSize < MinTableSize ){
        Error( "Table size too small" );
        return NULL;
    }

    /* Allocate table */
    H = malloc( sizeof( struct HashTbl ) );
    if( H == NULL )
        FatalError( "Out of space!!!" );

     //H->TableSize = NextPrime( TableSize );
     //Para que possa ver os resultados dos exercícios
    
    H->TableSize =  TableSize ;
    /* Allocate array of Cells */
    H->TheCells = malloc( sizeof( Cell ) * H->TableSize );
    if( H->TheCells == NULL )
        FatalError( "Out of space!!!" );

    for( i = 0; i < H->TableSize; i++ )
        H->TheCells[ i ].Info = Empty;

    return H;
}


Position ProcPos( ElementType Key, LinHashTable H ){
   
}


bool Find(ElementType Key,LinHashTable H){
  return false;

}

void Insert( ElementType Key, LinHashTable H ){
   
}
/* END */

float LoadFactor(LinHashTable H){
   
}
LinHashTable Rehash( LinHashTable H ){
    int i, OldSize;
    Cell *OldCells;

    OldCells = H->TheCells;
    OldSize  = H->TableSize;

    /* Get a new, empty table */
    H = InitializeTable( 2 * OldSize );

    /* Scan through old table, reinserting into new */
    for( i = 0; i < OldSize; i++ )
        if( OldCells[ i ].Info == Legitimate )
        Insert( OldCells[ i ].Element, H );

     free( OldCells );

    return H;
}



ElementType Retrieve( Position P, LinHashTable H ){
    return H->TheCells[ P ].Element;
}

void DestroyTable( LinHashTable H ){
    free( H->TheCells );
    free( H );
}

void PrintTable(LinHashTable H){
  
}
