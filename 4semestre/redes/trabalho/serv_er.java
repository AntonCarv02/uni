package trabalho;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class serv_er {
    public static void main(String[] args) {

        ServerSocket server = null;
        ArrayList<String> Logins = new ArrayList<String>();

        try {


            // Cria um ServerSocket para aguardar conexões na porta 5555
            server = new ServerSocket(5555);
            System.out.println("Servidor...");
            

            while (true) {

                // Aguarda uma conexão de um cliente
                Socket client = server.accept();
                System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());



                // Cria uma nova thread para tratar a conexão do cliente
                ClientThread clientThread= new ClientThread(client);

                clientThread.start();
            
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server!= null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



class ClientThread extends Thread {
    private Socket clientSocket;



    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }




    public void run() {


        try {
            // Obtém os streams de entrada e saída para comunicação com o cliente
            //Set up PrintWriter and BufferedReader here
            
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            
            // Lê e processa os dados recebidos do cliente
           
            // Envia uma resposta ao cliente
                
        



            // Fecha a conexão com o cliente
            clientSocket.close();
            System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

