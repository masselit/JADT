package messagerie.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import messagerie.model.ChaineDeCommande;
import messagerie.model.Connexion;
import messagerie.model.Inscription;
import messagerie.model.Message;
import messagerie.model.Utilisateur;
/**
 * @author Thibaut MASSELIN
 * @date 16/11/2016
 */
public class Reception implements Callable<Serialisation<?>>{
	//Attributes
	private BufferedReader in;
	private Serialisation<?> deseria;
	private Message maillionMessage;
	//Constructor
	public Reception(BufferedReader in){
		this.in = in;
		
		this.maillionMessage = new Message();	
 		ChaineDeCommande maillionUtilisateur = new Utilisateur();
 		ChaineDeCommande maillionConnexion = new Connexion();
 		ChaineDeCommande maillionInscription = new Inscription();
 		
 		//structure
 		maillionMessage.setSuivant(maillionConnexion); 	
 		maillionConnexion.setSuivant(maillionInscription);
 		maillionInscription.setSuivant(maillionUtilisateur);
	}
	
	//Method
	/**
	 * stock toute la socket dans un String
	 * @throws SecurityException 
	 */
	public String lectureXML() throws SecurityException{
		String xml ="";
		while(true){
			try {
				xml += in.readLine();
				if(!in.ready()){
					break;
				}
			} catch (IOException e) {
				Thread.currentThread().interrupt();
				throw new SecurityException("throw - erreur readLine Reception");
			}			
		}
		System.out.println(xml);
		return xml;					
	}

	/**
	* point d'entrée du Thread Callable<?>
	*/
    @Override
	public Serialisation<?> call() throws ExecutionException,IllegalArgumentException,SecurityException{   
		String xml = lectureXML();
		deseria = maillionMessage.operation(xml);
 		return deseria;
	}
}

	
	