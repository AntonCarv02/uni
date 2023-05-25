#include "hash-linear.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define Error(Str) FatalError(Str)
#define FatalError(Str) fprintf(stderr, "%s\n", Str), exit(1)

#define MinTableSize (10)

enum KindOfEntry
{
    Legitimate,
    Empty,
    Deleted
};

struct HashEntry
{
    ElementType Element;
    enum KindOfEntry Info;
};

typedef struct HashEntry Cell;

/* Cell *TheCells will be an array of */
/* HashEntry cells, allocated later */
struct HashTbl
{
    int Ocupados;
    int TableSize;
    Cell *TheCells;
};



static int NextPrime(int N)
{
    if (N % 2 == 0)
        N++;
    do
    {
        N += 2;
    } while (!isPrime(N));

    return N;
}

int isPrime(int n)
{
    for (int i = 3; i <= n / 2; i + 2)
    {

        // if n is divisible by i, then n is not prime
        // change flag to 1 for non-prime number
        if (n % i == 0)
        {
            return 0;
        }
    }
    return 1;
}



/* Hash function for integers */
Index Hash(ElementType Key, int TableSize)
{
    return Key % TableSize;
}


LinHashTable InitializeTable(int TableSize)
{
    LinHashTable H;
    int i;

    if (TableSize < MinTableSize)
    {
        Error("Table size too small");
        return NULL;
    }

    /* Allocate table */
    H = malloc(sizeof(struct HashTbl));
    if (H == NULL)
        FatalError("Out of space!!!");

    // H->TableSize = NextPrime( TableSize );
    // Para que possa ver os resultados dos exercícios

    H->TableSize = TableSize;
    /* Allocate array of Cells */
    H->TheCells = malloc(sizeof(Cell) * H->TableSize);
    if (H->TheCells == NULL)
        FatalError("Out of space!!!");

    for (i = 0; i < H->TableSize; i++)
        H->TheCells[i].Info = Empty;

    return H;
}
/* END */



Position ProcPos(ElementType Key, LinHashTable H)
{
    Cell P;
    Cell *L = H->TheCells;
    
    P= L[ Hash( Key, H->TableSize ) ];
    while( P.Element != NULL && P.Element != Key ){
    
        P = L[ Hash( ++Key, H->TableSize ) ];
        
    }
    
    Position p = P.Element;
    return p;
}
/* END */



bool Find(ElementType Key, LinHashTable H)
{   
    return ProcPos(Key, H)==Key;
}



void Insert(ElementType Key, LinHashTable H)
{
    int value = Key;
    if(H->TheCells[Hash(Key,H->TableSize)].Info != Legitimate)
    {
        H->TheCells[Hash(Key,H->TableSize)].Element = Key;
    
    } else {
        while (H->TheCells[Hash(Key,H->TableSize)].Info != Legitimate)
        {
            Key++;

        } 
        
        H->TheCells[Hash(Key,H->TableSize)].Element = value;
        H->TheCells[Hash(Key,H->TableSize)].Info = Legitimate;
    }

}

float LoadFactor(LinHashTable H)
{
    int count=0;

    for (int i = 0; i < H->TableSize; i++)
    {
        if(H->TheCells[i].Element != NULL){
            count++;
        }
    }
    

    return count/H->TableSize;
}



LinHashTable Rehash(LinHashTable H)
{
    int i, OldSize;
    Cell *OldCells;

    OldCells = H->TheCells;
    OldSize = H->TableSize;

    /* Get a new, empty table */
    H = InitializeTable(2 * OldSize);

    /* Scan through old table, reinserting into new */
    for (i = 0; i < OldSize; i++)
        if (OldCells[i].Info == Legitimate)
            Insert(OldCells[i].Element, H);

    free(OldCells);

    return H;
}
/* END */

ElementType Retrieve(Position P, LinHashTable H)
{   
    H->TheCells[P].Info = Empty;
    return H->TheCells[P].Element;
}



void DestroyTable(LinHashTable H)
{
    free(H->TheCells);
    free(H);
}



void PrintTable(LinHashTable H)
{
}



int main(int argc, char const *argv[])
{
    HashTbl
    return 0;
}
