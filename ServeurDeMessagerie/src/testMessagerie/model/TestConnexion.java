package testMessagerie.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import messagerie.model.Connexion;
import messagerie.model.Utilisateur;
/**
 * @author Thibaut MASSELIN
 * @date 15/11/2016
 */
public class TestConnexion {
	
	@Test
	public void testoperationSpec()throws Exception{
		Connexion connexion = new Connexion();
		//NullPinterException -> non connectée à la bdd
		assertEquals(false,connexion.operationSpec("<lolo></lolo>"));
		assertEquals(true,connexion.operationSpec("<Connexion></Connexion>"));
	}
	@Test
	public void testCreerGetSet()throws Exception{
		Connexion connexion = new Connexion();
		connexion.setDestinataire("valeurDestinataire");
		connexion.setExpediteur("valeurExpediteur");
		connexion.setMotDePasse("UnMotDePasse");
		connexion.setIdentifiant("lolo");
		
		assertEquals("valeurDestinataire",connexion.getDestinataire());
		assertEquals("valeurExpediteur",connexion.getExpediteur());
		assertEquals("UnMotDePasse",connexion.getMotDePasse());
		assertEquals("lolo",connexion.getIdentifiant());
		assertEquals(new Connexion().getClass(),connexion.creer(connexion.getValeurs(), connexion.getDestinataire(), connexion.getExpediteur()).getClass());
	}
	
	@Test
	public void testTarget()throws Exception{
		Connexion connexion = new Connexion();
		assertEquals(new Utilisateur().getClass(),connexion.target().getClass());
	}
}
