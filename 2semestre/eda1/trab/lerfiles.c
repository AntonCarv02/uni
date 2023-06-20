#include <stdio.h>
#include "ler_files.h"

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
    

    /*for (int i = 0; i < 4; i++)
    { // Percorre o array e mostra-o
        for (int j = 0; j < 4; j++)
        {
            printf("%c ", filearray[i][j]);
        }
        printf("\n");
    }*/

}

void readDictonary(){

    

}

