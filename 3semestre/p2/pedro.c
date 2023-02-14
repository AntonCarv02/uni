#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int vez(int a){
    if(a%2 == 0)
    {
        return 2;
    }
    else return 1;

}

void play(int x, int hands[][2], int turn){
    if(turn%2 != 0){
        switch(x){
            case 1:
                hands[1][0] = hands[0][0] + hands[1][0];
                if(hands[1][0]>=5) hands[1][0]-=5;
                
            break;
            case 2:
                hands[1][1] = hands[0][0] + hands[1][1];
                if(hands[1][1]>=5)  hands[1][1]-=5;
                
            break;
            case 3:
                hands[1][1] = hands[0][1] + hands[1][1];
                if(hands[1][1]>=5) hands[1][1]-=5;
                
            break;
            case 4:
                hands[1][0] = hands[0][1] + hands[1][0];
                if(hands[1][0]>=5) hands[1][0]-=5;
               
            break;
            case 5:
                if (hands[0][1] == 0 && hands[0][0] > 0 || hands[0][0] == 0 && hands[0][1] > 0 ){
                    if(hands[0][0]%2 == 0 && hands[0][0]>0)
                    {
                        hands[0][0] = hands[0][0]/2;
                        hands[0][1] = hands[0][0];
                    }
                    else if(hands[0][1]%2 == 0 && hands[0][1]>0)
                    {
                        hands[0][1] = hands[0][1]/2;
                        hands[0][0] = hands[0][1];
                    }
            break;    
                    
            }else printf("jogada inválida!");
            default:
            break;
        }   
    }
    else{
        if(turn%2 == 0){
            switch(x){
            case 1:
                hands[0][0] = hands[0][0] + hands[1][0];
                if(hands[0][0]>=5)  hands[0][0]-=5;
                
            break;
            case 2:
                hands[0][1] = hands[1][0] + hands[0][1];
                if(hands[0][1]>=5) hands[0][1]-=5;
               
            break;
            case 3:
                hands[0][1] = hands[0][1] + hands[1][1];
                if(hands[0][1]>=5)  hands[0][1]-=5;
                
            break;
            case 4:
                hands[0][0] = hands[0][0] + hands[1][1];
                if(hands[0][0]>=5)  hands[0][0]-=5;
                
            break;
            case 5:
                if (hands[1][1] == 0 && hands[1][0] > 0 || hands[1][0] == 0 && hands[1][1] > 0){
                    if(hands[1][0]%2 == 0 && hands[1][0]>0)
                    {
                        hands[1][0] = hands[1][0]/2;
                        hands[1][1] = hands[1][0];
                    }
                    else if(hands[1][1]%2 == 0 && hands[1][1]>0)
                    {
                        hands[1][1] = hands[1][1]/2;
                        hands[1][0] = hands[1][1];
                    }
            break;    
                    
            }else printf("jogada inválida!\n");
            
            
            }      
        }
    }
}

void aocalhas(int hands[][2], int turn){
    
    int validplay[] = {0,0,0,0,0};
    char jogada[3];
    srand(time(0));

    for(int x=1; x<=5; x++){
        if(x = 1){
            jogada[0] == 101 && jogada[1] == 101;
                if(verificajogada(hands, jogada, turn) == 0) validplay[0] = 1;
        }
        if(x = 2){
            jogada[0] == 101 && jogada[1] == 100;
                if(verificajogada(hands, jogada, turn) == 0) validplay[1] = 1;
        }
        if(x = 3){ 
            jogada[0] == 100 && jogada[1] == 100;
                if(verificajogada(hands, jogada, turn) == 0) validplay[2] = 1;
        }
        if(x = 4){
            jogada[0] == 100 && jogada[1] == 101;
                if(verificajogada(hands, jogada, turn) == 0) validplay[3] = 1;
        }
        if(x = 5){  
            jogada[0] == 61;
                if(verificajogada(hands, jogada, turn) == 0) validplay[4] = 1;
        }
    }

    int num;
        
    while(1){
        num = rand() % 6;
        if(validplay[num] == 1){
            chooseplay(num);
            break;
        }
    }



}

void chooseplay( int num){

    char jogada[3];

    if(num = 1) jogada[0] == 101 && jogada[1] == 101;          
    else if(num = 2) jogada[0] == 101 && jogada[1] == 100;
    else if(num = 3) jogada[0] == 100 && jogada[1] == 100;
    else if(num = 4) jogada[0] == 100 && jogada[1] == 101;
    else jogada[0] == 61;
    
}




int verificajogada(int hands[][2], char jogada[3], int turn){

    if(turn%2 != 0)
    {
        if((hands[0][0] == 0 && jogada[0]== 101) || (hands[0][1] == 0 && jogada[0]== 100) || (hands[1][0] == 0 && jogada[1] == 101) || (hands[1][1] == 0 && jogada[1] == 100))
        {
            return 1;
        } 
        else return 0;
    
    }
    else if(turn%2 == 0)
    {
        if((hands[1][0] == 0 && jogada[0]== 101) || (hands[1][1] == 0 && jogada[0]== 100) || (hands[0][0] == 0 && jogada[1] == 101) || (hands[0][1] == 0 && jogada[1] == 100))
        {
            return 1;
        }
        else return 0;
    }


}


void chicoesperto(int hands[][2], int turn){

    char jogada[3];

    if(turn%2 != 0){
        if(hands[0][0] < hands[0][1]) jogada[0] = 100;
        else jogada[0] = 101;
        if(hands[1][1] < hands[1][0] && hands[1][1] != 0) jogada[1] = 100;
        else if(hands[1][1] > hands[1][0] && hands[1][0] == 0) jogada[1] = 100;
        else jogada[1] = 101;
    
    }
    else
    {
        if(hands[1][0] < hands[1][1]) jogada[0] = 100;
        else jogada[0] = 101;
        if(hands[0][1] < hands[0][0] && hands[0][1] != 0) jogada[1] = 100;
        else if(hands[0][1] > hands[0][0] && hands[0][0] == 0) jogada[1] = 100;
        else jogada[1] = 101;
    }  

}



int main(int argc, char *argv[]){
    int i;
    int hands [][2] = {{1,1},{1,1}};

    for(i=1; i<=2; i++)
    {
        if (strcmp(argv[i],"humano") || strcmp(argv[i],"ao-calhas") || strcmp(argv[i],"chico-esperto")){
            continue;
        }
        else printf("Estratégia Inválida\n");
    }


    printf("Jogo dos dedos - %s vs %s\n",argv[1], argv[2]);
    printf("%s 1: %d, %d\n",argv[1], hands[0][0], hands[0][1]);
    printf("%s 2: %d, %d\n",argv[2], hands[1][0], hands[1][1]);
    
    int turn = 1;
    int x;
    while(!(hands[0][0] == 0 && hands[0][1] == 0) || !(hands[1][0] == 0 && hands[1][1] == 0))
    {
        
        char jogada[3];
        printf("vez do %s %d:\n",argv[1], vez(turn));
        scanf("%s", jogada);

         

        while(!(jogada[0] == 46 || jogada[0] == 101 && jogada[1] == 101 || jogada[0] == 101 && jogada[1] == 100 || jogada[0] == 100 && jogada[1] == 101 || jogada[0] == 100 && jogada[1] == 100 || jogada[0] == 61) && (verificajogada(hands, jogada, turn))==1){

            printf("jogada inválida! jogue outra vez:\n");
            scanf("%s", jogada);
            
        }   

        
            
        if (jogada[0] == 101 && jogada[1] == 101){
            x = 1;
        }
        else if (jogada[0] == 101 && jogada[1] == 100){
            x = 2;
        }
        else if (jogada[0] == 100 && jogada[1] == 100){
            x = 3;
        }
        else if (jogada[0] == 100 && jogada[1] == 101){
            x = 4;
        }
        else if (jogada[0] == 61){
            x = 5;
        }
        else if (jogada[0] == 46){
            printf("humano %d desistiu!\n", vez(turn));
            break;
        }    
                
               
            
        play(x, hands, turn);
        
        if(hands[0][0] == 0 && hands[0][1] == 0){
            printf("vitória do %s %d!\n", argv[2], vez(turn));
            break;
        }
        else if(hands[1][0] == 0 && hands[1][1] == 0){
            printf("vitória do %s %d!\n", argv[1], vez(turn));
            break;
        }
        

        printf("%s 1: %d, %d\n",argv[1], hands[0][0], hands[0][1]);
        printf("%s 2: %d, %d\n",argv[2], hands[1][0], hands[1][1]);
        turn++;
    }
    
}
