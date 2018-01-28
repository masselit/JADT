package model;


import java.util.ArrayList;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import metier.Serialisation;

/**
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
	
	
	public void setSuivant(ChaineDeCommande suivant){
		this.suivant = suivant;
	}
	
	/**
	 *  fonction récurcive qui a pour but de parcourir tous les maillions de la chaine
	 * @param xml
	 */
	public Serialisation<?> operation(String xml){
		if(operationSpec(xml)){
			return deserialisation;
		}
		
		if(suivant != null){
			return suivant.operation(xml);
		}
		
		System.out.println("il n'y a rien qui corespond a la demande");
		return null;//créer une classe qui gerre la fin de boucle ou par une exeption
	}
	
	protected boolean isList(Object obj){
		return obj.getClass().isAssignableFrom(new ArrayList<>().getClass());
    }
	
	
	abstract protected boolean operationSpec(String xml);
	abstract public ChaineDeCommande creer(Map<String,Object> contenuMap, String des,String exp);
	abstract public Map<String,Object> getValeurs();
	abstract public String getExpediteur();
	abstract public String getDestinataire();
}
