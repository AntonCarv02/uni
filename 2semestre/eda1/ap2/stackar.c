#include "stackar.h"
#include "fatal.h"
#include <stdlib.h>

#define EmptyTOS (-1)
#define MinStackSize (5)

struct StackRecord
{
	int Capacity;
	int TopOfStack;
	ElementType *Array;
};

Stack CreateStack(int MaxElements)
{

	Stack S;

	if (MaxElements < MinStackSize)
		Error("Stack size is too small");

	S = malloc(sizeof(struct StackRecord));
	if (S == NULL)
		FatalError("Out of space!!!");

	S->Array = malloc(sizeof(ElementType) * MaxElements);
	if (S->Array == NULL)
		FatalError("Out of space!!!");

	S->Capacity = MaxElements;
	MakeEmpty(S);

	return S;
}

void DisposeStack(Stack S)
{
	if (S != NULL)
	{
		free(S->Array);
		free(S);
	}
}

int IsEmpty(Stack S)
{
	if (S->TopOfStack == EmptyTOS)
	{
		return 1;
	}
	return 0;
}

int IsFull(Stack S)
{
	if (S->TopOfStack == S->Capacity-1)
	{
		return 1;
	}
	return 0;
}

void MakeEmpty(Stack S)
{
	S->TopOfStack = EmptyTOS;
}

void Push(ElementType X, Stack S)
{
	if(!IsFull(S)){
		printf("here %d\n",X);
		S->Array[S->TopOfStack+1] = X;
		S->TopOfStack++;
	}
}

ElementType Top(Stack S)
{
	if(!IsEmpty(S)){
		return S->Array[S->TopOfStack];
	} 
	return 0;
}

ElementType Pop(Stack S)
{
	ElementType n = S->Array[S->TopOfStack];
	S->TopOfStack-=1;

	return n;
}

/*/
int main(int argc, char const *argv[])
{
    Stack s = CreateStack(5);

    printf("emp %d\n",IsEmpty(s));

	for (int i = 1; i < 6; i++)
	{
		Push(i,s);
	}
	printf(" top %d\n",Top(s));
	printf(" pop %d\n", Pop(s));
	printf("full %d\n",IsFull(s));
	Push(6,s);
	printf(" top %d\n",Top(s));
	printf("full %d\n",IsFull(s));
    return 0;
}*/