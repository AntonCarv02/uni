#include "boogle.h"

int count = 0;



int main(int argc, char const *argv[])
{
    char game[4][4], palavra[17]="";
    int usado[4][4], coord[2][17];
    memset(usado, 0, sizeof(usado));

    List result = CreateList() ;
    
    HashTable WordsTable = InitializeTable(SIZE_DIC), PrefixTable = InitializeTable(SIZE_DIC);

    WordsTable = loadDic("txt/corncob_caps_2023.txt", WordsTable);

    PrefixTable = loadPrefix("txt/corncob_caps_2023.txt", PrefixTable);

    readBoggle(game, "txt/boggle0.txt");
    showBoard(game);
    // PrintTable(PrefixTable); 


    runBoggle(usado, WordsTable, PrefixTable,game,palavra, coord, result);
    printf("palavras encontradas: %d\n", count);
    
    return 0;
}





int valid(int x, int y, int usado[4][4])
{
    if ((x >= 0 && x < 4) && (y >= 0 && y < 4) && !usado[x][y])
    {
        return 1;
    }
   
        return 0;
    
}

void SearchBoogle(HashTable words, HashTable prefix, char game[4][4], int usado[4][4], int x, int y, char palavra[17], int coord[2][17], List result)
{

    usado[x][y] = 1;
    
    int tamanho = strlen(palavra);

    palavra[tamanho]= game[x][y];
    //printf("%s\n", palavra);
    coord[0][tamanho]=x;
    coord[1][tamanho]=y;

    
    if((Search(palavra, prefix))){
        for (int l = -1; l < 2; l++)
        {
            for (int c = -1; c < 2; c++)
            {

                if (((l != 0) || (c != 0)) && valid(x + l, y + c, usado))
                {

                    SearchBoogle(words, prefix, game, usado, x + l, y + c, palavra, coord, result);
                }
            }
        }//printf("%s:",palavra);
    }


    if ((Search(palavra, words)))
    {   count++;
        printf("%s:",palavra);
        /*for (int i = 0; i<=tamanho; i++)
        {   

            //*ADICIONAR A LISTA DE OUTPUT AQUI/
            char character=game[coord[0][i]][coord[1][i]];
            
            printf(" %c(%d,%d)",character,coord[0][i],coord[1][i]);
            
            if((i+1<=tamanho)){
                printf(" ->");
            }
        }
        printf("\n");*/
        

    }
    
    usado[x][y] = 0;
    palavra[tamanho]= '\0';
}



void readBoggle(char game[4][4], char *file)
{
    char c;

    FILE *f = fopen(file, "r");

    if ((f == NULL))
    {
        FatalError("num ah fichero moh");
        
    }
    for (int i = 0; i < 4; i++)
    { // Percorre o array e mostra-o
        for (int j = 0; j < 4; j++)
        {

            c = fgetc(f);
            game[i][j] = c;

            if (fgetc(f) == EOF)
            {
                break;
            }
        }
    }

    fclose(f);
    
}

void showBoard(char game[4][4])
{
    for (int i = 0; i < 4; i++)
    { // Percorre o array e mostra-o
        printf("\t");
        for (int j = 0; j < 4; j++)
        {
            printf("%c ", game[i][j]);
        }
        printf("\n");
    }
}

void runBoggle(int usado[4][4], HashTable words, HashTable prefix, char game[4][4], char palavra[17], int coord[2][17], List result)
{

    int x = 0 , count = 0;
    while (x < 4)
    {
        int y = 0;
        while (y < 4)
        {   
            SearchBoogle(words, prefix, game, usado, x, y, palavra, coord, result);
            y++;
        }
        x++;
    }
}
