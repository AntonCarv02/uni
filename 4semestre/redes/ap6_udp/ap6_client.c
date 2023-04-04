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

    struct sockaddr_in servaddr;
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

    memset(&servaddr, 0, sizeof(servaddr));

    // Filling server information
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(portno);
    servaddr.sin_addr.s_addr = INADDR_ANY;

    int n;
    socklen_t len;

    scanf("%s", msg);

    sendto(sockfd, msg, strlen(msg), MSG_CONFIRM, (const struct sockaddr *)&servaddr, sizeof(servaddr));
    
    n = recvfrom(sockfd, buffer, 1024, MSG_WAITALL, (struct sockaddr *)&servaddr, &len);
    buffer[n] = '\0';

    printf("from server: %s", buffer);
    
    close(sockfd);

    return 0;
}