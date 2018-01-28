package metier;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import model.MsgErr;
import model.ChaineDeCommande;
import model.Connexion;
import model.Images;
import model.Inscription;
import model.Message;
import model.Son;
import model.Texts;
import model.Utilisateur;

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
		xstream.processAnnotations(Utilisateur.class);
		xstream.processAnnotations(Inscription.class);
		xstream.processAnnotations(Connexion.class);
		xstream.processAnnotations(Message.class);
		xstream.processAnnotations(Images.class);
		xstream.processAnnotations(Son.class);
		xstream.processAnnotations(Texts.class);
		xstream.processAnnotations(MsgErr.class);
		//utilsation des alias
		xstream.alias("Message", Message.class);
		xstream.alias("Utilisateur", Utilisateur.class);
		xstream.alias("Inscription", Inscription.class);
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
	 * @param contenu
	 * @param des
	 * @param exp
	 * @param target
	 * @return un String xml
	 */
	@SuppressWarnings("unchecked")
	public String creer(ChaineDeCommande target)throws IllegalArgumentException{
		try{
			tClasse = (T) target.creer(target.getValeurs(),target.getDestinataire(),target.getExpediteur());
		}catch(ClassCastException e){
			return "Erreur dans la sérialisation créer";
		}
		// Convertion la Class T en XML
		String xml = xstream.toXML(tClasse);
		return xml;
	}
	//Getter - Setter
	public T getT(){
		return tClasse;		
	}
}
