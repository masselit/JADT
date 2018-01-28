package messagerie.metier;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import messagerie.model.MsgErr;
/**
 * @author Thibaut MASSELIN
 * @date 20/11/2016
 */
public class Envoie implements Runnable{
	//Attributes
	private Socket sock = null;
	private PrintWriter out = null;
    private String xml = null;
    private Serialisation<?> deseria;
    
    //Constructors
	public Envoie(Socket sock,Serialisation<?> deseria) {
		this.sock = sock;
		this.deseria = deseria;
		xml = deseria.creer(deseria.getT());
	}
	
	public Envoie(Socket sock,MsgErr msgErr){
		this.sock = sock;
		deseria = new Serialisation<MsgErr>();
		xml = deseria.creer(msgErr);
	}
	
	//Methods
	@Override
	public void run() {
		try {
			out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));				
			System.out.println("Envoie : "+xml);
			
			if(out  != null){
				out.println(xml);
				out.flush();
			}
		} catch (IOException e) {
			System.out.println("Erreur PrintWriter dans l'envoie");
			e.printStackTrace();
		}
	}
	
}
