package trabalho;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555); // Connect to the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner userInput = new Scanner(System.in);

            boolean running = true;
            while (running) {
                // user input
                System.out.print("Enter your command: ");
                String command = userInput.nextLine();

                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    running = false;

                }

                // Send command to the server
                writer.println(command);

                // Process the server's response
                String response;
                while ((response = reader.readLine()) != null) {
                    System.out.println(response);

                }
            }

            // Close the connections
            writer.close();
            reader.close();
            userInput.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientThread extends Thread {

    private Socket socket;
    private BufferedReader input; // private PrintWriter output;

    public ClientThread(Socket s) throws IOException {

        this.socket = s;

        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }// this.output = new PrintWriter(socket.getOutputStream(), true);

@Override 
    public void run() {

        try {

        while(true) {

            String response = input.readLine(); System.out.println(response);

        } 
        }catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally {

            try {   
                input.close(); 
            } catch (Exception e) { 
                e.printStackTrace();
            }
        }
    }
}