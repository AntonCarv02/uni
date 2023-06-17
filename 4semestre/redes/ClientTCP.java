import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555); // Conecta ao servidor na porta 5555

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            byte[] buffer = new byte[1024];
            int bytesRead;
            boolean logged = false;
            String response = "";
            String message = "";
            while (true) {
                message = scanner.nextLine();
                if (message != "") {
                    // manda o comando
                    outputStream.write(message.getBytes());
                    outputStream.flush();

                    if (message.equalsIgnoreCase("exit")) {
                        break;// sai do loop
                    
                    } else if (message.startsWith("PUTFILE") && logged) {
                    
                        message = scanner.nextLine();
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        bytesRead = inputStream.read(buffer);
                        response = new String(buffer, 0, bytesRead);
                        System.out.println(response);
                    
                    } else if (message.equals("LISTQUESTIONS") && logged) {
                        boolean IsFound = false;
                        while (!IsFound) {
                            bytesRead = inputStream.read(buffer);
                            response = new String(buffer, 0, bytesRead);
                            System.out.print(response);
                            IsFound = response.contains("ENDQUESTIONS");
                        }
                    } else if (message.equals("LISTFILES") && logged) {
                        boolean IsFound = false;
                        while (!IsFound) {
                            bytesRead = inputStream.read(buffer);
                            response = new String(buffer, 0, bytesRead);
                            System.out.print(response);
                            IsFound = response.contains("ENDFILES");
                        }
                    } else if (message.startsWith("GETFILE") && logged) {
                        
                        int sz = 0;
                        String filecontent = "";

                        bytesRead = inputStream.read(buffer);
                        response = new String(buffer, 0, bytesRead);


                        System.out.println(response);
                        String[] parts = response.split(" ");
                        sz = Integer.parseInt(parts[3]);



                        while (sz != filecontent.length()) {
                            bytesRead = inputStream.read(buffer);
                            response = new String(buffer, 0, bytesRead);
                            filecontent = filecontent + response;
                        }
                        System.out.println(filecontent);





                    } else {
                        bytesRead = inputStream.read(buffer);
                        response = new String(buffer, 0, bytesRead);
                        System.out.println(response);
                        if (response.startsWith("HELLO")) {
                            logged = true;
                        }
                    }
                }
                response = "";
                message = "";
                Arrays.fill(buffer, (byte) 0);

            }
            scanner.close();
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}