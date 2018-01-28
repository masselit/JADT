package metier;

import java.io.PrintWriter;

import model.ChaineDeCommande;
/**
 * @author Thibaut MASSELIN
 * @date 20/11/2016
 */
public class Envoyer implements Runnable{
	private PrintWriter out = null;
    private String message = null;
    private Serialisation<?> deseria;
	private ChaineDeCommande target;
    
	public Envoyer(PrintWriter out,Serialisation<?> deseria,ChaineDeCommande target) {
		this.out = out;
		this.deseria = deseria;
		this.target = target;
	}
	@Override
	public void run() { 		
		message = deseria.creer(target);
		System.out.println(message);
		System.out.println(out.toString());
		if(out  != null){
			out.println(message);
			out.flush();
		}	
	}
	
}
