package messagerie.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import messagerie.model.Image;
import messagerie.model.Message;
import messagerie.model.Son;
import messagerie.model.Text;*/

/**
 * @author Thibaut MASSELIN
 * @date 20/11/2016
 */

public class Serveur {
	public static void main(String[] args) {
		
		ServerSocket socketserver;
        Socket client; 
        
        System.out.println("Lancement du Server");
        
        try {       
        	socketserver = new ServerSocket(3550);  
        	while(true){
        		client = socketserver.accept(); 
	            //socket.setSoTimeout(100000);
	            System.out.println("Un nouveau client connecté avec le port:" + client.getPort());
	            Thread t = new Thread(new Authentification(client)); 	            
	            t.start();
        	}
        }catch (IOException e) {
        	e.printStackTrace();
        }
	}

}




/*System.out.println("---------------- Doc XML Reçut ----------------");
		
		//pour imité l'envoie{
		XStream xstream = new XStream(new DomDriver());
		
		Message msg = new Message();
		msg.setDestinataire("Jean");
		msg.setExpediteur("Thibaut");
		Object messageText = new Text("la vie est belle");
		msg.addContenus(messageText);
		Object messageImage = new Image("image 547855");
		msg.addContenus(messageImage);
		Object messageSon = new Son("son 154257557");			
		msg.addContenus(messageSon);
		
		List<String> nn = new ArrayList<>();
		nn.add(new String("Amie1"));
		nn.add(new String("Amie2"));
		Utilisateur unUtil = new Utilisateur();
		unUtil.setDestinataire("Thibaut");
		unUtil.setExpediteur("Serveur");
		unUtil.setNom("LOLO");
		unUtil.setPrenom("jean");
		unUtil.setAvatar(new Image("photoDePlage"));
		unUtil.setListAmie(nn);
		
		
		//String xml = xstream.toXML(unUtil);
		String xml = xstream.toXML(msg);
		System.out.println(xml);
		Thread t2 = new Thread(new ChatServeur(xml));
		t2.start();
		*/