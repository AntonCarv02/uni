package trabalho;

// A Java program for a Client
import java.io.*;
import java.net.*;
 
public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    BufferedReader input= new BufferedReader(new InputStreamReader(System.in));
    private DataOutputStream out = null;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE= "\u001B[34m";
 
    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            
 
            // takes input from terminal
        
            // sends output to the socket
            out = new DataOutputStream(
                socket.getOutputStream());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }
        
 
        // string to read message from input
        String line = "";
 
        // keep reading until "Over" is input
        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
            }
            catch (IOException i) {
                System.out.println(i);
            }
        }
 
        // close the connection
        try {                                                                                                                            
            input.close();
            out.close();
            socket.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5001);
    }
}