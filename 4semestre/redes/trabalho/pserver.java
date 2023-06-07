package trabalho;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class pserver {
    private static final int BUFFER_SIZE = 1024;
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int PORT = 5555;

    private ByteBuffer buffer;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private Map<String, String> studentAttendance;
    private Map<Integer, Question> questionMap;
    private int questionCounter;

    public pserver() throws IOException {
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        studentAttendance = new HashMap<>();
        questionMap = new HashMap<>();
        questionCounter = 1;
    }

    public void start() throws IOException {
        System.out.println("Server started");

        while (true) {
            int readyCount = selector.select();

            if (readyCount == 0) {
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    handleAccept(key);
                } else if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("New client connected: " + socketChannel.getRemoteAddress());
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        buffer.clear();

        int bytesRead = socketChannel.read(buffer);
        if (bytesRead == -1) {
            disconnectClient(key);
            return;
        }

        buffer.flip();
        String message = CHARSET.decode(buffer).toString().trim();
        System.out.println("Received from client: " + message);

        // Split the message into command and parameters, if applicable
        String[] parts = message.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String parameters = (parts.length > 1) ? parts[1] : "";

        String response = processCommand(command, parameters);
        buffer.clear();
        buffer.put(CHARSET.encode(response));
        buffer.flip();
        socketChannel.write(buffer);
    }

    private String processCommand(String command, String parameters) {
        String response;

        switch (command) {
            case "iam":
                response = handleLogin(parameters);
                break;
            case "ask":
                response = handleAsk(parameters);
                break;
            case "answer":
                response = handleAnswer(parameters);
                break;
            case "listquestions":
                response = handleListQuestions();
                break;
            default:
                response = "ERROR: Unknown command.";
                break;
        }

        return response;
    }

    private String handleLogin(String studentId) {
        String response;

        // Check if studentId exists in the studentAttendance map
        if (studentAttendance.containsKey(studentId)) {
            response = "HELLO " + studentId;

            // Check if the student is more than 45 minutes late
            if (LocalTime.now().isAfter(LocalTime.of(8, 45))) {
                response += " (Late)";
            } else if (LocalTime.now().isAfter(LocalTime.of(8, 20))) {
                response += " (Half-day)";
            } else {
                response += " (Full-day)";
            }
        } else {
            // Add studentId to the studentAttendance map
            studentAttendance.put(studentId, "Present");
            response = "HELLO " + studentId + " (Full-day)";
        }

        return response;
    }

    private String handleAsk(String question) {
        int questionId = questionCounter++;
        Question newQuestion = new Question(questionId, question);
        questionMap.put(questionId, newQuestion);
        return "REGISTERED " + questionId;
    }

    private String handleAnswer(String parameters) {
        String[] parts = parameters.split("\\s+", 2);
        int questionId = Integer.parseInt(parts[0]);
        String answer = parts[1];

        Question question = questionMap.get(questionId);
        if (question != null) {
            question.setAnswer(answer);
            return "ANSWERED " + questionId;
        } else {
            return "ERROR: Invalid question ID.";
        }
    }

    private String handleListQuestions() {
        StringBuilder response = new StringBuilder();

        for (Question question : questionMap.values()) {
            response.append("(").append(question.getId()).append(") ").append(question.getQuestion()).append("\n");
            if (question.getAnswer() != null) {
                response.append("(Answered) ").append(question.getAnswer()).append("\n");
            } else {
                response.append("(Not Answered)\n");
            }
        }

        return response.toString();
    }

    private void disconnectClient(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.close();
        key.cancel();
        System.out.println("Client disconnected: " + socketChannel.getRemoteAddress());
    }

    public static void main(String[] args) {
        try {
            pserver server = new pserver();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}