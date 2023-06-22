#include "boogle.h"

int main(int argc, char const *argv[])
{
    char game[4][4];
    HashTable WordsTable , PrefixTable;

    //WordsTable = loadDic("txt/corncob_caps_2023.txt", WordsTable);
    PrefixTable = loadPrefix("txt/test.txt", PrefixTable);

    readBoggle(game, "txt/boggle0.txt");
    showBoard(game);

    PrintTable(PrefixTable);

    return 0;
}


void readBoggle(char game[4][4], char *file)
{
    char c;
    
    FILE *f = fopen(file, "r");

    if (!(f == NULL))
    {
        
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
    

    

}

void showBoard(char game[4][4]){
    for (int i = 0; i < 4; i++)
    { // Percorre o array e mostra-o
        for (int j = 0; j < 4; j++)
        {
            printf("%c ", game[i][j]);
        }
        printf("\n");
    }
}


