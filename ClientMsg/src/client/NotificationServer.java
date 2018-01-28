package client;

import java.io.BufferedReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import metier.Reception;
import metier.Serialisation;
import view.MessagerieController;
/**
 * 
 * @author Dorian Thivolle
 * @author Thibaut
 *
 */
public class NotificationServer implements Runnable{
	private BufferedReader in;
	private MessagerieController messagerieController;
	private Thread t;
	public NotificationServer(BufferedReader in, MessagerieController messagerieController) {
		this.in = in;
		this.messagerieController = messagerieController;
	}

	@Override
	public void run() {
		while(true){
			Callable<Serialisation<?>> c1 = new Reception(in);
			FutureTask<Serialisation<?>> ft1 = new FutureTask<>(c1);
		    t = new Thread(ft1);
			t.start();
			Serialisation<?> seria =null;
			try {
				seria = ft1.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			System.out.println("sa passe");
			messagerieController.setMessgae(seria);
		}
		
	}

}
