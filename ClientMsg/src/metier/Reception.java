package metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.Callable;

import model.Connexion;
import model.Inscription;
import model.ChaineDeCommande;
import model.Message;
import model.MsgErr;
import model.Utilisateur;
/**
 * @author Thibaut MASSELIN
 * @date 16/11/2016
 */
public class Reception implements Callable<Serialisation<?>>{
	private BufferedReader in;
	private Message maillionMessage;
	
	public Reception(BufferedReader in){
		this.in = in;
		
		this.maillionMessage = new Message();	
 		ChaineDeCommande maillionUtilisateur = new Utilisateur();
 		ChaineDeCommande maillionConnexion = new Connexion();
 		ChaineDeCommande maillionInscription = new Inscription();
 		ChaineDeCommande maillionMsgErr = new MsgErr();
 		
 		//structure
 		maillionMessage.setSuivant(maillionConnexion); 	
 		maillionConnexion.setSuivant(maillionInscription);
 		maillionInscription.setSuivant(maillionUtilisateur);
 		maillionUtilisateur.setSuivant(maillionMsgErr);
	}
	/**
	 * stock toute la socket dans un String
	 * @param in
	 * @return
	 */
	private String lectureXML(){
		String xml ="";
		while(true){
			try {
				xml += in.readLine();
				if(!in.ready()){
					return xml;
				}
			} catch (IOException e) {
				return xml;
			}
		}	
	}
	
	/**
	* point d'entrée du Thread Callable<?>
	*/
    @Override
	public Serialisation<?> call() throws Exception {   
    	String xml = lectureXML();
    	System.out.println(xml);
		return maillionMessage.operation(xml);
	}
}

	
	