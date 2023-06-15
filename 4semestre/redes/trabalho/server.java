package trabalho;

import java.io.*;
import java.net.*;
import java.util.*;

public class server {

    private static long serverStart;

    public static long getServerStart() {
        return serverStart;
    }

    public static void main(String[] args) {

        ServerSocket server = null;

        try {

            // Cria um ServerSocket para aguardar conexões na porta 5555
            server = new ServerSocket(5555);
            serverStart = System.currentTimeMillis();
            System.out.println("Servidor das aulas...");

            while (true) {

                // Aguarda uma conexão de um cliente
                Socket client = server.accept();
                System.out.println("aluno conectado: " + client.getInetAddress().getHostAddress());

                // Cria uma nova thread para tratar a conexão do cliente
                ClientThread clientThread = new ClientThread(client, serverStart);

                clientThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
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
    final static String FILE_DIRECTORY = "/home/antonio/Documentos/uni/4semestre/redes/trabalho/";

    private Socket clientSocket;

    protected static Hashtable<String, String> studentAttendance;
    protected static Hashtable<Integer, Question> questionArr;
    protected static ArrayList<String> fileNameArr;

    private HashMap<Integer, String> answerArr;
    private int questionCounter = 0;
    private String user = "aluno";
    private long serverstart;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getServerstart() {
        return serverstart;
    }

    public ClientThread(Socket clientSocket, long serverstart) {
        this.clientSocket = clientSocket;
        this.serverstart = serverstart;

        studentAttendance = new Hashtable<>();
        questionArr = new Hashtable<>();
        answerArr = new HashMap<>();
        fileNameArr = new ArrayList<>();

    }

    public void run() {

        try {
            // Obtém os streams de entrada e saída para comunicação com o cliente
            // Set up PrintWriter and BufferedReader here

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String line = input.readLine(), sublogin, log = null, outtext  =null;
            String[] logarr = line.split(" ");

            do {
                sublogin = logarr[0];
                if (sublogin.equalsIgnoreCase("iam")) {

                    log = (handleLogin(logarr[1]));
                }

            } while (!sublogin.equalsIgnoreCase("iam") && log.equals(null));

            output.println((log + "\nEND"));

            
           while ((line = input.readLine()) != null) {
            
               if (line.equals("exit")) {

                    break;
                }
                outtext = processCommand(line);
                System.out.println(outtext);
                output.println((outtext + "\nEND"));
            
            }

            // Fecha a conexão com o cliente
            clientSocket.close();
            System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*public void run() {
    try {
        String command;
        while ((command = reader.readLine()) != null) {
            // Adicionar verificação antes de processar o comando recebido
            
                // Processar comando

               if (line.equals("exit")) {

                    break;
                }
                output.println((processCommand(line) + "\nEND"));
            
        }

        // Fechar o socket e o leitor de dados após o loop
        reader.close();
        socket.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
} */

    private String processCommand(String command) {
        String response;
        String parts[] = command.split(" ");
        String commandname = parts[0];

        switch (commandname.toLowerCase()) {

            case "ask":
                response = handleAsk(parts[1]);

                break;
            case "answer":
                response = handleAnswer(parts[1], parts[2]);
                break;
            case "listquestions":

                response = handleListQuestions() + "ENDQUESTIONS";
                break;

            case "getfile":

                response = handleGetFile(Integer.parseInt(parts[1]));
                break;
            case "putfile":
               
                response = handlePutFile(parts[1], Integer.parseInt(parts[2]));
                break;

            case "listfiles":

                response = handleListFiles();
                break;

            default:
                return ("unknown command");
        }

        return response;
    }

    private String handleListFiles() {
        
        StringBuilder fileNames = new StringBuilder();

        if ((fileNameArr != null) && (fileNameArr.size()> 0)) {

            for (int i = 0; i < fileNameArr.size(); i++) {

                File f = new File(fileNameArr.get(i));

                if (f.isFile()) {
                    fileNames.append("(").append(i + 1).append(") ").append(f.getName()).append("\n");
                }
            }
        }

        return fileNames.toString();

    }

    private String handlePutFile(String filename, int bytes) {
 
        File file = new File(FILE_DIRECTORY + filename);
        FileOutputStream fos = null;
        InputStream in = null;
        byte[] bytearray = new byte[bytes];
        int totalBytesRead =0;

        int bytesRead;
        try {
            fos = new FileOutputStream(file);
            in=clientSocket.getInputStream();
            System.out.println("AAAAAAAAAAAAAAA");
            
            while ((bytesRead = in.read(bytearray)) > 0 && totalBytesRead < bytes) {
                fos.write(bytearray, 0, bytesRead);
                totalBytesRead += bytesRead;
            }

           
            // Adiciona o nome do ficheiro ao ArrayList 
            synchronized (fileNameArr) {
                fileNameArr.add(filename);
            }


        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
               
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Failed UPLOADED" + filename;
    }

    /*
    
    private String handlePutFile(String filename, int bytes) {
    File file = new File(FILE_DIRECTORY + filename);
    FileOutputStream fos = null;
    InputStream in = null;

    try {
        fos = new FileOutputStream(file);
        in = clientSocket.getInputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        int totalBytesRead = 0;

        while ((bytesRead = in.read(buffer)) > 0 && totalBytesRead < bytes) {
            fos.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
        }

        // Verifica se a quantidade de bytes lida é menor do que o esperado
        if (totalBytesRead < bytes) {
            throw new IOException("A leitura do arquivo foi incompleta.");
        }

        // Adiciona o nome do arquivo ao ArrayList
        synchronized (fileNameArr) {
            fileNameArr.add(filename);
        }

        return "UPLOADED " + filename;
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (in != null)
                in.close();

            if (fos != null)
                fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    return "FAILED TO UPLOAD " + filename;
}
    
    
    
    
    
    
    
    
    
    String filename = parts[1];
    int fileSize = Integer.parseInt(parts[2]);

    byte[] fileContent = new byte[fileSize];
    int bytesRead = socket.getInputStream().read(fileContent, 0, fileSize);

    // Salvar o arquivo recebido
    try (FileOutputStream fos = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos)) {
        bos.write(fileContent, 0, bytesRead);
    }

    response = "Uploaded " + filename;
    break; */

    /*
     * private String handlePutFile(String filename, int bytes) {
     * File file = new File(FILE_DIRECTORY + filename);
     * FileOutputStream fos = null;
     * InputStream is = null;
     * BufferedOutputStream bos = null;
     * byte[] mybytearray = new byte[bytes];
     * try {
     * fos = new FileOutputStream(file);
     * is = clientSocket.getInputStream();
     * bos = new BufferedOutputStream(fos);
     * 
     * int bytesRead = is.read(mybytearray, 0, mybytearray.length);
     * bos.write(mybytearray, 0, bytesRead);
     * bos.flush(); // Certifique-se de que todos os dados sejam gravados no disco
     * 
     * return "UPLOADED" + filename;
 */

    private String handleGetFile(int fileIndex) {
        
        String filename=fileNameArr.get(fileIndex);
        
        File file = new File(FILE_DIRECTORY + filename);
        FileInputStream fis = null;
        OutputStream os = null;


        try {
            fis = new FileInputStream(file);
            os = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(os, true);

           
            writer.println("FILE " + fileIndex +" "+ filename+" "+ (file.length()));


            byte[] mybytearray = new byte[(int) file.length()];
            int bytesRead;
            while(( bytesRead = fis.read(mybytearray, 0, mybytearray.length))>0){

                os.write(mybytearray, 0, bytesRead);

            }

            
            
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (os != null)
                    os.close();
                
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "DOWNLOADED " + filename;
    }

    /*
     * private String handleGetFile(String filename) {
     * File file = new File(FILE_DIRECTORY + filename);
     * FileInputStream fis = null;
     * BufferedInputStream bis = null;
     * OutputStream os = null;
     * try {
     * fis = new FileInputStream(file);
     * bis = new BufferedInputStream(fis);
     * os = clientSocket.getOutputStream();
     * 
     * byte[] mybytearray = new byte[(int) file.length()];
     * int bytesRead = bis.read(mybytearray, 0, mybytearray.length);
     * os.write(mybytearray, 0, bytesRead);
     * os.flush();
     * 
     * } catch (IOException e) {
     * e.printStackTrace();
     * } finally {
     * try {
     * if (os != null)
     * os.close();
     * if (bis != null)
     * bis.close();
     * if (fis != null)
     * fis.close();
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * return "DOWNLOADED " + filename;
     * }
     */

    private String handleListQuestions() {
        StringBuilder response = new StringBuilder();

        for (int i = 1; i <= questionArr.size(); i++) {

            Question question = questionArr.get(i);

            response.append("(" + i + ") " + question.getQuestion() + "?\n");

            if (answerArr.get(i) != null) {
                response.append(getUser() + " " + answerArr.get(i) + "\n");
                response.append("professor " + question.getAnswer() + "\n");
            } else if (getUser().equalsIgnoreCase("professor")) {
                response.append("professor " + question.getAnswer() + "\n");
            } else {
                response.append("(Not Answered)\n");
            }
        }

        return response.toString();

    }

    private String handleAnswer(String number, String parameters) {

        int questionnum = Integer.parseInt(number);
        Question question = questionArr.get(questionnum);

        if (question == null) {

            return ("não existe pergunta Nº " + questionnum);
        }

        if (getUser().equalsIgnoreCase("professor")) {

            question.setAnswer(parameters);
            questionArr.put(questionnum, question);

        } else {
            answerArr.put(questionnum, parameters);
        }

        return "REGISTERED QUESTION " + questionnum;
    }

    private String handleAsk(String parameters) {
        int i = parameters.indexOf("?");

        String question = parameters.substring(0, i);
        questionCounter++;

        if (getUser().equalsIgnoreCase("professor")) {

            Question newQuestion = new Question(question, parameters.substring(i));
            questionArr.put(questionCounter, newQuestion);

        } else {
            Question newQuestion = new Question(question, null);
            questionArr.put(questionCounter, newQuestion);

        }
        return "QUESTION " + questionCounter + " " + parameters.substring(0, i) + "?";
    }

    private String handleLogin(String parameters) {

        long studentdelay = System.currentTimeMillis() - getServerstart();

        String status;
        studentdelay /= 60000;

        switch (parameters) {
            case "professor":

                setUser("professor");
                break;

            default:
                if (studentdelay < 20) {
                    status = "Present";
                } else if (studentdelay < 45) {
                    status = "Late";

                } else {
                    status = "Absent";

                }
                setUser(parameters);
                studentAttendance.put(parameters, status);
                break;

        }

        return "HELLO " + parameters;
    }

}