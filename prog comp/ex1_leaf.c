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
    int count;
    for (int i = 0; i <= s; i++)
    {
        count = 0;
        for (int j = 0; j < size; j++)
        {
            if ((arr[j] / 10) == i)
            {
                count++;
            }
        }
        dots[i] = count;
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

int main(int argc, char const *argv[])
{

    int leaf1, leaf2, stem1, stem2, stemcheck = -1;

    int a = createArr();
    int arr[a];

    recebeValores(arr, a);
    orderArray(arr, a);
    // printArr(arr, a);

    int b = createArr();
    int arr2[b];

    recebeValores(arr2, b);
    orderArray(arr2, b);
    // printArr(arr2, b);

    int size;
    if (arr[a - 1] / 10 > arr2[b - 1] / 10)
    {
        size = arr[a - 1] / 10;
    }
    else
    {
        size = arr2[b - 1] / 10;
    }

    int arrdots[size];
    Arrdots(arrdots, arr2, size, b);
    printf(".");
    printArr(arrdots, size);

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
            printf(" .|%d|. ", i);

            while (stem1 == i)
            {
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