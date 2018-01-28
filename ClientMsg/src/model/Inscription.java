package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import model.Images;
import metier.Serialisation;
/**
 * @author Thibaut MASSELIN
 * @date 23/11/2016
 */

@XStreamAlias("Inscription")
public class Inscription extends ChaineDeCommande {
	
	@XStreamAlias("pseudo")
	private String pseudo;
	@XStreamAlias("mdp")
	private String mdp; 
	@XStreamAlias("prenom")
	private String prenom; 
	@XStreamAlias("nom")
	private String nom; 
	@XStreamAlias("email")
	private String email; 
	@XStreamAlias("dNaissance")
	private Date dNaissance; 
	@XStreamAlias("avatar")
	private Images avatar;
	
	public Inscription(){
		this.pseudo = new String();
		this.mdp = new String();
		this.prenom = new String();
		this.nom = new String();
		this.email = new String();
		this.dNaissance = new Date();
		this.avatar = new Images("");
	}
	@Override
	protected boolean operationSpec(String xml) {
		deserialisation = new Serialisation<Inscription>();
		deserialisation.deserialisation(xml);
		if(deserialisation.getT() != null){
			//si c'est là bonne classe pour la lecture
			if(this.getClass().isAssignableFrom(deserialisation.getT().getClass())){
				return true;
			}			
		}
		return false;
	}

	@Override
	public ChaineDeCommande creer(Map<String,Object> contenu, String des, String exp) {
		
		for(Entry<String, Object> obj : contenu.entrySet()){
			switch (obj.getKey()){
				case "pseudo": this.setPseudo(obj.getValue().toString());break;
				case "mdp": this.setMdp(obj.getValue().toString());break;
				case "prenom":this.setPrenom(obj.getValue().toString());break;
				case "nom":this.setNom(obj.getValue().toString());break;	
				case "email":this.setEmail(obj.getValue().toString());break;	
				case "dNaissance":this.setdNaissance((Date) obj.getValue());break;	
				case "avatar":this.setAvatar((Images) obj.getValue());break;
 			}
		}
		
		this.setExpediteur(this.getPseudo());
		this.setDestinataire("SERVER");
		
		return this;
	}

	
	@Override
	public Map<String, Object> getValeurs() {
		Map<String,Object> maps = new HashMap<>();
		maps.put("pseudo",getPseudo());
		maps.put("mdp",getMdp());
		maps.put("prenom",getPrenom());
		maps.put("nom",getNom());
		maps.put("email",getEmail());
		maps.put("dNaissance",getdNaissance());
		maps.put("avatar",getAvatar());
		return maps;
	}
	
	private void setExpediteur(String exp) {
		expediteur = exp;
	}
	
	private void setDestinataire(String des) {
		destinataire = des;
	}
	
	@Override
	public String getExpediteur() {
		return expediteur;
	}

	@Override
	public String getDestinataire() {
		return destinataire;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getdNaissance() {
		return dNaissance;
	}

	public void setdNaissance(Date dNaissance) {
		this.dNaissance = dNaissance;
	}

	public Images getAvatar() {
		return avatar;
	}

	public void setAvatar(Images avatar) {
		this.avatar = avatar;
	}

}
