#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
 

typedef unsigned int Index;
typedef Index Position;

struct HashTbl;
typedef struct HashTbl *LinHashTable;
enum KindOfEntry { Legitimate, Empty, Deleted };

struct HashEntry {
    char* Element;
    enum KindOfEntry Info;
};

typedef struct HashEntry Cell;

/* Cell *TheCells will be an array of */
/* HashEntry cells, allocated later */
struct HashTbl {
    int Ocupados;
    int TableSize;
    Cell* TheCells;
};




static int NextPrime(int N) {
    int i, j;
    bool isPrime;
    if (N <= 2)
        return 2;
    if (N % 2 == 0)
        i = N + 1;
    else
        i = N;
    while (true) {
        isPrime = true;
        for (j = 3; j * j <= i; j += 2) {
            if (i % j == 0) {
                isPrime = false;
                break;
            }
        }
        if (isPrime)
            return i;
        i += 2;
    }
}

/* Hash function for strings */
Index Hash(const char* Key, int TableSize) {
    unsigned int hv = 0;
    while (*Key != '\0')
        hv = (hv << 5) + *Key++;
    return hv % TableSize;
}

LinHashTable InitializeTable(int TableSize) {
    LinHashTable H;
    int i;

    if (TableSize < 10) {
        printf("Table size too small\n");
        return NULL;
    }

    H = malloc(sizeof(struct HashTbl));
    if (H == NULL) {
        printf("Out of space!!!\n");
        return NULL;
    }

    H->TableSize = NextPrime(TableSize);
    H->Ocupados = 0;

    H->TheCells = malloc(sizeof(Cell) * H->TableSize);
    if (H->TheCells == NULL) {
        printf("Out of space!!!\n");
        free(H);
        return NULL;
    }

    for (i = 0; i < H->TableSize; i++) {
        H->TheCells[i].Info = Empty;
        H->TheCells[i].Element = NULL;
    }

    return H;
}

Index ProcPos(const char* Key, LinHashTable H) {
    Index P = Hash(Key, H->TableSize);
    while (H->TheCells[P].Info != Empty && strcmp(H->TheCells[P].Element, Key) != 0) {
        P = (P + 1) % H->TableSize;
    }
    return P;
}

int Find(const char* Key, LinHashTable H) {
    Index P = ProcPos(Key, H);
    if (H->TheCells[P].Info == Legitimate && strcmp(H->TheCells[P].Element, Key) == 0)
        return 1;
    else
        return 0;
}

LinHashTable Rehash(LinHashTable H) {
    int i, OldSize;
    Cell* OldCells;

    OldCells = H->TheCells;
    OldSize = H->TableSize;

    /* Get a new, empty table */
    LinHashTable newH = InitializeTable(OldSize);

    /* Scan through old table, reinserting into new */
    for (i = 0; i < OldSize; i++)
        if (OldCells[i].Info == Legitimate)
            Insert(OldCells[i].Element, newH);

    for (i = 0; i < OldSize; i++)
        free(OldCells[i].Element);
    free(OldCells);
    return newH;
}

void Insert(const char* Key, LinHashTable H) {
    Index P = ProcPos(Key, H);
    if (H->TheCells[P].Info != Legitimate) {
        H->TheCells[P].Info = Legitimate;
        H->TheCells[P].Element = strdup(Key);
        H->Ocupados++;
        if (H->Ocupados > H->TableSize)
            H = Rehash(H);
    }
}

float LoadFactor(LinHashTable H) {
    return (float)H->Ocupados / H->TableSize;
}




LinHashTable Add(LinHashTable H, const char* arquivo) {
    char word[50]="";
    FILE* f = fopen(arquivo, "r");
    while(fscanf(f, " %s", word) != EOF)
        Insert(word, H);
    fclose(f);
    return H;
}

void PrintTable(LinHashTable H) {
    for (int i = 0; i < H->TableSize; i++) {
        if (H->TheCells[i].Info == Legitimate)
            printf("%d Palavra: %s\n", i, H->TheCells[i].Element);
        else if (H->TheCells[i].Info == Empty)
            printf("Palavra: %d, Empty\n", i);
        else if (H->TheCells[i].Info == Deleted)
            printf("Palavra: %d, Deleted\n", i);
    }
}

void DestroyTable(LinHashTable H) {
    for (int i = 0; i < H->TableSize; i++)
        free(H->TheCells[i].Element);
    free(H->TheCells);
    free(H);
}

int main() {
    LinHashTable H = InitializeTable(58111);
    H = Add(H, "txt/corncob_caps_2023.txt");
    PrintTable(H);
    DestroyTable(H);
    return 0;
}