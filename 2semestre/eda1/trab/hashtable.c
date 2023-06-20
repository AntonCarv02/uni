#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "hashtable.h"

#define MinTableSize (10)
#define SIZE_DIC 58109

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
    Cell *TheCells;
};


#include <stdbool.h>

bool isPrime(int number) {
    if (number <= 1)
        return 0;

    for (int i = 2; i * i <= number; i++) {
        if (number % i == 0){
            return 0;
            }
    }

    return 1;
}

int NextPrime(int number) {
    number++; // Increment the number to start checking the next number

    while (1) {
        if (isPrime(number)){

            return number;
        }

        number++; // Increment the number to check the next one
    }

}



/* Hash function for ints */
Index Hash(char *Key, int TableSize)
{
    unsigned int hv = 0;
    while (*Key != '\0')
        hv = (hv << 5) + *Key++;

    
    return hv % TableSize;
}


HashTable InitializeTable(int TableSize)
{
    HashTable H;
    int i;

    if (TableSize < MinTableSize)
    {
        printf("Table size too small");
        return NULL;
    }

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
    /*if(strcmp(Key,"AARDVARK")==0){
        printf("%u\n",CurrentPos);
    }*/
    
    while (H->TheCells[CurrentPos].Info != Empty && strcmp(H->TheCells[CurrentPos].Element, Key) != 0)
    {
        CurrentPos++;
        CurrentPos %= H->TableSize;
    }


    return CurrentPos;
}

HashTable rehash(HashTable H)
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



void Insert(char *Key, HashTable H)
{
    Position Pos = Find(Key, H);
    if (H->TheCells[Pos].Info != Legitimate)
    {
        /* OK to insert here */
        H->TheCells[Pos].Info = Legitimate;
        H->TheCells[Pos].Element = strdup(Key);
    }

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

    char str[30] = "";
    FILE *f = fopen(filename, "r");

    while(fscanf(f, " %s", str) != EOF)/*Se o ficheiro não tiver mais nada, fecha*/
        {  
            
            Insert(str, h);
            
        }

    fclose(f);
    return h;
}



int main(int argc, char const *argv[])
{   
    HashTable H = InitializeTable(SIZE_DIC);
    
    H = loadDic("txt/corncob_caps_2023.txt", H);
    Position p = Find("WATCHWORD",H);

    printf("%d %s",p, H->TheCells[p].Element);
    return 0;
}
