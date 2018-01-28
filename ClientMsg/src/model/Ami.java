package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Dorian THIVOLLE
 * @date 23/11/2016
 *
 */

public class Ami {

    private final StringProperty pseudo;
    private StringProperty etat;
    
    public Ami() {
        this(null);
    }

    public Ami(String pseudo) {
        this.pseudo = new SimpleStringProperty(pseudo);
        this.etat = new SimpleStringProperty("false");
    }

    public String getPseudo() {
        return pseudo.get();
    }

    public StringProperty PseudoProperty() {
        return pseudo;
    }

	public String getEtat() {
		return etat.get();
	}

	public void setEtat(String etat) {
		this.etat.set(etat);
	}
	
	 public StringProperty EtatProperty() {
	        return etat;
	    }

}