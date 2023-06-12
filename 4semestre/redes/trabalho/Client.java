package trabalho;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555); // Connect to the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            boolean running = true;
            while (running) {
                // Prompt user for input
                System.out.print("Enter your command: ");
                String command = userInput.readLine();

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
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}