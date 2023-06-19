package trabalho;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class server {
    private static final long TEMPO_ESPERA = 60 * 1000;// 1 minuto
    private static final String FILE = "trabalho/save/SavedData.dat";
    private static long serverStart;

    public static Hashtable<String, String> studentAttendance;
    public static ArrayList< Question> questionArr;
    public static ArrayList<String> fileNameArr;

    public static long getServerStart() {
        return serverStart;
    }

    public static void main(String[] args) {

        ServerSocket server = null;
        studentAttendance = new Hashtable<>();
        questionArr = new ArrayList<>();
        fileNameArr = new ArrayList<>();

        try {

            // Load data from file if it exists
            loadPersistedData();

            // Cria um ServerSocket para aguardar conexões na porta 5555
            server = new ServerSocket(5555);
            serverStart = System.currentTimeMillis();
            System.out.println("Servidor das aulas...");

            // Create a separate thread for data persistence
            Thread persistenceThread = new saveThread();
            persistenceThread.start();

            while (true) {

                // Aguarda uma conexão de um cliente
                Socket client = server.accept();
                System.out.println("aluno conectado: " + client.getInetAddress().getHostAddress());

                // Cria uma nova thread para tratar a conexão do cliente
                ClientThread clientThread = new ClientThread(client, serverStart, studentAttendance, questionArr,
                        fileNameArr);

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

    @SuppressWarnings("unchecked")
    private static void loadPersistedData() {
        try (FileInputStream fis = new FileInputStream(FILE);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            // Read the persisted data from the file and update the questionArr and
            // answerArr
            questionArr = (ArrayList<Question>) ois.readObject();
            fileNameArr = (ArrayList<String>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
            // Ignore errors if the file doesn't exist or if deserialization fails
            System.out.println("sem dados guardados.");
        }
    }

    public static class saveThread extends Thread {

        public void run() {
            System.out.println("saving....");
            persistDataRegularly();

        }

        private void persistDataRegularly() {
            while (true) {
                try {
                    Thread.sleep(TEMPO_ESPERA);
                    persistDataToFile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void persistDataToFile() {
            try (FileOutputStream fos = new FileOutputStream(FILE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                // Write the questionArr and answerArr to the file for persistence
                oos.writeObject(questionArr);
                oos.writeObject(fileNameArr);
                oos.flush();
                System.out.println("DADOS COPIADOS, Ficheiro: " + FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}












class ClientThread extends Thread {
    final static String FILE_DIRECTORY = "trabalho/";
    final static String SAVE_DIRECTORY ="trabalho/save/";

    private Socket clientSocket;

    protected Hashtable<String, String> studentAttendance;
    protected ArrayList< Question> questionArr;
    protected ArrayList<String> fileNameArr;

    private HashMap<Integer, String> answerArr;
    private int questionCounter;
    private String user;
    private long serverstart, timeElapsed;

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
    public long getTime() {
        return timeElapsed;
    }

    public void setTime() {
        this.timeElapsed=System.currentTimeMillis();
    }
    

    public ClientThread(Socket clientSocket, long serverstart, Hashtable<String, String> studentAttendance,
            ArrayList< Question> questionArr, ArrayList<String> fileNameArr) throws IOException {
        this.clientSocket = clientSocket;
        this.serverstart = serverstart;

        this.studentAttendance = studentAttendance;
        this.questionArr = questionArr;
        this.fileNameArr = fileNameArr;

        answerArr = new HashMap<>();

        input = clientSocket.getInputStream();
        output = clientSocket.getOutputStream();

        setTime();
    }

    public void run() {
        
        try {
            // Obtém os streams de entrada e saída para comunicação com o cliente
            // Set up PrintWriter and BufferedReader here
            byte[] msg = new byte[1024];
            int read ;
            String line, sublogin, log = null, outtext = null;
            


            do {
                read = input.read(msg);
                line = new String(msg, 0, read);
                String[] logarr = line.split(" ");

                sublogin = logarr[0];
                if (sublogin.equalsIgnoreCase("iam")) {

                    log = (handleLogin(logarr[1]));
                }

                
                output.write((log + "\nEND").getBytes());
                output.flush();
            } while (!sublogin.equalsIgnoreCase("iam") && log==(null));


            loadPersistedData();

            


            while ((read = input.read(msg)) != -1) {

                line = new String(msg, 0, read, StandardCharsets.UTF_8);

                if (line.equals("exit")) {
                    output.write("GOODBYE END".getBytes());
                    break;
                }

                outtext = processCommand(line) + "\nEND";
                System.out.println(outtext);

                output.write((outtext).getBytes());
                output.flush(); //
                
                persistDataRegularly();
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
        String args = "";
        if (command.toLowerCase().startsWith("LIST")) {
            commandname = command;

        } else {
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

                fileNames.append("(" + (i + 1) + ") " + (f.getName()).substring(1) + "\n");

            }
        }

        return fileNames.toString();

    }

    private String handlePutFile(String args) {

        String[] parts = args.split(" ");
        String filename = "s_" + parts[0];
        int bytes = Integer.parseInt(parts[1]);

        File file = new File(FILE_DIRECTORY + filename);
        FileOutputStream fos = null;
        InputStream in = null;

        byte[] bytearray = new byte[bytes];
        int read;

        try {
            fos = new FileOutputStream(file);
            in = clientSocket.getInputStream();

            read = in.read(bytearray);

            fos.write(bytearray, 0, read);

            // Adiciona o nome do ficheiro ao ArrayList
            synchronized (fileNameArr) {
                fileNameArr.add(filename);
            }

        } catch (IOException e) {
            System.out.println("Failed UPLOAD of" + filename);
            e.printStackTrace();

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        }

        return "UPLOADED " + filename;
    }

    private String handleGetFile(int fileIndex) {

        String filename = fileNameArr.get(fileIndex - 1);

        File file = new File(FILE_DIRECTORY + filename);
        FileInputStream fis = null;
        OutputStream os = null;

        try {
            fis = new FileInputStream(file);
            os = clientSocket.getOutputStream();

            os.write(("FILE" + " " + fileIndex + " " + filename + " " + (file.length()) + " ")
                    .getBytes(StandardCharsets.UTF_8));

            byte[] mybytearray = new byte[(int) file.length()];
            int bytesRead;

            String readyString = "";
            do {
                int ready = input.read(mybytearray);
                readyString = new String(mybytearray, 0, ready);
                System.out.println(readyString);

            } while (!readyString.equals("READY"));

            while ((bytesRead = fis.read(mybytearray)) > 0) {

                os.write(mybytearray, 0, bytesRead);
                os.flush(); //
            }

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        }

        return "DOWNLOADED " + filename;
    }

    private String handleListQuestions() {
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < questionArr.size(); i++) {

            Question question = questionArr.get(i);

            response.append("\n(" + (i + 1) + ") " + question.getQuestion() + "?\n");

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
        
        Question question;
        String parameters;
        int questionnum = args.charAt(0) - 49;

        try {
            parameters= args.substring(2);
        } catch (Exception e) {
            // TODO: handle exception
            return "formato errado";
        }

        try {
                   
            question = questionArr.get(questionnum);

        } catch ( Exception e) {
            // TODO: handle exception
            return ("não existe pergunta Nº " + questionnum);
        }
       

        if (getUser().equalsIgnoreCase("professor")) {

            question.setAnswer(parameters);
            questionArr.add( question);

        } else {
            answerArr.put(questionnum, parameters);
        }

        return "REGISTERED ANSWER TO QUESTION " + (questionnum + 1);
    }

    private synchronized String handleAsk(String parameters) {

        int i = parameters.indexOf("?");
        String question ="";

        try {
            question = parameters.substring(0, i);
            
        } catch (Exception e) {
            // TODO: handle exception
            return "formato errado";
        }
        

        questionCounter = questionArr.size();

        if (getUser().equalsIgnoreCase("professor")) {

            Question newQuestion = new Question(question, null);
            questionArr.add( newQuestion);
            
        } else {
            Question newQuestion = new Question(question, null);
            questionArr.add( newQuestion);

        }
        return "QUESTION " + (questionCounter + 1) + " " + parameters.substring(0, i) + "?";
    }

    private String handleLogin(String parameters) {

        long studentdelay = System.currentTimeMillis() - getServerstart();

        String status= "";;
        studentdelay /= 60000;

        switch (parameters) {
            case "professor":

            setUser("professor");
                break;

            default:
                if (studentdelay < 1) {
                    status = "Present";
                } else if (studentdelay < 2) {
                    status = "Late";

                } else {
                    status = "Absent";

                }
                setUser(parameters);
                studentAttendance.put(parameters, status);
                break;

        }

        return "HELLO " + parameters + " " + status;

    }

    @SuppressWarnings("unchecked")
    private  void loadPersistedData() {
        try (FileInputStream fis = new FileInputStream(SAVE_DIRECTORY + getUser()+".dat");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            // Read the persisted data from the file and update the questionArr and
           
            answerArr = (HashMap<Integer, String>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            // Ignore errors if the file doesn't exist or if deserialization fails
            System.out.println("Sem dados.");
        }
    }

    private void persistDataRegularly() {
        if (System.currentTimeMillis()-getTime()>1000*30) {
            
            persistDataToFile();
            setTime();
            
        }
    }

    private  void persistDataToFile() {
        try (FileOutputStream fos = new FileOutputStream(SAVE_DIRECTORY+getUser()+".dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Write the questionArr and answerArr to the file for persistence
            oos.writeObject(answerArr);
            oos.flush();
            System.out.println("Data persisted to file: " + SAVE_DIRECTORY+getUser()+".dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}