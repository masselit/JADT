package messagerie.model;


import java.util.ArrayList;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import messagerie.metier.Serialisation;

/**
 * Implémentation du designe pattern chain of responsibility
 * @author Thibaut MASSELIN
 * @date 12/11/2016
 */

public abstract class ChaineDeCommande {
	
	protected ChaineDeCommande suivant;
	
	protected transient Serialisation<?> deserialisation;
	
	@XStreamAlias("expediteur")
	protected String expediteur;
	@XStreamAlias("destinataire")
	protected String destinataire;
	
	/**
	 * permet de passer à la classe suivante dans la chaîne de classe 
	 * @param suivant
	 */
	public void setSuivant(ChaineDeCommande suivant){
		this.suivant = suivant;
	}
	
	/**
	 *  fonction récurcive qui a pour but de parcourir tous les maillions de la chaine
	 * @param xml
	 * @return Serialisation<?>
	 */
	public Serialisation<?> operation(String xml) throws IllegalArgumentException{	
		if(operationSpec(xml)){
			return deserialisation;
		}
		
		if(suivant != null){
			return suivant.operation(xml);
		}
		throw new IllegalArgumentException("erreur Aucune class ChaineDeCommande touvée");
		//créer une classe qui gerre la fin de boucle ou par une exeption
	}
	
	/**
	 * l'objet est t-il une ArrayList
	 * @param obj
	 * @return true si c'est un objet de type ArrayList
	 */
	protected boolean isList(Object obj){
		return obj.getClass().isAssignableFrom(new ArrayList<>().getClass());
    }
	
	/**
	 * Methodes à implémenter par les classes enfants
	 */
	
	abstract protected boolean operationSpec(String xml);
	abstract public ChaineDeCommande creer(Map<String,Object> contenuMap, String des,String exp);	
	abstract public void actionBdd();
	abstract public Map<String,Object> getValeurs();	
	abstract public String getExpediteur();
	abstract public String getDestinataire();
	abstract public ChaineDeCommande target();
}
