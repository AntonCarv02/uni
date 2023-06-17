import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Server {
    private static long serverStartTime; // Tempo de início do servidor
    private static mperguntas pMperguntas; // declara pMperguntas 
    private static Lock lock; // Declara lock para sincronizar
    private static List<mfiles> gfiles; //lista para os ficheiros
    private static List<String> presente;
    private static List<String> atrasado;
    private static boolean ServeOn;
    public static void main(String[] args) {
        serverStartTime = System.currentTimeMillis(); // Registra o tempo de início do servidor
        pMperguntas = new mperguntas(); // inicializa pMperguntas
        lock = new ReentrantLock(); // inicializa the lock
        gfiles = new ArrayList<mfiles>();// inicializa a lista de ficheiros
        presente = new ArrayList<String>();
        atrasado = new ArrayList<String>();
        ServeOn=true;
        

        TimerTask save = new TimerTask(){
            @Override
            public void run() {
                // Lógica para salvar os dados em um arquivo
                try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("perguntasrespostas.dat"))) {
                    outputStream.writeObject(pMperguntas.getPerguntas());
                    outputStream.writeObject(pMperguntas.getRepostas());

                    System.out.println("Salvado com sucesso!");
                } catch (IOException e) {
                    System.err.println("Erro ao salvar os dados: " + e.getMessage());
                }
            }
        };

        try {
            
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("perguntasrespostas.dat"))) {
                pMperguntas.setPerguntas((List<String>) inputStream.readObject());
                pMperguntas.setRepostas((List<ArrayList<resposta>>) inputStream.readObject());
    
                System.out.println("Dados recuperados do arquivo com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao recuperar os dados do arquivo: " + e.getMessage());
            }
    
            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("Servidor aguardando conexões...");

            while (ServeOn) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
                //save a cada 5minutos
                Timer t = new Timer();  
                t.schedule(save, 0, 5 * 60 * 1000);  
            
            }
            serverSocket.close();
            System.exit(0);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                byte[] buffer = new byte[1024];
                String response, studentName="", password;
                String message;
                boolean logged=false;
                while(true){
                    int bytesRead = inputStream.read(buffer);      
                    message = new String(buffer, 0, bytesRead);
                    if(message!=""){
                        String[] parts = message.split(" ");
                        if(message.equalsIgnoreCase("exit")){
                            break;
                        } 
                         
                            if (message.startsWith("IAM")) {
                                if (parts.length == 4 && parts[2].equals("WITHPASS")) {
                                    studentName = parts[1];
                                    password = parts[3];
                                    // Verificar a senha
                                    
                                    boolean validPassword = checkPassword(studentName, password);

                                    if (validPassword) {
                                        // Calcular tempo de atraso
                                        long currentTime = System.currentTimeMillis();
                                        long elapsedMinutes = (currentTime - serverStartTime) / (1000 * 60); // Tempo decorrido em minutos
                                        response = "HELLO " + studentName ;
                                        outputStream.write(response.getBytes());
                                        outputStream.flush();
                                        logged=true;
                                        
                                        if (elapsedMinutes < 20) {
                                            presente.add(studentName);
                                        } else if (elapsedMinutes >= 20 && elapsedMinutes < 45) {
                                            atrasado.add(studentName);
                                        } 
                                    
                                    
                                    } else {
                                        String errorResponse = "ERROR " + studentName;
                                        outputStream.write(errorResponse.getBytes());
                                        outputStream.flush();
                                    }
                                    
                                }
                            }
                           
                            if(message.startsWith("ASK")&&logged){
                                String pergunta="";
                                int s;
                                response="";
                                int tm = parts.length;
                                for(int i=1;i<tm;i++){
                                    pergunta=String.join(" ", pergunta, parts[i]);
                                }
                                lock.lock();
                                try {
                                    pMperguntas.addpergunta(pergunta);
                                    s = pMperguntas.getPerguntas().size();//retorna o nmr da pergunta
                                } finally {
                                    lock.unlock();
                                }
                                response="QUESTION "+s+":"+pergunta;
                                outputStream.write(response.getBytes());
                                outputStream.flush();       
                            }
                            else if (message.startsWith("ANSWER")&&logged){
                                String resposta="";
                                int nmrP= Integer.parseInt(parts[1]);
                                response="";
                                int tm = parts.length;
                                for(int i=2;i<tm;i++){
                                    resposta=String.join(" ", resposta, parts[i]);
                                }
                                lock.lock();
                                try {
                                    pMperguntas.addresposta(nmrP, studentName, resposta);
                                } finally {
                                    lock.unlock();
                                }
                                response="REGISTERED "+nmrP;
                                outputStream.write(response.getBytes());
                                outputStream.flush();       
                            }else if (message.startsWith("DELETEANSWER")&&logged){
                                int nmrP= Integer.parseInt(parts[1]);
                                int nmrQ= Integer.parseInt(parts[2]);
                                pMperguntas.getRepostas().get(nmrP-1).remove(nmrQ-1);
                                response="DELETED ANSWER "+ nmrQ;
                                outputStream.write(response.getBytes());
                                outputStream.flush();
                            }
                            else if (message.equals("LISTQUESTIONS")&&logged){
                                int sz = pMperguntas.getPerguntas().size();
                                    for(int i=0;i<sz;i++){
                                        response="("+(i+1)+") "+pMperguntas.getPerguntas().get(i)+"\n";
                                        outputStream.write(response.getBytes());
                                        outputStream.flush();
                                        if(pMperguntas.getRepostas().get(i).isEmpty()){
                                            response="    NOTANSWERED\n";
                                            outputStream.write(response.getBytes());
                                            outputStream.flush();
                                        }
                                        else{
                                            for (resposta n: pMperguntas.getRepostas().get(i)) {
                                                response="    ("+n.getAswerName()+") "+ n.getResposta()+"\n";
                                                outputStream.write(response.getBytes());
                                                outputStream.flush();
                                            }
                                        }    
                                    }
                                    response="ENDQUESTIONS"+"\n";
                                    outputStream.write(response.getBytes());
                                    outputStream.flush();
                            }
                            else if (message.startsWith("PUTFILE")&&logged){
                                String fn,content="";
                                int fsize=0,sz=0;
                                fn=parts[1];
                                fsize=Integer.parseInt(parts[2]);
                                while(sz!=fsize){
                                    bytesRead = inputStream.read(buffer);      
                                    message = new String(buffer, 0, bytesRead);
                                    content= content+ message;
                                    sz= content.length();
                                }
                                mfiles novo = new mfiles(fn, content);
                                gfiles.add(novo);
                                response="UPLOADED "+fn;
                                outputStream.write(response.getBytes());
                                outputStream.flush();
                            }
                            else if (message.equals("LISTFILES")&&logged){
                                for (int i=0;i<gfiles.size();i++){
                                    mfiles n=gfiles.get(i);
                                    response="    ("+(i+1)+") "+ n.getFname()+"\n";
                                    outputStream.write(response.getBytes());
                                    outputStream.flush();
                                }
                                response="ENDFILES"+"\n";
                                outputStream.write(response.getBytes());
                                outputStream.flush();
                            }
                            else if (message.startsWith("GETFILE")&&logged){
                                int file=Integer.parseInt(parts[1]);
                                response="FILE "+file +" "+gfiles.get(file-1).getFname()+" "+gfiles.get(file-1).getContent().length();
                                outputStream.write(response.getBytes());
                                outputStream.flush();
                                String inputString = gfiles.get(file-1).getContent();
                                int chunkSize = 1024;
                                int length = inputString.length();
                                int numOfChunks = (int) Math.ceil((double) length / chunkSize);
                                String[] chunks = new String[numOfChunks];
                                for (int i = 0; i < numOfChunks; i++) {
                                    int start = i * chunkSize;
                                    int end = Math.min(start + chunkSize, length);
                                    chunks[i] = inputString.substring(start, end);
                                }
                                for (String string : chunks) {
                                    outputStream.write(string.getBytes());
                                    outputStream.flush();  
                                }
                                                                    
                            }else{
                                response=" ";
                                outputStream.write(response.getBytes());
                                outputStream.flush();
                            }  
                    }else{
                        response=" ";
                        outputStream.write(response.getBytes());
                        outputStream.flush();
                    }    
                        Arrays.fill(buffer, (byte) 0);
                        message="";
                        response="";
                }    

                inputStream.close();
                outputStream.close();
                clientSocket.close();

                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }

        private boolean checkPassword(String studentName, String password) {
            if(studentName.equals(password)){
                return true;
            }
            else{
                return false;
            }
        }
    }
    
}