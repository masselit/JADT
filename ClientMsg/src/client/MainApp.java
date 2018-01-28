package client;

import java.io.IOException;
import java.net.ConnectException;

import model.Ami;
import model.Groupe;
import model.Utilisateur;
import view.*;
import boot.Fenetre;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Thibaut
 * @author Dorian THIVOLLE
 * @date 08/11/2016
 */

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Fenetre fenetre;
    
    @Override
    public void start(Stage primaryStage) throws ConnectException {
    	
    	this.primaryStage = primaryStage;
        
		fenetre = new Fenetre();
		fenetre.initWindows();
		fenetre.closedWindows();
		showConnectionView();
    	//initRootLayoutMessagerie();
        //showMessagerie();
        //showInscription();
 
    }
    
    /*
     * Methode qui permet de charger la page connexion
     */
    public void showConnectionView() {
        try {
        	// Charger la fenêtre connexion
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            Scene scene = new Scene(personOverview);
            primaryStage.setTitle("Connexion");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
            ConnectionController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void showInscription() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/Inscription.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            Scene scene = new Scene(personOverview);
            primaryStage.setTitle("Inscription");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
            
         // Give the controller access to the main app.
            InscriptionController controller = loader.getController();
            controller.setMainApp(this);
            
            // Set person overview into the center of root layout.
            //rootLayout.setCenter(personOverview);
        } catch (IOException e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the root layout Messagerie.
     */
    public void initRootLayoutMessagerie() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/RootLayoutMessage.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();
            
            
        } catch (IOException e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
  
    /**
     * Initializes the Messagerie.
     */
    
    public void showMessagerie(Client client,Utilisateur user) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/MessageView.fxml"));
            AnchorPane view = (AnchorPane) loader.load();
            
         //   Give the controller access to the main app.
            MessagerieController controller = loader.getController();
            controller.setMainApp(this);
            controller.setClient(client);
            controller.setUser(user);
            controller.startNotif();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(view);
        } catch (IOException e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
   
    private ObservableList<Ami> personData = FXCollections.observableArrayList();
    
    private ObservableList<Groupe> groupData = FXCollections.observableArrayList();
    
    

    /**
     * Constructor
     */
    public MainApp() {
    	
        // Add some sample data
        personData.add(new Ami("Hans"));
        personData.add(new Ami("Ruth"));
        personData.add(new Ami("Heinz"));
        personData.add(new Ami("Cornelia"));
        personData.add(new Ami("Werner"));
        personData.add(new Ami("Lydia"));
        personData.add(new Ami("Anna"));
        personData.add(new Ami("Stefan"));
        personData.add(new Ami("Martin"));
        personData.add(new Ami("Aliaz"));
        personData.add(new Ami("Jean"));
        personData.add(new Ami("Misere"));
        personData.add(new Ami("Coline"));
        personData.add(new Ami("Thibaut"));
        personData.add(new Ami("Rédiane"));
        personData.add(new Ami("Milouse"));
        personData.add(new Ami("Conitive"));
        personData.add(new Ami("Expotriarche"));
        
        groupData.add(new Groupe("Les Marmottes"));
        groupData.add(new Groupe("Les Ancyclopède"));
        groupData.add(new Groupe("Les Richton"));
        groupData.add(new Groupe("Les Tapettes"));
        groupData.add(new Groupe("Les Brouettes"));
        groupData.add(new Groupe("Les Trotinettes"));

    }

    public ObservableList<Ami> getPersonData() {
        return personData;
    }
    
    public ObservableList<Groupe> getGroupData() {
        return groupData;
    }
   
    
    /*public ObservableList<Message> getMessage() {
        return personData;
    }*/
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}