#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <math.h>


void orderArray(int arr[], int a)
{

    int aux1, aux2;
    for (int i = 0; i < a; i++)
    {
        for (int j = i + 1; j < a; j++)
        {
            aux1 = arr[i];
            aux2 = arr[j];

            if (aux2 < aux1)
            {
                arr[i] = aux2;
                arr[j] = aux1;
            }
        }
    }
}


void recebeValores(int arr[], int a)
{
    int b;
    for (int i = 0; i < a; i++)
    {

        scanf("%d", &b);
        arr[i] = b;
    }
}


int createArr()
{
    int a;
    puts("\nIntroduza um valor: ");

    scanf("%d", &a);

    return a;
}


void printArr(int arr[], int size)
{
    // printf("size %d", (size));
    for (int i = 0; i < (size); i++)
    {

        printf("%d\n", arr[i]);
    }
}


void Arrdots(int dots[], int arr[], int s, int size)
{
    int count, value;
    int j = 0;

    for (int i = 0; i <= s; i++)
    {
        count = 0;
        while (j < size)
        {
            value = (arr[j] / 10);
            if (value == i)
            {
                count++;
                j++;
            }
            else
            {
                break;
            }
        }
        dots[i] = count;
        int hahahahaha = arr[0];
    }


    // find max
    int max = dots[0];
    for (int i = 1; i <= s; i++)
    {
        if (dots[i] > max)
        {
            max = dots[i];
        }
    }

    for (int i = 0; i <= s; i++)
    {
        dots[i] = max - dots[i];
    }
}


void pontosStem(int i, int size)
{

    int potencia10 = 0,pot10ind=0,ind=i;

    while ((size ) >= 10)
    {
        potencia10++;
        size = (size / 10);
    }

    while ((ind) >= 10)
    {
        pot10ind++;
        ind = (ind / 10);
    }
     printf("|");
    for(int j=0;j<(potencia10-pot10ind);j++){
        printf(".");
    }
    printf("%d|.",i);
}



int main(int argc, char const *argv[])

{

    int leaf1, leaf2, stem1, stem2, stemcheck = -1;

    int a = createArr();

    int verifVazioArr = 0;

    if (a == 0)
    {
        a = 1;
        verifVazioArr = 1;
    }

    int arr[a];
    arr[0] = 0;

    if (verifVazioArr == 1)
    {
        a = 0;
    }

    recebeValores(arr, a);
    orderArray(arr, a);
    // printArr(arr, a);

    int b = createArr();

    int arr2[b];

    recebeValores(arr2, b);
    orderArray(arr2, b);
    // printArr(arr2, b);

    if (verifVazioArr == 1)
    {
        a = 1;
    }
    int size;
    if (arr[a - 1] / 10 > arr2[b - 1] / 10)
    {
        size = arr[a - 1] / 10;
    }
    else
    {
        size = arr2[b - 1] / 10;
    }

    int arrdots[size + 1];
    Arrdots(arrdots, arr2, size, b);

    // printArr(arrdots, size);

    int j = 0, k = 0;
    for (int i = 0; i <= size; i++)
    {

        stem1 = arr[j] / 10;
        stem2 = arr2[k] / 10;
        // printf("\ns1-%d s2-%d\n",stem1,stem2);

        if ((stem2 == i) || (stem1 == i))
        {
            for (int y = 0; y < arrdots[i]; y++)
            {
                printf(".");
            }

            while (stem2 == i)
            {

                leaf2 = (arr2[k] % 10);
                printf("%d", leaf2);
                stem2 = arr2[k + 1] / 10;
                k++;
            }
            // pode ser melhor
            pontosStem(i,size);
            /*if ((i < 10) || (size < 10 * 10))
            {
                printf(".|.%d|.", i);
            }
            else
            {
                printf(".|%d|.", i);
            }*/

            while (stem1 == i)
            {
                if (verifVazioArr == 1)
                {
                    break;
                }
                leaf1 = (arr[j] % 10);
                printf("%d", leaf1);
                stem1 = arr[j + 1] / 10;
                j++;
            }
            puts("");
        }
    }

    puts("\n\n");
    return 0;
}
/*
11 29 19 154 17 23 19 49 17 12 41 18
14 34 129 123 24 35 121 12 33 125 51 121 37 32 31
*/