package trabalho;

import java.io.*;
import java.net.*;
import java.util.*;

public class serv_er {

   
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
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);

                // Cria uma nova thread para tratar a conexão do cliente
                ClientThread clientThread = new ClientThread(client, serverStart, output);

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
    private Socket clientSocket;

    protected static Hashtable<String, String> studentAttendance;
    protected static Hashtable<Integer, Question> questionArr;

    private HashMap<Integer, String> answerArr;
    private int questionCounter;
    private String user = "aluno";
    private long serverstart;

    PrintWriter out;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getServerstart() {
        return serverstart;
    }

    public PrintWriter getOut() {
        return out;
    }

    public ClientThread(Socket clientSocket, long serverstart,PrintWriter out) {
        this.clientSocket = clientSocket;
        this.serverstart = serverstart;

        studentAttendance = new Hashtable<>();
        questionArr = new Hashtable<>();

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void run() {

        try {
            // Obtém os streams de entrada e saída para comunicação com o cliente
            // Set up PrintWriter and BufferedReader here

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            output.print("Login...");
            String line = input.readLine(), sublogin;
            int i = line.indexOf(" ");

            do {
                sublogin = line.substring(0, i);
                if (sublogin.equalsIgnoreCase("iam")) {

                    handleLogin(line.substring(i));
                }

            } while (!sublogin.equalsIgnoreCase("iam"));

            while (true) {

                // Lê e processa os dados recebidos do cliente
                line = input.readLine();

                if (line.equals("exit")) {
                    
                    break;
                }
                int index = line.indexOf(" ");

                // Envia uma resposta ao cliente
                output.println(processCommand(line.substring(0, index), line.substring(index)));

            }

            // Fecha a conexão com o cliente
            clientSocket.close();
            System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private String processCommand(String command, String parameters) throws Exception {
        String response;

        switch (command) {

            case "ask":
            
                response = handleAsk(parameters);
                showAll(response);
                break;
            case "answer":

                response = handleAnswer(parameters);
                break;
            case "listquestions":
                
                response= "ENDQUESTIONS"+handleListQuestions();
                break;
            default:
                throw new Exception("unknown command");
        }

        return response;
    }

    private synchronized void showAll(String response) {

        out.print(response);
    }

    private String handleListQuestions() {
        StringBuilder response = new StringBuilder();

        for (int i =0;i<questionArr.size();i++) {

            Question question = questionArr.get(i);

            response.append("("+i+") "+question.getQuestion()+"\n");

            if (answerArr.get(i) != null) {
                response.append(getUser()+" "+answerArr.get(i)+"\n");
                response.append("professor " +question.getAnswer()+"\n");
            } else {
                response.append("(Not Answered)\n");
            }
        }

        return response.toString();

    }

    private String handleAnswer(String parameters) throws Exception {

        int questionnum = parameters.charAt(0);
        Question question = questionArr.get(questionnum);

        if (question == null) {

            throw new Exception("não existe pergunta Nº " + questionnum);
        }

        if (getUser().equalsIgnoreCase("professor")) {

            question.setAnswer(parameters.substring(2));
            questionArr.put(questionnum, question);

        } else {
            answerArr.put(questionnum, parameters.substring(2));
        }

        return "REGISTERED QUESTION "+ questionnum;
    }

    private String handleAsk(String parameters) {
        int i = parameters.indexOf("?");

        String question = parameters.substring(0, i);

        if (getUser().equalsIgnoreCase("professor")) {

            Question newQuestion = new Question(question, parameters.substring(i));
            questionArr.put(questionCounter++, newQuestion);

        } else {
            Question newQuestion = new Question(question, null);
            questionArr.put(questionCounter++, newQuestion);

        }
        return "QUESTION " + questionCounter + parameters.substring(0, i);
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

        return "HELLO" + parameters;
    }

}
