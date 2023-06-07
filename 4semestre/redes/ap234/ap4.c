#include <unistd.h>
#include <sys/socket.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <netdb.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>

void strdate(char *buffer, int len)
{
    time_t now = time(NULL);
    struct tm *ptm = localtime(&now);
    
    if (ptm == NULL) {
        
        puts("The localtime() function failed");
        return;
    }

    strftime(buffer, len, "%c", ptm);
}



int main(int argc, char const *argv[])
{

    int sockfd, newsock, addrlen;
    struct sockaddr_in  cli_addr,serv_addr;
    char buffer[1024];


    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd < 0)
    {
        perror("Error cannot open socket");
        exit(1);
    }


    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(1599);


    if(bind(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr))!=0){
        perror("bind");
    }


    // especificar o tamanho da fila de espera
    
    if ((listen(sockfd, 3)) != 0) {
        printf("Listen failed...\n");
        exit(0);
    }

    newsock = accept(sockfd, (struct sockaddr *)&cli_addr, (socklen_t*)&addrlen);
    if (newsock < 0) {
        printf("server accept failed...\n");
        exit(0);
    }

    strdate(buffer,1024);

    write(newsock, buffer, strlen(buffer));
    write(newsock,"\n", 2);

    close(newsock);

    close(sockfd);

    return 0;
}
