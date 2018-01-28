package messagerie.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import messagerie.bdd.GestionnaireBdd;
import messagerie.metier.Serialisation;

/**
 * Maillon de la chaîne
 * @author Thibaut MASSELIN
 * @date 15/11/2016
 */
@XStreamAlias("Connexion")
public class Connexion extends ChaineDeCommande{
	//Attribute
	@XStreamAlias("identifiant")
	private String identifiant;
	@XStreamAlias("motDePasse")
	private String motDePasse;
	
	//Constructor
	public Connexion(){
		this.identifiant = new String();
		this.motDePasse = new String();
	}
	
	//Method
	/**
	 * Traitement spécifique dans la chaine pour cette classe 
	 * @return vrai(la bonne classe) faux(la movaise classe)
	 */
	@Override
	public boolean operationSpec(String xml) {
		deserialisation = new Serialisation<Connexion>();
		deserialisation.deserialisation(xml);
		if(deserialisation.getT() != null){
			//si c'est là bonne classe pour la lecture
			if(this.getClass().isAssignableFrom(deserialisation.getT().getClass())){
				return true;
			}			
		}
		return false;
	}
	
	/**
	 * Réalise une action sur la base de données
	 * La connection
	 */
	@Override
	public void actionBdd()throws SecurityException{
		try {
			if(GestionnaireBdd.connexion(this.identifiant, this.motDePasse)){
				throw new SecurityException("connecter");
			}
		} catch (IllegalStateException e) {
			System.err.println("problème gestionnaire BDD :" + e.getMessage());		
		}
	}

	@Override
	public ChaineDeCommande creer(Map<String, Object> contenuMap, String des, String exp){
		//Serveur
		this.setDestinataire(des);
		this.setExpediteur(exp);
		for(Entry<String, Object> obj : contenuMap.entrySet()){
			if(obj.getKey().equals("identifiant"))
				this.setIdentifiant(obj.getValue().toString());
		}
		return this;
	}

	@Override
	public Map<String, Object> getValeurs() {
		Map<String,Object> maps = new HashMap<>();
		maps.put("identifiant", this.identifiant);
		maps.put("motDePasse", this.motDePasse);
		return maps;
	}
	
	@Override
	public ChaineDeCommande target() {
		Utilisateur util = new Utilisateur();
		util.setExpediteur(getDestinataire());
		util.setDestinataire(getExpediteur());
		util.actionBdd();
		return util;
	}
	//Getter - Setter
	
	@Override
	public String getExpediteur() {
		return expediteur;
	}
	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}
	@Override
	public String getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}	
}
