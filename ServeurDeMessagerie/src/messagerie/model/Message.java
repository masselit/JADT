package messagerie.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import messagerie.bdd.GestionnaireBdd;
import messagerie.metier.Serialisation;
/**
 * Maillon de la chaîne
 * @author Thibaut MASSELIN
 * @date 12/11/2016
 */

//utiliser des object Map<E,T> plutot que des List<Object>

@XStreamAlias("Message")
public class Message extends ChaineDeCommande{
	//Attribute
	@XStreamAlias("contenus")
	private List<Object> contenus;
	
	//Constructor
	
	public Message(){
		contenus = new ArrayList<Object>();
	}
	
	//Method
	
	/**
	 * Traitement spécifique dans la chaine pour cette classe 
	 * @return vrai(la bonne classe) faux(la movaise classe)
	 */
	@Override
	public boolean operationSpec(String xml) {
		deserialisation = new Serialisation<Message>();
		deserialisation.deserialisation(xml);
		if(deserialisation.getT() != null){
			//si c'est là bonne classe pour la lecture
			if(this.getClass().isAssignableFrom(deserialisation.getT().getClass())){
				System.out.println("messageOK");
				return true;			
			}
		}	
		return false;
	}
	
	@Override
	public void actionBdd() {
		String contenu ="";
		for(Object obj :this.contenus)
			contenu += obj.toString() +" ";
		GestionnaireBdd.envoieMessage(expediteur, destinataire, contenu);
	}	
	
	@Override
	public Map<String,Object> getValeurs() {
		Map<String,Object> maps = new HashMap<>();
		maps.put("contenus", contenus);
		return maps;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ChaineDeCommande creer(Map<String,Object> contenuMap, String des,String exp) {
		this.setDestinataire(des);
		this.setExpediteur(exp);
		if(contenuMap.containsKey("contenus"))
			this.setContenus((List<Object>) contenuMap.get("contenus"));
		return this;
	}
	
	@Override
	public ChaineDeCommande target() {
		return this;
	}	
	
	public void addContenus(Object contenuMsg){
		contenus.add(contenuMsg);
	}
	
	//Getter - Setter
	@Override
	public String getExpediteur() {
		return expediteur;
	}	
	public void setExpediteur(String expediteur){
		this.expediteur = expediteur;
	}
	
	@Override
	public String getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(String destinataire){
		this.destinataire = destinataire;
	}
	
	public List<Object> getContenus() {
		return contenus;
	}
	public void setContenus(List<Object> contenus) {
		this.contenus = contenus;
	}

	
}
