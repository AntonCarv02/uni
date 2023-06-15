package trabalho;

import java.io.*;
import java.net.*;

public class Client {
    final static String FILE_DIRECTORY = "/home/antonio/Documentos/uni/4semestre/redes/trabalho/";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555); // Connect to the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            

            boolean running = true;
            while (running) {
                // user input
                System.out.println("Escolha o comando: ");
                String command = userInput.readLine();
               
                // Send command to the server
                writer.println(command);
                


                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    running = false;


                }else if (command.startsWith("PUTFILE")) {
                    


                    String[] parts = command.split(" ");
                    String filename = parts[1];
                    File file = new File(FILE_DIRECTORY+filename);
                    int fileSize = Integer.parseInt(parts[2]);

                    
                    byte[] fileContent = new byte[fileSize];
                    InputStream is = new FileInputStream(file);
                    OutputStream out = socket.getOutputStream();
                    System.out.println("aaaaaaaa");
                    int count;
                    while ((count = is.read(fileContent)) > 0) {
                        out.write(fileContent, 0, count);
                    }

                    out.close();
                    is.close();

                    /*else if (command.startsWith("PUTFILE")) {
                        String[] parts = command.split(" ");
                        String filename = parts[1];
                    
                        File file = new File(filename);
                        int fileSize = (int) file.length();
                    
                        // Enviar comando e tamanho do arquivo para o servidor
                        writer.println(command + " " + fileSize);
                    
                        byte[] fileContent = new byte[fileSize];
                        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                            bis.read(fileContent, 0, fileSize);
                        }
                    
                        // Enviar conteúdo do arquivo para o servidor
                        socket.getOutputStream().write(fileContent, 0, fileSize);
                        socket.getOutputStream().flush();
                    } */




                } else if (command.startsWith("GETFILE")) {
                    


                    String response= reader.readLine();
                    
                    if(response.startsWith("FILE")){
                        
                        String[] parts= response.split(" ");
                        int fileIndex = Integer.parseInt(parts[1]);
                        String filename = parts[2];
                        int fileSize = Integer.parseInt(parts[3]);

                        
                        File file = new File(filename);
                        FileOutputStream fos = new FileOutputStream(file);
                        InputStream in = socket.getInputStream();;
                        
                        // Ler e salvar o conteúdo do arquivo recebido
                        byte[] fileContent = new byte[fileSize];
                        int bytesRead;

                        while ((bytesRead = in.read(fileContent)) > 0) {
                            fos.write(fileContent, 0, bytesRead);
                        }

                        fos.close();
                        in.close();

                        System.out.println("File " + fileIndex +" " + filename + " " +fileSize);
                        System.out.println(fileContent);
                    }
                } 

                
                
                // Process the server's response
                
                String response=null;
                while ((response = reader.readLine()) != null && !response.equals("END") ) {

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
