package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import client.Client;
import client.MainApp;
import util.Encrypt;
import util.GestionData;
import model.Images;
import model.Inscription;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import metier.Envoyer;
import metier.Serialisation;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
/**
 * 
 * @author Dorian Thivolle
 * @author Thibaut
 *
 */
public class InscriptionController {
	
	@FXML
    private TextField userLastName;
	@FXML
    private TextField userFirstName;
	@FXML
    private TextField pseudo;
	@FXML
    private FileInputStream avatar;
	@FXML
	private DatePicker date;
	@FXML
    private TextField mailAdress;
    @FXML
    private PasswordField passWord;
    @FXML
    private PasswordField passWordVerif;

    private MainApp mainApp;

    public InscriptionController() {
    	
    }
    
    public void importAvatar()
    {
    	JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("c:\\"));
        chooser.setDialogTitle("Avatar");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        	System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
        	try {
				avatar = new FileInputStream(chooser.getSelectedFile());
			} catch (FileNotFoundException e) {
				System.out.println("Format incorrect");
				e.printStackTrace();
			}
        } 
        else 
        {
          System.out.println("No Selection");
        }
    }
    
    public void dejaInscrit()
    {
    	mainApp.showConnectionView();
    }
    
    public void inscription()
    {	    	
		if(GestionData.validInscription(
				userLastName, 
				userFirstName, 
				pseudo, 
				mailAdress, 
				date, 
				passWord, 
				passWordVerif))
    	{
			if(GestionData.validMdp(passWord, passWordVerif))
			{
				String pwdEncrypt = Encrypt.MD5encrypt(passWord.getText().toString());
	    		System.out.println("Mot de passe Valide");
	    		
	    		/*DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
	    		try{
	    			Date dateConvert = dateFormat.parse(date.getValue().toString());
	    			System.out.println("Date du date picker : " + dateConvert);
	    		}catch(ParseException e){
	    			e.printStackTrace();
	    		}*/
	    		Client client = new Client();					
	    		Inscription inscription = new Inscription();
	    		try{
	    			client.initClient();
		    		inscription.setAvatar(new Images(avatar.toString()));
		    		inscription.setdNaissance(null);//problème de convertion de date
		    		inscription.setEmail(mailAdress.getText().toString());
		    		inscription.setNom(userLastName.getText().toString());
		    		inscription.setPrenom(userFirstName.getText().toString());
		    		inscription.setPseudo(pseudo.getText().toString());
		    		inscription.setMdp(pwdEncrypt);//pwdEncrypt
		    		
		    		Serialisation<?> seriInscrit = new Serialisation<Inscription>();
	    			Thread t = new Thread(new Envoyer(client.getPrinter(),seriInscrit, inscription));
	    			t.start();
		    		
	    		}catch(Exception e){
	    			System.out.println(e.getMessage());
	    		}	    		
	    		mainApp.showConnectionView();
			}else{
				Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Password");
	            alert.setHeaderText("Le contenu des champs mot de passe n'est pas identiques.");
	            alert.setContentText("Champ(s) Vide");
	            alert.showAndWait();
			}
				
    		
    	}else{
    		
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Saisie");
            alert.setHeaderText("Saisie incorrect");
            alert.setContentText("Champ(s) Vide");
            alert.showAndWait();
            
    	}    	
    }

    @FXML
    private void initialize() {
        
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
