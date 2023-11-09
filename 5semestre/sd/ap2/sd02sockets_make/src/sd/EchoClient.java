package sd;

import java.io.*;
import java.net.*;

public class EchoClient {

    private String address= null;
    private int sPort= 0;
    
    public EchoClient(String add, int p) {
	address= add;
	sPort=p;
    }
    
    
    public static void main(String[] args){
	// exigir os argumentos necessarios
		if (args.length < 2) {
			System.err.println("Argumentos insuficientes:  java EchoClient ADDRESS PORT");
			System.exit(1);
		}
		
		try {
			String addr= args[0];
			int p= Integer.parseInt(args[1]);	
			
			EchoClient cl= new EchoClient(addr,p);
			
			// ler o texto a enviar ao servidor
			String s= args[2];
			
			cl.go( s );   // faz o pretendido
		}
		catch (Exception e) {
			System.out.println("Problema no formato dos argumentos");
			e.printStackTrace();
		}
    }
    
    
    
    public void go(String msg) {
	
		// exercicio 1: mostrar a mensagem que vai ser enviada
		// ...
		
		System.out.println(msg);
		byte[] resp= new byte[1024]; ;
		// ... exercicio 3
		
		try (Socket s = new Socket(address, sPort)) {
			
			// escrever a mensagem?
			OutputStream socketOut= s.getOutputStream();
			InputStream socketIn= s.getInputStream();

			socketOut.write(msg.getBytes());


			int bread = socketIn.read(resp);
			String response = new String(resp, 0, bread);


			System.out.println(response);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//... exercicio 3
		
    }
    
}
