package messagerie.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import messagerie.bdd.GestionnaireBdd;
//import messagerie.bdd.GestionnaireBdd;
import messagerie.metier.Serialisation;
/**
 * Maillon de la chaîne
 * @author Thibaut MASSELIN
 * @date 23/11/2016
 */
@XStreamAlias("Inscription")
public class Inscription extends ChaineDeCommande {
	//Attributes
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
	//Constructor
	public Inscription(){
		this.pseudo = new String();
		this.mdp = new String();
		this.prenom = new String();
		this.nom = new String();
		this.email = new String();
		this.dNaissance = null;
		this.avatar = null;
	}
	//Method
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
	/**
	 * Méthode jamais utilisé
	 * @param contenuMap
	 * @param des
	 * @param exp
	 * @return
	 */
	@Override
	public ChaineDeCommande creer(Map<String, Object> contenuMap, String des, String exp) {
		//impossible le serveur ne peut envoier d'incription
		System.err.println("Class Inscription envoier côté serveur");
		return null;
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
	
	@Override
	public void actionBdd() {	
		GestionnaireBdd.inscription(this.pseudo, this.mdp, this.prenom, this.nom, this.email, this.dNaissance, this.avatar);	
	}
	
	@Override
	public ChaineDeCommande target() {
		return new Utilisateur();
	}
	//Getter - Setter
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
