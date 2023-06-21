
#define FatalError( Str )   fprintf( stderr, "%s\n", Str ), exit( 1 )
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


typedef char* ElementType;

        #ifndef _HashQuad_H
        #define _HashQuad_H

        typedef unsigned int Index;
        typedef Index Position;

        struct HashTbl;
        typedef struct HashTbl *HashTable;

        HashTable InitializeTable( int TableSize );
        void DestroyTable( HashTable H );
        Position Find( ElementType Key, HashTable H );
        HashTable Insert( ElementType Key, HashTable H );
        ElementType Retrieve( Position P, HashTable H );
        HashTable Rehash( HashTable H );
        int isPrime(int number);
        int NextPrime(int number);
        void PrintTable(HashTable H);
        float LoadFactor(HashTable H);
        HashTable loadDic(const char *filename, HashTable h);

        HashTable loadPrefix(const char *filename, HashTable prefix);
        
        #endif  /* _HashQuad_H */