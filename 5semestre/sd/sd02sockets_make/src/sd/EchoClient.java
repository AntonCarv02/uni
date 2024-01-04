package sd;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EchoClient {

	private String address = null;
	private int sPort = 0;

	public EchoClient(String add, int p) {
		address = add;
		sPort = p;
	}

	public static void main(String[] args) {
		// exigir os argumentos necessarios
		if (args.length < 2) {
			System.err.println("Argumentos insuficientes:  java EchoClient ADDRESS PORT");
			System.exit(1);
		}

		try {
			String addr = args[0];
			int p = Integer.parseInt(args[1]);

			
			EchoClient cl = new EchoClient(addr, p);

			// ler o texto a enviar ao servidor
			String s = "mensagem_que_deve_vir_como_argumento";


			cl.go(s); // faz o pretendido
		} catch (Exception e) {
			System.out.println("Problema no formato dos argumentos");
			e.printStackTrace();
		}
	}

	public void go(String msg) {

		// exercicio 1: mostrar a mensagem que vai ser enviada
		// .. exercicio 3

		Socket s;
		try {
			s = new Socket(address, sPort);
			// ja esta connected

			// escrever a mensagem?
			OutputStream socketOut = s.getOutputStream();
			InputStream socketIn = s.getInputStream();



			socketOut.write(msg.getBytes());
			socketOut.flush();



			byte[] arr=new byte[1024]; 

			int read =  socketIn.read(arr);
			String mens = new String(arr,0,read);


			System.out.println("ddd"+mens );

		} catch (IOException e) {
			e.printStackTrace();

		}

	}
}
