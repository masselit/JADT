package messagerie.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import messagerie.bdd.GestionnaireBdd;
import messagerie.metier.Serialisation;
import messagerie.model.Images;

/**
 * Maillon de la chaîne
 * @author Thibaut MASSELIN
 * @date 13/11/2016
 */

//utiliser des object Map<E,T> plutot que des List<Object>
@XStreamAlias("Utilisateur")
public class Utilisateur extends ChaineDeCommande {
	//Attributes
	@XStreamAlias("pseudo")
	private String pseudo;
	@XStreamAlias("avatar")
	private Images avatar; 
	@XStreamAlias("prenom")
	private String prenom;
	@XStreamAlias("nom")
	private String nom;
	@XStreamAlias("listAmie")
	private List<String> listAmie;
	@XStreamAlias("listGroup")
	private List<String> listGroup;
	
	//Constructor
	public Utilisateur(){
		this.pseudo = new String();
		this.avatar = new Images("");
		this.prenom = new String();
		this.nom = new String();
		this.listAmie = new ArrayList<>();
		this.listGroup = new ArrayList<>();
	}
		
	//Method
	/**
	 * Traitement spécifique dans la chaine pour cette classe 
	 * @return vrai(la bonne classe) faux(la movaise classe)
	 */
	@Override
	protected boolean operationSpec(String xml) {
		deserialisation = new Serialisation<Utilisateur>();
		deserialisation.deserialisation(xml);
		if(deserialisation.getT() != null){
			//si c'est là bonne classe pour la lecture
			if(this.getClass().isAssignableFrom(deserialisation.getT().getClass())){
				return true;
			}			
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ChaineDeCommande creer(Map<String,Object> contenu, String des, String exp) {
		//Server
		this.expediteur = "Serveur";
		//changer ses valeurs finir cette classe
		// récupération des donné dans la bdd
		for(Entry<String, Object> obj : contenu.entrySet()){
			
			if(isList(obj.getValue())){
				if(obj.getKey().equals("listAmie"))
					this.setListAmie((List<String>) obj.getValue());
 				if(obj.getKey().equals("listGroup"))
 					this.setListGroup((List<String>) obj.getValue());
 			}else{
 				switch (obj.getKey()){
 					case "pseudo": this.setPseudo((String) obj.getValue());break;
 					case "avatar":this.setAvatar((Images) obj.getValue());break;
 					case "prenom":this.setPrenom((String) obj.getValue());break;
 					case "nom":this.setNom((String) obj.getValue());break;
 				}		
 			}
		}
		return this;
	}

	@Override
	public Map<String,Object> getValeurs() {		
		Map<String,Object> maps = new HashMap<>();	
		maps.put("pseudo", getPseudo());
		maps.put("avatar",getAvatar());
		maps.put("prenom",getPrenom());
		maps.put("nom",getNom());
		maps.put("listAmie",getListAmie());
		maps.put("listGroup",getListGroup());	
		return maps;
	}
	
	@Override
	public void actionBdd() {
		Map<String,Object> maps= GestionnaireBdd.recuperationProfil(getDestinataire());	
		for(Entry<String, Object> obj : maps.entrySet()){
			switch (obj.getKey()){
				case "pseudo": this.setPseudo((String) obj.getValue());break;
				case "prenom":this.setPrenom((String) obj.getValue());break;
				case "nom":this.setNom((String) obj.getValue());break;
			}	
		}			
	}	

	@Override
	public ChaineDeCommande target() {
		return this;
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

	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public Images getAvatar() {
		return avatar;
	}
	public void setAvatar(Images avatar) {
		this.avatar = avatar;
	}

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<String> getListAmie() {
		return listAmie;
	}
	public void setListAmie(List<String> listAmie) {
		this.listAmie = listAmie;
	}

	public List<String> getListGroup() {
		return listGroup;
	}
	public void setListGroup(List<String> listGroup) {
		this.listGroup = listGroup;
	}
}
