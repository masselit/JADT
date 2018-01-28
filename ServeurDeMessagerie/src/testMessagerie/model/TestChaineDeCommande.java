package testMessagerie.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import messagerie.metier.Serialisation;
import messagerie.model.ChaineDeCommande;
import messagerie.model.Message;
import messagerie.model.Utilisateur;
/**
 * @author Thibaut MASSELIN
 * @date 23/11/2016
 */
public class TestChaineDeCommande {

	private ChaineDeCommande maillionMessage;
	
	@Before
	public void initialize(){
		maillionMessage = new Message();	
 		ChaineDeCommande maillionUtilisateur = new Utilisateur(); 		
 		maillionMessage.setSuivant(maillionUtilisateur);
	}
	
	@Test
	public void testOperation() throws Exception {	
		assertEquals(new Serialisation<Message>().getClass(),maillionMessage.operation("<Message></Message>").getClass());
	}
	
	@Test
	public void testOperationNull(){
		try{
			maillionMessage.operation("<a></a>");
		}catch(IllegalArgumentException e){
			assertEquals("erreur Aucune class ChaineDeCommande touvée",e.getMessage());
		}
	}
	
	
}
