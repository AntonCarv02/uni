package trabalho;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
 
public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";
    public static final String ANSI_RESET = "\u001B[0m";
    private ArrayList<String> Logins = new ArrayList<String>();

    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
           
            socket = server.accept();
            System.out.println(ANSI_ORANGE+"Client accepted"+ANSI_RESET);
 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
 
            String[] line= new String[1024];
 
            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF().split(" ");
                    if(line[0]!= "IAM")
                    {
                        System.out.println(ANSI_ORANGE+ "HELLO" + line[1]+ ANSI_RESET);
                    }
 
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
 
            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }



    
    public static void main(String args[])
    {
        Server server = new Server(5001);
    }
}