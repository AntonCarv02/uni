#include <stdio.h>
#include <stdlib.h>
#include "hashtable.h"
#include "lists.h"

void readBoggle(char game[4][4], char *file);

void showBoard(char game[4][4]);

int valid(int x, int y, int usado[4][4]);

void SearchBoogle(HashTable words, HashTable prefix, char game[4][4], int usado[4][4], int x, int y, char palavra[17],int coord[2][17], List result);

void runBoggle(int usado[4][4], HashTable words, HashTable prefix, char game[4][4], char palavra[17], int coord[2][17], List result);

