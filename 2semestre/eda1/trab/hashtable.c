
#include "hashtable.h"

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
    int TableSize;
    int Ocupados;
    Cell *TheCells;
};


int isPrime(int number)
{
    if (number <= 1)
        return 0;

    for (int i = 2; i * i <= number; i++)
    {
        if (number % i == 0)
        {
            return 0;
        }
    }

    return 1;
}

int NextPrime(int number)
{
    number++; // Increment the number to start checking the next number

    while (1)
    {
        if (isPrime(number))
        {

            return number;
        }

        number++; // Increment the number to check the next one
    }
}



/* Hash function for ints */
Index Hash(char *Key, int TableSize)
{
    unsigned int hv = 0;
    //printf("%s %d", Key, TableSize);
    
    while (*Key != '\0'){
        hv = (hv << 5) + *Key++;
}
    
    //printf(" %d\n",hv% TableSize);
    
    return hv % TableSize;
}



HashTable InitializeTable(int TableSize)
{
    HashTable H;
    int i;

    /*if (TableSize < MinTableSize)
    {
        printf("Table size too small");
        return NULL;
    }*/



    /* Allocate table */
    H = malloc(sizeof(struct HashTbl));
    if (H == NULL)
        printf("Out of space!!!");


    H->TableSize = NextPrime(TableSize);

    /* Allocate array of Cells */
    H->TheCells = malloc(sizeof(Cell) * H->TableSize);

    if (H->TheCells == NULL)
        printf("Out of space!!!");



    for (i = 0; i < H->TableSize; i++)
        H->TheCells[i].Info = Empty;


    return H;
}




Position Find(char *Key, HashTable H)
{
    Index CurrentPos;
    int CollisionNum;


    CurrentPos = Hash(Key, H->TableSize);
    
    while (H->TheCells[CurrentPos].Info != Empty && strcmp(H->TheCells[CurrentPos].Element, Key) != 0)
    {
        CurrentPos++;
        CurrentPos %= H->TableSize;
    }
    
    
    return CurrentPos;
}




HashTable Rehash(HashTable H)
{
    int i, OldSize;
    Cell *OldCells;

    OldCells = H->TheCells;
    OldSize = H->TableSize;

    /* Get a new, empty table */
    HashTable new = InitializeTable(2 * OldSize);


    /* Scan through old table, reinserting into new */
    for (i = 0; i < OldSize; i++){

        if (OldCells[i].Info == Legitimate){

            Insert(OldCells[i].Element, new);
        }
    }
    free(OldCells);
    
    return new;
}




HashTable Insert(char *Key, HashTable H)
{
    Position Pos = Find(Key, H);

    
   
    if (H->TheCells[Pos].Info != Legitimate)
    { 
        /* OK to insert here */
        H->TheCells[Pos].Info = Legitimate;
        H->TheCells[Pos].Element = strdup(Key);
        H->Ocupados++;

        
        if (LoadFactor(H)>(0.5)){

            
            H = Rehash(H);
        }
    }
    return H;
}


float LoadFactor(HashTable H) {
    return (float)H->Ocupados / H->TableSize;
}



ElementType Retrieve(Position P, HashTable H)
{
    return H->TheCells[P].Element;
}



void DestroyTable(HashTable H)
{
    free(H->TheCells);
    free(H);
}



void PrintTable(HashTable H)
{
    for (int i = 0; i < H->TableSize; i++)
    {
        if (H->TheCells[i].Info == Legitimate)
        {
            printf("Palavra nº %d: %s\n", i, H->TheCells[i].Element);
        }
        else
        {
            printf("%d: null\n", i);
        }
    }
}




HashTable loadDic(const char *filename, HashTable h)
{
    h = InitializeTable(SIZE_DIC);
    char str[30];
    FILE *f = fopen(filename, "r");

    while (fscanf(f, " %s", str) != EOF) /*Se o ficheiro nao tiver mais nada, fecha*/
    {
        int i = 1;

        h =Insert(str, h);
        h->Ocupados=0;

    }

    fclose(f);
    return h;
}




HashTable loadPrefix(const char *filename, HashTable prefix)
{
    prefix = InitializeTable(6);
    char str[30],pre[30];
    FILE *f = fopen(filename, "r");

    while (fscanf(f, " %s", str) != EOF) /*Se o ficheiro nao tiver mais nada, fecha*/
    {
        int i = 1;

        
        while (i<strlen(str))
        {   
            
            memset(pre,'\0',sizeof(pre));

            strncpy(pre, str,i);

            
            prefix = Insert(pre, prefix);
            
            i++;
            
        }        
        
    }

    fclose(f);
    return prefix;
}



