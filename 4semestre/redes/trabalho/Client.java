package trabalho;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    final static String FILE_DIRECTORY = "/home/antonio/Documentos/uni/4semestre/redes/trabalho/";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555); // Connect to the server
            InputStream reader = (socket.getInputStream());
            OutputStream writer =socket.getOutputStream();
            Scanner s =new Scanner(System.in);
            byte[] msg = new byte[1024]; 
            String command;
            int read;

            boolean running = true;
            while (running) {
                // user input
                System.out.println("Escolha o comando: ");
                command = s.nextLine();
                
                // Send command to the server
                writer.write(command.getBytes());
                writer.flush();

                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    running = false;


                }else if (command.startsWith("PUTFILE")) {
                    


                    String[] parts = command.split(" ");
                    String filename = parts[1];
                    File file = new File(/*FILE_DIRECTORY+ */ filename);
                    int fileSize = Integer.parseInt(parts[2]);

                    if(!file.exists()){
                        throw new Exception("num ah fichero moh" );
                    }
                    
                    byte[] fileContent = new byte[fileSize];
                    InputStream fis = new FileInputStream(file);

                    
                    int count;
                    while((count = fis.read(fileContent))>0){
                        writer.write(fileContent, 0, count);
                    }
                    
                    

                    fis.close();
                    read = reader.read(msg);
                    command = new String(msg, 0, read);
                    System.out.println(command);
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
                    


                    read = reader.read(msg);
                    String response = new String(msg, 0, read);
                    
                    
                    if(response.startsWith("FILE")){
                        
                        String[] parts= response.split(" ");
                     //   int fileIndex = Integer.parseInt(parts[1]);
                        String filename = parts[2];
                        int fileSize = Integer.parseInt(parts[3]);

                        System.out.println(response);

                        File file = new File(filename);
                        FileOutputStream fos = new FileOutputStream(file);
                        
                        // Ler e salvar o conteúdo do arquivo recebido
                        byte[] fileContent = new byte[fileSize];
                        int bytesRead;
                        
                        //#####
                       

                        while (fileSize != fileContent.length) {
                             bytesRead = reader.read(fileContent);
                            fos.write(fileContent, 0, bytesRead);
                        }
                        //#####
                        fos.close();
                        

                        
                        System.out.println(fileContent.toString());

                    }else{
                        System.out.println("falta ERRO");
                    }
                } 

                
                
                // Process the server's response
                
                String response="";
                read= reader.read(msg);
                response = new String(msg, 0, read);

                while (!response.equals("END") ) {
                    
                    System.out.println(response);
				    
                    read=reader.read(msg);
                    response = new String(msg, 0, read);
                }

            }
            
            // Close the connections
            writer.close();
            reader.close();
            s.close();
            socket.close();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
