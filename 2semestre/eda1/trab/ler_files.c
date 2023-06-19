#include <stdio.h>
#include "ler_files.h"

int main(int argc, char const *argv[])
{
    int filearray[4][4];

    FILE *f = fopen(argv[1], "r");


    while (true)
    {
        if (!(f == NULL))
        {

            fscanf(f, "%d%c");

            // printf("l%d c%d\n",fileline,filecol);

            if (fgetc(f) == EOF)
            {
                break;
            }
        }
    }
    fclose(f);

    return 0;
}
