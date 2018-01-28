package boot;

import java.awt.Color;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import client.MainApp;

import java.util.concurrent.TimeUnit;
/**
 * 
 * @author Dorian Thivolle
 *
 */
@SuppressWarnings("serial")
public class Fenetre extends JFrame{

	private MainApp mainApp;
	
	public Fenetre(){
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Terminer le processus lorsqu'on clique sur"Fermer"
	}
	
	public void initWindows()
	{
		Panneau panel = new Panneau();
		
		//JFrame fenetre = new JFrame();
		this.setTitle("My Window");// Titre de la fen^tre
		this.setSize(570, 700);//Définit une taille pour celle-ci ; ici, 400 px delarge et 500 px de haut
		this.setLocationRelativeTo(null); // Fen^tre au milieu de l'écran
		this.setResizable(false); // Redimetionnable ou non
		this.setAlwaysOnTop(true); // Toujour au premier plan
		this.setUndecorated(true); // Sans boutton et contour
		this.setBackground(new Color(0, 0, 0, 0));
		//fenetre.setLocation(50,45);//setLocation(int x, int y);
		
		//On prévient notre JFrame que ce sera notre JPanel qui sera son contentPane
		this.setContentPane(panel);
		this.setVisible(true);
	}
	
	public void closedWindows()
	{
		try {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Fin Timer");
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}catch (NullPointerException e){
			
			System.out.println(e.getMessage());
			System.out.println("Erreur");
			e.printStackTrace();
			
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println("Erreur");
			e.printStackTrace();
		}
	}
}
