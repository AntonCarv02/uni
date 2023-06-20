
#define FatalError( Str )   fprintf( stderr, "%s\n", Str ), exit( 1 )



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
        void Insert( ElementType Key, HashTable H );
        ElementType Retrieve( Position P, HashTable H );
        HashTable Rehash( HashTable H );
        
        #endif  /* _HashQuad_H */