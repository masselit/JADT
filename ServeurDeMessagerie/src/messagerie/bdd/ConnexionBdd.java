package messagerie.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Création d'un singleton
 * @autor Thibaut MASSELIN
 */

public class ConnexionBdd {
	private static Connection connection = null;
	private final static String URL = "jdbc:mysql://localhost/messagerie";
	private final static String USER = "client";
	private final static String PASSWRD = "fVvyx7a2usuwnfNr";
	
	/**
	 *  Constructor
	 */
	private ConnexionBdd(){
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(URL, USER, PASSWRD);
			
		} catch (ClassNotFoundException |SQLException e) {
			throw new IllegalStateException("Imposible de se connecter à la BDD!");
		}
	}

	/**
	 *  Retourne la connection
	 * @return Connection
	 */
	public static Connection getConnexion(){
		// si la connexion est null connexion par défaut
		if (connection == null) {
			ConnexionBdd.getInstance();
		}		
		return connection;
	}

	/**
	 *  Instance de connexion
	 */
	private static void getInstance(){
		if (connection == null) {
			new ConnexionBdd();
		}
	}
	
	/**
	 *  Ferme la connection
	 */
	
	public static void closeConnexion() {
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
