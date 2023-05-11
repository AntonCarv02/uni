#include "btree.h"


struct TreeNode{
    ElementType Element;
    BTree  Left;
    BTree  Right;
};


BTree MakeEmpty( BTree T ){

}


BTree SetTree( ElementType X){
    BTree node = (BTree)malloc(sizeof(struct TreeNode));

    node->Element = X;
    node->Left = NULL;
    node->Right = NULL;
    
    return node;
}


Position Find( ElementType X, BTree T ){

    Position node = T;

    while((node->Element != X)){
        if(node->Element < X){
            node = node->Right;
        }else if (node->Element > X){

            node = node->Left;
        }
        if(node == NULL){
            break;
        }
    }
    return node;
}


ElementType Retrieve( Position P ){

    return P->Element;
}


Position insertLeft(Position tree, int X) {
  tree->left = SetTree(X);
  return tree->left;
}


Position insertRight(Position tree, int X) {
  tree->right = SetTree(X);
  return tree->right;
}



void PrintInOrder( BTree T ){

}


void PrintPreOrder( BTree T ){

}


void PrintPostOrder( BTree T ){

}