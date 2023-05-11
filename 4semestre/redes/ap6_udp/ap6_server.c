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
#include "ap6_server.h"

int main(int argc, char const *argv[])
{

    struct sockaddr_in srv_addr, cli_addr;
    int portno = 9998;
    char buffer[1024];
    char msg[1024];

    // AF_INET representa IPv4; SOCK_STREAM representa TCP
    int sockfd = socket(AF_INET, SOCK_DGRAM, 0);

    if (sockfd < 0)
    {
        perror("Error cannot open socket");
        exit(1);
    }


    memset(&srv_addr, 0, sizeof(srv_addr));
    memset(&cli_addr, 0, sizeof(cli_addr));
       
    // Filling server information
    srv_addr.sin_family    = AF_INET; // IPv4
    srv_addr.sin_addr.s_addr = INADDR_ANY;
    srv_addr.sin_port = htons(portno);


    if ( bind(sockfd, (const struct sockaddr *)&srv_addr, 
            sizeof(srv_addr)) < 0 )
    {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }
       
    socklen_t len;
    int n;
   
    len = sizeof(cli_addr);  //len is value/result
    
    
    n = recvfrom(sockfd, buffer, 1024, MSG_WAITALL, ( struct sockaddr *) &cli_addr, &len);
    
    buffer[n] = '\0';
    
    sendto(sockfd, buffer, strlen(buffer), MSG_CONFIRM, (const struct sockaddr *) &cli_addr,len);
    
    printf("%s", buffer);
    
    close(sockfd);

    return 0; 
}
