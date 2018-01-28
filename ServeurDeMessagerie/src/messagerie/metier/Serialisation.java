package messagerie.metier;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import messagerie.model.ChaineDeCommande;
import messagerie.model.Connexion;
import messagerie.model.Images;
import messagerie.model.Inscription;
import messagerie.model.Message;
import messagerie.model.MsgErr;
import messagerie.model.Son;
import messagerie.model.Texts;
import messagerie.model.Utilisateur;

/**
 * @author Thibaut MASSELIN
 * @date 12/11/2016
 */


/**
 * Classe générique 
 * @param <T> (le nom d'une classe est attendut)
 */
public class Serialisation<T extends ChaineDeCommande> {
	//Attribute
	private XStream xstream;
	private T tClasse;
	
	//Constructor
	public Serialisation() {
		//Instantiation de XStream dans la création de l'object
		xstream = new XStream(new DomDriver());
		//utilisation des annotations
		xstream.processAnnotations(Message.class);
		xstream.processAnnotations(Inscription.class);
		xstream.processAnnotations(Utilisateur.class);
		xstream.processAnnotations(Connexion.class);		
		xstream.processAnnotations(Images.class); 
		xstream.processAnnotations(Son.class); 
		xstream.processAnnotations(Texts.class); 
		xstream.processAnnotations(MsgErr.class); 
		//utilsation des alias
		xstream.alias("Message", Message.class);
		xstream.alias("Inscription", Inscription.class);
		xstream.alias("Utilisateur", Utilisateur.class);
		xstream.alias("Connexion", Connexion.class);
		xstream.alias("Texts", Texts.class);
		xstream.alias("Images", Images.class);
		xstream.alias("Son", Son.class);
		xstream.alias("MsgErr", MsgErr.class);
	}
	
	//Method
	
	/**
	 * donne le nom de la classe xml
	 * @param xml
	 */

	@SuppressWarnings("unchecked")
	public synchronized void deserialisation(String xml){
		try{		
			tClasse = (T) xstream.fromXML(xml);	
		}catch(Exception e){
			tClasse = null;
		}
	}
	/**
	 * Créer un nouveau document xml partir d'une classe T donné en paramètre
	 * @param ChaineDeCommande qui est une class abstract
	 * @return un String xml
	 */
	public String creer(ChaineDeCommande temp)throws IllegalArgumentException{
		ChaineDeCommande resultat;
		try{
			resultat = temp.target().creer(temp.getValeurs(),temp.getDestinataire(),temp.getExpediteur());
		}catch(ClassCastException e){
			MsgErr msgErr = new MsgErr();
			msgErr.setMessageErreur("Erreur dans la sérialisation créer");
			resultat =  msgErr;
		}
		// Convertion la Class T en XML
		String xml = xstream.toXML(resultat);
		return xml;
	}
	//Getter - Setter
	public T getT(){
		return tClasse;		
	}
}
