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
#include <arpa/inet.h>

int main()
{

    struct sockaddr_in addr;
    int portno = 9998;
    char buffer[1024];
    char msg[1024];

    // AF_INET representa IPv4; SOCK_STREAM representa TCP
    int sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd < 0)
    {
        perror("Error cannot open socket");
        exit(1);
    }

    addr.sin_family = AF_INET;
    addr.sin_port = htons(portno); 

    if (inet_pton(AF_INET, "127.0.0.1", &addr.sin_addr) <= 0)
    {
        printf(
            "\nInvalid address/ Address not supported \n");
        return -1;
    }

    if (( connect(sockfd, (struct sockaddr *)&addr, sizeof(addr))) < 0)
    {
        printf("\nConnection Failed \n");
        return -1;
    }

 scanf("%s", msg);
    while (strcmp(msg, "quit"))
    {   
        

        send(sockfd, msg, strlen(msg)+1, 0);
        int valread = read(sockfd, buffer, 1024);
        printf("%s\n",buffer);
        scanf("%s", msg);
    }
    
    close(sockfd);

    return 0;
}