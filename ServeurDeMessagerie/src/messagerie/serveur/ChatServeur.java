package messagerie.serveur;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import messagerie.metier.Envoie;
import messagerie.metier.Reception;
import messagerie.metier.Serialisation;
import messagerie.model.Message;

/**
 * @author Thibaut MASSELIN
 * @create 25/11/2016
 * @date 7/12/2016
 */
public class ChatServeur implements Runnable{

	private Socket socket = null;
    private BufferedReader in = null;
    private Thread threadReception, threadEnvoie;
    private Serialisation<?> seria = null;
    private  Message message = null;
    public ChatServeur(Socket socket,Serialisation<?> seriaConn){
        this.socket = socket;
        this.message = new Message();
        this.message.setExpediteur(seriaConn.getT().getExpediteur());
    }
    
    /**
     * Point d'entrer du Thread
     */
	@Override
	public void run(){
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				try{					
					methReception(in);
					exeBdd();
					methEnvoie();
				}catch(IllegalArgumentException | ExecutionException | InterruptedException a){					
					System.out.println(a.getMessage());				
		        	break;
				}
			}
			System.out.println("Fin des comunucations avec :" + socket.getPort());
			Authentification.CLIENTS.remove(this);
			socket.close();	
		} catch (IOException e) {
			System.out.println("problème BufferedReader du ChatServeur");
			//e.printStackTrace();
		}
	}
	/**
	 * demande du client
	 * @param in
	 * @return Serialisation<?>
	 * @throws IllegalArgumentException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private Serialisation<?> methReception(BufferedReader in)throws IllegalArgumentException,ExecutionException, InterruptedException{
		//socket.setSoTimeout(100);
		System.out.println("---------------- Déserialisation(Lecture) ----------------");
		System.out.println("Attente de demande du Client");
		Callable<Serialisation<?>> c1 = new Reception(in);
		FutureTask<Serialisation<?>> ft1 = new FutureTask<>(c1);
	    threadReception = new Thread(ft1);
		threadReception.start();
		seria = ft1.get();
		return seria;
	}
	
	/**
	 * Exécution dans la base de données
	 */
	private void exeBdd() {
		System.out.println("---------------- Execution (Base de Données) ----------------");
		seria.getT().actionBdd();
	}
	
	/**
	 * Envoie des données
	 * @throws NullPointerException
	 */
	private void methEnvoie(){
		System.out.println("---------------- Serialisation ----------------");	
		System.out.println(Authentification.CLIENTS.size());		
		//à destination de tous les utilisateurs connecter
		if(this.seria.getT().getDestinataire().equals("ALL")){
			System.out.println("Nature de demande ALL");
			for(ChatServeur socketThread : Authentification.CLIENTS){
				if(!(socketThread.message.getExpediteur().equals(this.seria.getT().getExpediteur()))){
					System.out.println(this.seria.getT().getExpediteur()+" envoie à "+socketThread.message.getExpediteur());
					threadEnvoie = new Thread(new Envoie(socketThread.socket,this.seria));
					threadEnvoie.start();
				}
			}
		//à destination du serveur qui renvoie au client
		}else if(this.seria.getT().getDestinataire().equals("SERVEUR")){
			System.out.println("Nature de demande SERVEUR");
			threadEnvoie = new Thread(new Envoie(this.socket,this.seria));
			threadEnvoie.start();
		//à destination d'un seul utilsateur
		}else{
			Socket socketVise = null;
			System.out.println("Nature de demande AUTRE");
			for(ChatServeur socketThread : Authentification.CLIENTS){
				if(socketThread.message.getExpediteur().equals(this.seria.getT().getDestinataire())){
					socketVise = socketThread.socket;
				}
			}
			threadEnvoie = new Thread(new Envoie(socketVise,this.seria));
			threadEnvoie.start();
		}
	}
}

 