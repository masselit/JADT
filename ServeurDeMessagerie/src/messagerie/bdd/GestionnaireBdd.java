package messagerie.bdd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import messagerie.model.Images;

/**
 * 
 * @author Alice Langlois
 * @date 23/11/2016
 *
 */
public class GestionnaireBdd {
	//verifie que le mdp correspond au pseudo, renvoie un booleen false si le mdp est faux
	public static boolean connexion(String pseudo, String mdp) throws IllegalStateException{
		boolean estConnecte = false;		
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"SELECT mdp FROM utilisateur WHERE pseudo ='" + pseudo + "'");				
			ResultSet resultat = prepare.executeQuery();				
			while (resultat.next()){ 					
				String motDePasse = resultat.getString("mdp");
				if ( motDePasse.equals(mdp) ){
					estConnecte = true; 
				}
			}
		}catch(SQLException e){			
			return false;
		}	
		return estConnecte;		
	}
	
	//inscription d'un utilisateur dans la base de donnés
	public static void inscription(String pseudo, String mdp, String prenom, String nom, String email, Date dNaissance, Images avatar){
		try{
			String req = "INSERT INTO utilisateur VALUES('" + pseudo +"','"+ nom +"','"+ prenom +"','"+ email +"',NULL,'"+ avatar.toString() +"','"+ mdp +"', NOW())";
			System.out.println(req);
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(req);							
			prepare.execute();
		}catch(SQLException e){
			e.printStackTrace(); 
		}				
	}
	
	//ajouter un utilisateur dans un groupe
	public static void ajoutUtiGroup(String pseudo, Integer groupeId){
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"INSERT INTO groupeUti VALUES('"+ groupeId +"'," + pseudo +"'" );				
			prepare.execute();				
		}catch(SQLException e){
			e.printStackTrace(); 
		}
	}
	
	//Supprimer un groupe
	public static void supprimerGroupe(Integer groupeId){		
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"DELETE FROM groupe WHERE id='"+ groupeId + "'");				
			prepare.execute();				
		}catch(SQLException e){
			e.printStackTrace(); 
		}				
	}
	
	//supprime l'utilisateur d'un groupe
	public static void supprimerUtiGroupe(Integer groupeId, String pseudo){		
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"DELETE FROM groupeuti WHERE idGroup='"+ groupeId + "' AND pseudoUtilisateur='"+ pseudo +"'");				
			prepare.execute();				
		}catch(SQLException e){
			e.printStackTrace(); 
		}				
	}
	
	//recupere les infos du profil de l'utilisateur retourne une liste
	public static Map<String,Object> recuperationProfil(String pseudo){		
		Map<String,Object> profil = new HashMap<>();
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"SELECT nom, prenom, avatar FROM utilisateur WHERE pseudo ='" + pseudo + "'");				
			ResultSet resultat = prepare.executeQuery();
			if(resultat.next()){
				profil.put("nom",resultat.getString("nom"));
				profil.put("pseudo", pseudo);
				//profil.put("prenom", resultat.getString("prenom"));
				profil.put("avatar", resultat.getString("avatar"));	 
			}
		}catch(SQLException e){
			e.printStackTrace(); 
		}		
		return(profil);		
	}
	
	//recupere les groupes de l'utilisateur retourne une liste
	public static ArrayList<String> recuperationListeGroupe(String pseudo){
		ArrayList<String> lGroupe = new ArrayList<String>();
		int i = 0;		
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"SELECT nomGroupe FROM groupe, groupeuti, utilisateur WHERE pseudo LIKE '" + pseudo + "' AND pseudo = pseudoUtilisateur AND id = idGroup");				
			ResultSet resultat = prepare.executeQuery();				
			while (resultat.next()){ 					
				i++;
				lGroupe.add(resultat.getString(i));
				resultat.next();
			}
		}catch(SQLException e){
			e.printStackTrace(); 
		}		
		return(lGroupe);				
	}
	
	//recupere la liste d'ami retourne une liste
	public static ArrayList<String> recuperationListeAmi(String pseudo){
		ArrayList<String> lAmi = new ArrayList<String>();
		int i = 0;		
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"SELECT idUti2 FROM relation WHERE idUti1 ='" + pseudo + "' AND sontAmis = 1'" );				
			ResultSet resultat = prepare.executeQuery();				
			while (resultat.next()){ 					
				i++;
				lAmi.add(resultat.getString(i));
				resultat.next();
			}
		}catch(SQLException e){
			e.printStackTrace(); 
		}		
		return(lAmi);				
	}
	
	//Supprimer un utilisateur
	public static void supprimerUti(String pseudo){		
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"DELETE FROM utilisateur WHERE pseudo='"+ pseudo + "'");				
			prepare.execute();				
		}catch(SQLException e){
			e.printStackTrace(); 
		}				
	}
	
	// enregistre le message dans la bdd
	public static void envoieMessage(String pseudo, String destinataire, String txt){				
		try{
			String req = "INSERT INTO message VALUES(NULL,'"+ pseudo +"','"+ destinataire +"',NOW(),'"+ txt +"',NULL)";
			System.out.println(req);
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(req);	
			prepare.execute();			
		}catch(SQLException e){
			e.printStackTrace(); 
		}	
	}
	
	// Lis un message selon l'utilisateur et le destinataire
	public static String lireMessage(String pseudo, String destinataire){
		String mess = "";
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"SELECT texte FROM message, utilisateur WHERE pseudo_utilisateur = pseudo AND pseudo ='"+ pseudo +"' AND pseudo_destinataire ='"+destinataire+"'") ;			
			prepare.executeQuery();
			ResultSet resultat = prepare.executeQuery();			
			while (resultat.next()){ 				
				 mess = resultat.getString("texte");				
			}
		}catch(SQLException e){
			e.printStackTrace(); 
		}		
		return(mess);
	}
	
	//Mise a jour du profil
	public static void majProfilUti(String pseudo, String prenom, String nom, Images avatar){
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"UPDATE utilisateur (pseudo,nom,prenom,avatar) SET pseudo='" + pseudo +"', nom="+ nom +"', prenom="+ prenom +"',avatar ='"+ avatar.toString()) ;				
			prepare.execute();
		}catch(SQLException e){
			e.printStackTrace(); 
		}		
	}
	
	// créer un groupe
	public static void creerGroupe(String nom, Images avatar){	
		try{
			PreparedStatement prepare = ConnexionBdd.getConnexion().prepareStatement(
			"INSERT INTO groupe VALUES('"+ nom +"','" + avatar+ "')");				
			prepare.execute();				
		}catch(SQLException e){
			e.printStackTrace(); 
		}	
	}
	
//Créer methode lister utilisateur avec like

}


