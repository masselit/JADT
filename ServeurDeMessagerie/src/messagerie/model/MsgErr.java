package messagerie.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Dernier Maillon de la chaîne
 * @author Thibaut MASSELIN
 * @date 09/12/2016
 *
 */

@XStreamAlias("MsgErr")
public class MsgErr extends ChaineDeCommande {
	@XStreamAlias("messageErreur")
	private String messageErreur;
	
	public MsgErr(){
		setMessageErreur(new String());
	}
	
	@Override
	protected boolean operationSpec(String xml) {
		// ne fait pas partie de la chaine
		return false;
	}

	@Override
	public ChaineDeCommande creer(Map<String, Object> contenuMap, String des, String exp) {		
		for(Entry<String, Object> obj : contenuMap.entrySet()){
			if(obj.getKey().equals("messageErreur")){
				this.setMessageErreur((String) obj.getValue());
			}
		}
		return this;
	}

	@Override
	public void actionBdd() {
		//rien
	}

	@Override
	public Map<String, Object> getValeurs() {
		Map<String,Object> maps = new HashMap<>();	
		maps.put("messageErreur", getMessageErreur());
		return maps;
	}

	@Override
	public String getExpediteur() {
		return expediteur;
	}

	@Override
	public String getDestinataire() {
		return destinataire;
	}

	public String getMessageErreur() {
		return messageErreur;
	}

	public void setMessageErreur(String messageErreur) {
		this.messageErreur = messageErreur;
	}

	@Override
	public ChaineDeCommande target() {
		return this;
	}

}
