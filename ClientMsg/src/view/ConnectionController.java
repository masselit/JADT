package view;

import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import client.Client;
import client.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import metier.Reception;
import metier.Envoyer;
import metier.Serialisation;
import model.Connexion;
import model.MsgErr;
import model.Utilisateur;
import util.Encrypt;
import util.GestionData;

/**
 * 
 * @author Dorian Thivolle
 * @author Thibaut
 *
 */
public class ConnectionController {
	
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passWord;
    @FXML
    private Button signIn;
    
    private static String messageLog;
    private MainApp mainApp;
    private Client client;
    
    public ConnectionController() {
    	 this.client = new Client();
    }
    
    public void connexion(){
    	try{
	    	if(GestionData.validConnect(userName, passWord)){	   		
	    		client.initClient();
	    		PrintWriter out = client.getPrinter();
	    		Connexion connexion = new Connexion();
	    		
	    		String pwdEcrypt = Encrypt.MD5encrypt(passWord.getText().toString());
	    		
	    		connexion.setIdentifiant(userName.getText().toString());
	    		connexion.setMotDePasse(pwdEcrypt);//pwdEcrypt
	    		
	    		//Envoie de la connexion
	    		Serialisation<?> seriConnect = new Serialisation<Connexion>();
				Thread t = new Thread(new Envoyer(out,seriConnect, connexion));
				t.start();	
				
				//création de l'attente de reception
				Callable<Serialisation<?>> c1 = new Reception(client.getIn());
				FutureTask<Serialisation<?>> ft1 = new FutureTask<>(c1);
				Thread t1 = new Thread(ft1);
				t1.start();
				Serialisation<?> seriUtilisateur =  ft1.get();
				
				if(MsgErr.class.isAssignableFrom(seriUtilisateur.getT().getClass())){
					Alert alert = new Alert(AlertType.WARNING);
		            alert.initOwner(mainApp.getPrimaryStage());
		            alert.setTitle("Erreur");
		            alert.setHeaderText("Connexion Impossible");
		            for(Entry<String, Object> obj : seriUtilisateur.getT().getValeurs().entrySet()){
		            	if(obj.getKey().equals("messageErreur")){
		    				alert.setContentText(obj.getValue().toString());
		    			}
		            }
		            alert.showAndWait();
	    		}else{
					mainApp.initRootLayoutMessagerie();
					mainApp.showMessagerie(client,(Utilisateur) seriUtilisateur.getT());
				}
	    	}
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}

    }
			
        	/*if (verif == true){
        		mainApp.initRootLayoutMessagerie();
            	mainApp.showMessagerie();
            	System.out.println("Vous êtes bien connecté");
            	messageLog = "Vous êtes bien connecté";
        	}else{
        		System.out.println("Identifiants incorrectes");
        		messageLog = "Identifiants incorrectes";
        	}
    	}else{
    		if(userName.getText().isEmpty())
    		{
    			System.out.println("Le champs pseudo est vide");
    			messageLog = "Le champs pseudo est vide";
    		}
    		if(passWord.getText().isEmpty())
    		{
    			System.out.println("Le champs mot de passe est vide");
    			messageLog = "Le champs mot de passe est vide";
    		}
    	}
    }*/
    
    public void haveSignUp()
    {
    	mainApp.showInscription();
    }

    public static String getMessageLog() {
		return messageLog;
	}


    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
    }
}