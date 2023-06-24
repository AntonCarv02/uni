
#define FatalError( Str )   fprintf( stderr, "%s\n", Str ), exit( 1 )
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SIZE_DIC 58109


typedef char* Element;

        #ifndef _HashQuad_H
        #define _HashQuad_H

        typedef unsigned int Index;

        struct HashTbl;
        typedef struct HashTbl *HashTable;

        int Ocupados(HashTable h);
        int tablesize(HashTable h);
        int Search( char* Key, HashTable H);
        HashTable InitializeTable( int TableSize );
        void DestroyTable( HashTable H );
        Index Find( char* Key, HashTable H );
        HashTable Insert(char *Key, HashTable H);
        Element Retrieve( Index P, HashTable H );
        HashTable Rehash( HashTable H );
        int isPrime(int number);
        int NextPrime(int number);
        void PrintTable(HashTable H);
        float LoadFactor(HashTable H);
        HashTable loadDic(const char *filename, HashTable h);
        HashTable loadPrefix(const char *filename, HashTable prefix);
        
        #endif  /* _HashQuad_H */