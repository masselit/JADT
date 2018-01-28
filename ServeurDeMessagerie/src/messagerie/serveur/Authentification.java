package messagerie.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import messagerie.metier.Envoie;
import messagerie.metier.Reception;
import messagerie.metier.Serialisation;
import messagerie.model.ChaineDeCommande;
import messagerie.model.Connexion;
import messagerie.model.Inscription;
import messagerie.model.MsgErr;
/**
 * @author Thibaut MASSELIN
 * @date 27/11/2016
 */
public class Authentification implements Runnable{
	private	Socket s = null;
	private BufferedReader in = null;
	private Thread t1;
	public final static List<ChatServeur> CLIENTS = new ArrayList<ChatServeur>();
	
	public Authentification(Socket s){
		this.s = s;
	}
	
	@Override
	public void run(){
		Serialisation<?> deseria = null;
		try{
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			Callable<Serialisation<?>> c1 = new Reception(in);
			FutureTask<Serialisation<?>> ft1 = new FutureTask<>(c1);
		    t1 = new Thread(ft1);
			t1.start();
			
			try {
			   deseria = ft1.get();
			} catch (Exception e) {
				System.err.println("Authentification ErrNo1");
			}				
			if(isConnect(deseria.getT())){
				Thread t1 = new Thread(new Envoie(this.s,deseria));
				t1.start();
				ChatServeur cs = new ChatServeur(s,deseria);
	            CLIENTS.add(cs);
	            System.out.println("Authentifier");
	            Thread t = new Thread(cs);
	            t.start();
			}else{
				//envoie un objet MsgErr
				System.err.println("mdp ou pseudo introuvable");
				
				MsgErr erreur = new MsgErr();				
				erreur.setMessageErreur("mdp ou pseudo introuvable");				
				Thread t1 = new Thread(new Envoie(this.s,erreur));
				t1.start();		
			}
		}catch (IOException e1) {
			System.err.println("Authentification ErrNo0");
	    }
	}
	/**
	 * On attend ici la levée d'une Exception 
	 * qui a comme libellé "connecter" pour valider la connexion
	 * synchronized -> pose d'un vérrou sur la méthode
	 * @param cdc
	 * @return
	 */
	private synchronized boolean isConnect(ChaineDeCommande cdc){
		boolean flag = false;
		try{
			if(Connexion.class.isAssignableFrom(cdc.getClass()) || Inscription.class.isAssignableFrom(cdc.getClass())){
				System.out.println("requette SQL");
				cdc.actionBdd();
			}
		}catch(SecurityException e){
			if(e.getMessage().equals("connecter"))
				flag = true;
		}
		return flag;
	}
}
