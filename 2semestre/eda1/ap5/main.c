#include "list.h"

int main(int argc, char const *argv[])
{
    
    List li = CreateList(NULL);
    Insert(2, li, Header(li));
    
    
    PrintList(li);
    
    Insert(3, li, First(li));
    
    PrintList(li);
    Delete(3,li);
    return 0;
}
