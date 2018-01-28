package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Dorian THIVOLLE
 * @date 23/11/2016
 *
 */

public class Groupe {
	
	private final StringProperty nom;
    
    public Groupe() {
        this(null);
    }

    public Groupe(String nom) {
        this.nom = new SimpleStringProperty(nom);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty NomProperty() {
        return nom;
    }
	
}
