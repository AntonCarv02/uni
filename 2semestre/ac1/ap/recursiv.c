#include <stdio.h>

int loga(int n){
    /*if(n==1){
        return 0;
    }
    return loga(n/2)+1;
*/

    //####################


    int res=0;
    
    while(n>1){
        n/=2;
        res++;
    }
    
    return res;
}


int main(int argc, char const *argv[])
{
    printf("%d", loga(15));

    return 0;
}

