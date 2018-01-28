package util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
/**
 * @author Dorian Thivolle
 */
public class GestionData {
	
	public static boolean validConnect(TextField mdp, PasswordField pwd)
	{
		boolean flag = false;
		
		if(!mdp.getText().isEmpty() && !pwd.getText().isEmpty())
		{
			flag = true;
		}
		
		return flag;
	}
	
	public static boolean validMdp(PasswordField password, PasswordField confirmPassword)
	{
		boolean flag = false;
		if(password.getText().toString().equals(confirmPassword.getText().toString()))
		{
			flag = true;
		}
		return flag;
	}
	
	public static boolean validInscription(
			TextField v_nom, 
			TextField v_prenom, 
			TextField v_pseudo, 
			TextField v_email, 
			DatePicker v_date, 
			PasswordField v_mdp,
			PasswordField v_confirmMdp)
	{
		boolean flag = false;
		
		if(!v_nom.getText().isEmpty() && //------------
    			!v_prenom.getText().isEmpty() && //------------
    			!v_pseudo.getText().isEmpty() && //------------
    			!v_email.getText().isEmpty() && //-------------*/
    			!v_mdp.getText().isEmpty() && //-------------
    			!v_confirmMdp.getText().isEmpty()) //--------------
    	{
			flag = true;
    	}
		else
		{
    		if(v_pseudo.getText().isEmpty()){
    			v_pseudo.setStyle("-fx-border-color: red");
    			System.out.println("Le Champs Pseudo est vide");
    		}
    		if(v_nom.getText().isEmpty()){
    			v_nom.setStyle("-fx-border-color: red");
    			System.out.println("Le Champs Nom est vide");
    		}
    		if(v_prenom.getText().isEmpty()){
    			v_prenom.setStyle("-fx-border-color: red");
    			System.out.println("Le Champs Prénom est vide");
    		}
    		if(v_email.getText().isEmpty()){
    			v_email.setStyle("-fx-border-color: red");
    			System.out.println("Le Champs de l'Adresse mail est vide");
    		}
    		if(v_mdp.getText().isEmpty()){
    			v_mdp.setStyle("-fx-border-color: red");
    			System.out.println("Le Champs Mot de passe 1 est vide");
    		}
    		if(v_confirmMdp.getText().isEmpty()){
    			v_confirmMdp.setStyle("-fx-border-color: red");
    			System.out.println("Le Champs Mot de passe 2 est vide");
    		}
    	}
		return flag;
	}
	
}
