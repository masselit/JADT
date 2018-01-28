package view;

import java.io.PrintWriter;
import client.Client;
import client.MainApp;
import client.NotificationServer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import metier.Envoyer;
import metier.Serialisation;
import model.Ami;
import model.Groupe;
import model.Message;
import model.Texts;
import model.Utilisateur;
/**
 * 
 * @author Dorian Thivolle
 * @author Thibaut
 *
 */
public class MessagerieController {
	@FXML
	private Button bEnvoyer;
	@FXML
	private GridPane gridPane;
	@FXML
	private TextField message;
	@FXML
	private ImageView profilImage;
	@FXML
	private VBox vbPrincipalProfil;
	@FXML
	private TitledPane titlePane;
	@FXML
	private TextArea msgBox;
	@FXML
	private TabPane tabPaneSmile;
	@FXML
    private Text nom;
    @FXML
    private RadioButton etat;
    @FXML
    private TableView<Ami> personView;
    @FXML
    private TableView<Groupe> groupeView;
    @FXML
    private TableColumn<Groupe, String> groupNameColumn;
    @FXML
    private TableColumn<Ami, String> firstNameColumn;
    @FXML
    private TableColumn<Ami, String> etatNameColumn;
	
	private MainApp mainApp;
	private Ami amiApp;
	private ObservableList<Message> listMessage = FXCollections.observableArrayList();
	private Message msg;
	private Client client;
	private Utilisateur user;
	private Thread t;
	private Serialisation<?> seriMsg;
	
	public MessagerieController() {}

	public void setClient(Client client){
		this.client = client;
	}
	public void setUser(Utilisateur user){
		this.user = user;
	}
	public void startNotif(){
		t = new Thread(new NotificationServer(client.getIn(), this));
		t.start();
	}
	
	public void envoyerMessage()
	{
		try{
			
			PrintWriter out = client.getPrinter();
			
			msg = new Message();
			msg.setDestinataire("ALL");//ALL
			msg.setExpediteur(user.getPseudo());
			
			Object messageText = new Texts(msgBox.getText().toString());
			msg.addContenus(messageText);
			/*Object messageImage = new Images("image 547855");
			msg.addContenus(messageImage);
			Object messageSon = new Son("son 154257557");
			msg.addContenus(messageSon);*/
			
			listMessage.add(msg);
			for(Message messag : listMessage)
			{
				for(Object obj :messag.getContenus()){
					System.out.println(obj.toString());
				}	
			}
			
			Serialisation<?> seriMsg = new Serialisation<Message>();
			Thread t = new Thread(new Envoyer(out,seriMsg, msg));
			t.start();
			
			Label bulles = new Label();
			bulles.setText("Moi : " + msgBox.getText().toString());
			int rowGrid = getRowCount(gridPane);
		    bulles.setFont(Font.font("Arial", FontWeight.LIGHT, 15));
		    bulles.setWrapText(false); 
		    gridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		    gridPane.setVgap(10); //vertical gap in pixels
		    gridPane.setPadding(new Insets(10, 10, 10, 10)); //margins around the whole grid //(top/right/bottom/left)
		    gridPane.add(bulles, 0, rowGrid++);  
		    msgBox.setText("");
		    
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void setMessgae(Serialisation<?> seriMsg){
		this.seriMsg = seriMsg;
		Platform.runLater(() ->recevoirMessage());
	}
	public void recevoirMessage()
	{
		try{
			Label bulles = new Label();
			
			bulles.setText(seriMsg.getT().getExpediteur() + " : " + seriMsg.getT().toString());
			int rowGrid = getRowCount(gridPane);
		    bulles.setFont(Font.font("Arial", FontWeight.LIGHT, 15));
		    bulles.setWrapText(false); 
		    gridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		    gridPane.setVgap(10); //vertical gap in pixels
		    gridPane.setPadding(new Insets(10, 10, 10, 10)); //margins around the whole grid //(top/right/bottom/left)
		    gridPane.add(bulles, 0, rowGrid++);  
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int getRowCount(GridPane pane) 
	{
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }
	
	private void showFriends(Ami amis)
	{
		nom.setText(amis.getPseudo());
		etat.setText(amis.getEtat());
	}
	
	@FXML
	private void initialize() {
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().PseudoProperty());
		etatNameColumn.setCellValueFactory(cellData -> cellData.getValue().EtatProperty());
		groupNameColumn.setCellValueFactory(cellData -> cellData.getValue().NomProperty());
	}
	
	/**
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        personView.setItems(mainApp.getPersonData());
        groupeView.setItems(mainApp.getGroupData());
        
        // Add observable list data to the table
    }
}
