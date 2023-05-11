#include "abp.h"
#include <stdlib.h>
#include "fatal.h"

struct TreeNode
{
    ElementType Element;
    SearchTree Left;
    SearchTree Right;
};

SearchTree MakeEmpty(SearchTree T)
{
    if (T != NULL)
    {
        MakeEmpty(T->Left);
        MakeEmpty(T->Right);
        free(T);
    }
    return NULL;
}

Position Find(ElementType X, SearchTree T)

{
    Position node = T;

    while ((node->Element != X))
    {
        if (node->Element < X)
        {
            node = node->Right;
        }
        else if (node->Element > X)
        {

            node = node->Left;
        }
        if (node == NULL)
        {
            break;
        }
    }
    return node;
}

Position FindMin(SearchTree T)
{
    if (T == NULL)
        return NULL;
    else if (T->Left == NULL)
        return T;
    else
        return FindMin(T->Left);
}

Position FindMax(SearchTree T)
{
    if (T == NULL)
        return NULL;
    else if (T->Right == NULL)
        return T;
    else
        return FindMax(T->Right);
}

SearchTree Insert(ElementType X, SearchTree T)
{
    if (T == NULL)
    {
        T = malloc(sizeof(struct TreeNode));
        if (T == NULL)
            FatalError("Out of space!!!");
        else
        {
            T->Element = X;
            T->Left = T->Right = NULL;
        }
    }
    else if (X < T->Element)
        T->Left = Insert(X, T->Left);
    else if (X > T->Element)
        T->Right = Insert(X, T->Right);
}

SearchTree Delete(ElementType X, SearchTree T)
{

    Position temp;

    if (T == NULL)
        Error("Element not found");
    else {
        if (X < T->Element) /* Go left */
            hT->Left = Delete(X, T->Left);
        else
            if (/* condition */)
            {
                /* code */
            }
            

    }
    
}

ElementType Retrieve(Position P)
{
    return P->Element;
}
