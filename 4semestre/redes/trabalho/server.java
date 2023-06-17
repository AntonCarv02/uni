package trabalho;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class server {
    private static final long PERSISTENCE_INTERVAL_MS = 5 * 60 * 1000; // 5 minutes
    private static long serverStart;
    private static boolean shouldPersistData = true;

    public static long getServerStart() {
        return serverStart;
    }

    public static void main(String[] args) {

        ServerSocket server = null;

        try {

            // Load data from file if it exists
            //loadPersistedData();

            // Cria um ServerSocket para aguardar conexões na porta 5555
            server = new ServerSocket(5555);
            serverStart = System.currentTimeMillis();
            System.out.println("Servidor das aulas...");

            // Create a separate thread for data persistence
            //Thread persistenceThread = new Thread(Server::persistDataRegularly);
            //persistenceThread.start();

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

    private InputStream input;
    private OutputStream output;




    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getServerstart() {
        return serverstart;
    }





    public ClientThread(Socket clientSocket, long serverstart) throws IOException {
        this.clientSocket = clientSocket;
        this.serverstart = serverstart;

        studentAttendance = new Hashtable<>();
        questionArr = new Hashtable<>();
        answerArr = new HashMap<>();
        fileNameArr = new ArrayList<>();


        input = clientSocket.getInputStream();
        output =clientSocket.getOutputStream();
    }

    public void run() {

        try {
            // Obtém os streams de entrada e saída para comunicação com o cliente
            // Set up PrintWriter and BufferedReader here
            byte[] msg = new byte[1024];
            int read= input.read(msg);
            String line = new String(msg, 0, read), sublogin, log = null, outtext  =null;
            String[] logarr = line.split(" ");

            do {
                sublogin = logarr[0];
                if (sublogin.equalsIgnoreCase("iam")) {

                    log = (handleLogin(logarr[1]));
                }

            } while (!sublogin.equalsIgnoreCase("iam") && log.equals(null));

            output.write((log + "\nEND").getBytes());
            output.flush();
            

            while ((read= input.read(msg)) != -1) {
               
                line = new String(msg, 0, read, StandardCharsets.UTF_8);
                
                if (line.equals("exit")) {
                    output.write("GOODBYE END".getBytes());
                    break;
                }


                outtext = processCommand(line)+ "\nEND";
                System.out.println(outtext);

                output.write((outtext).getBytes());
                output.flush(); //
                
            }

            // Fecha a conexão com o cliente
            clientSocket.close();
            System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }







    private String processCommand(String command) {
        String response;
        String commandname;
        String args="";
        if(command.toLowerCase().startsWith("LIST")){
            commandname= command;
            
        }else{
            int index = command.indexOf(" ");
            if (index != -1) {
                commandname = command.substring(0, index);
                args = command.substring(index + 1);
            } else {
                commandname = command;
                args = "";
            }   
        }
        
        switch (commandname.toLowerCase()) {

            case "ask":
                response = handleAsk(args);

                break;
            case "answer":
                response = handleAnswer(args);
                break;
            case "listquestions":

                response = handleListQuestions() + "ENDQUESTIONS";
                break;

            case "getfile":

                response = handleGetFile(Integer.parseInt(args));
                break;
            case "putfile":
               
                response = handlePutFile(args);
                break;

            case "listfiles":

                response = handleListFiles();
                break;

            default:
                return ("unknown command");
        }
        /*
         * 
         * adicionar limitador de bytes
         * 
         */
        return response;
    }

    private String handleListFiles() {
        
        StringBuilder fileNames = new StringBuilder();

        if ((fileNameArr != null)) {

            for (int i = 0; i < fileNameArr.size(); i++) {

                File f = new File(fileNameArr.get(i));

                fileNames.append("("+(i + 1)+") "+(f.getName()).substring(1)+"\n");
                
            }
        }

        return fileNames.toString();

    }

    private String handlePutFile(String args) {

        String[] parts = args.split(" "); 
        String filename = "s_"+parts[0];
        int bytes = Integer.parseInt(parts[1]);

        File file = new File(FILE_DIRECTORY + filename);
        FileOutputStream fos = null;
        InputStream in = null;

        byte[] bytearray = new byte[bytes];
        int read;
        
        try {
            fos = new FileOutputStream(file);
            in=clientSocket.getInputStream();

            read = in.read(bytearray);

            fos.write(bytearray, 0, read);
            

            // Adiciona o nome do ficheiro ao ArrayList 
            synchronized (fileNameArr) {
                fileNameArr.add(filename);
            }


            
        } catch (IOException e) {
            System.out.println("Failed UPLOAD of" + filename);
            e.printStackTrace();
             
        } finally{
            if(fos != null){
                try {            
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
            
        return "UPLOADED " + filename;
    }

    private String handleGetFile(int fileIndex) {
        
        String filename=fileNameArr.get(fileIndex-1);
        
        File file = new File(FILE_DIRECTORY + filename);
        FileInputStream fis = null;
        OutputStream os = null;


        try {
            fis = new FileInputStream(file);
            os = clientSocket.getOutputStream();
            
            os.write(("FILE"+ " " + fileIndex +" "+ filename+" "+ (file.length())+" \n").getBytes(StandardCharsets.UTF_8));
            
            byte[] mybytearray = new byte[(int) file.length()];
            int bytesRead;


            while(( bytesRead = fis.read(mybytearray))>0){
                os.write(mybytearray, 0, bytesRead);
                os.flush();   //
            }
            
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();


        } finally{
            if(fis != null){
                try {            
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

        return "DOWNLOADED " + filename;
    }

    

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

    private String handleAnswer(String args) {
        
        String parameters = args.substring(2);
        int questionnum = args.charAt(0)-48;
        
       
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

/*/
    private static void loadPersistedData() {
        try (FileInputStream fis = new FileInputStream(FILE_DIRECTORY);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            // Read the persisted data from the file and update the questionArr and answerArr
            questionArr = (Hashtable<Integer, Question>) ois.readObject();
            answerArr = (HashMap<Integer, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore errors if the file doesn't exist or if deserialization fails
            System.out.println("No persisted data found.");
        }
    }

    private static void persistDataRegularly() {
        while (shouldPersistData) {
            try {
                Thread.sleep(PERSISTENCE_INTERVAL_MS);
                persistDataToFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void persistDataToFile() {
        try (FileOutputStream fos = new FileOutputStream(FILE_DIRECTORY);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Write the questionArr and answerArr to the file for persistence
            oos.writeObject(questionArr);
            oos.writeObject(answerArr);
            oos.flush();
            System.out.println("Data persisted to file: " + FILE_DIRECTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

}