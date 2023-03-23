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
    struct hostent *server;
    int portno = 1599;
    char buffer[2048];
    char msg[] = {"GET /~pp/redes/grande.txt\n\n"};

    // AF_INET representa IPv4; SOCK_STREAM representa TCP
    int sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd < 0)
    {
        perror("Error cannot open socket");
        exit(1);
    }

    server = gethostbyname("www.di.uevora.pt");
    if (inet_pton(AF_INET, "127.0.0.1", &addr.sin_addr) <= 0)
    {
        printf("\nInvalid address/ Address not supported \n");
        return -1;
    }

    if (server == NULL)
    {
        perror("Error no such host");
        exit(1);
    }

    bzero((char *)&addr, sizeof(addr));

    addr.sin_family = AF_INET;

    bcopy((char *)server->h_addr_list[0], (char *)&addr.sin_addr.s_addr, server->h_length);

    addr.sin_port = htons(portno);

    if ((connect(sockfd, (struct sockaddr *)&addr, sizeof(addr))) < 0)
    {
        printf("\nConnection Failed \n");
        return -1;
    }

    send(sockfd, msg, strlen(msg), 0);

    read(sockfd, buffer, 1024);

    printf("%s", buffer);

    return 0;
}