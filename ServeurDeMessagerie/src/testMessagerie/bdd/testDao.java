package testMessagerie.bdd;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.statement.IStatementFactory;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class testDao implements IDatabaseConnection {

	public IDatabaseTester databaseTester;
	private static Connection connection = null;
	private final static String URL = "jdbc:mysql://localhost/messagerie";
	private final static String USER = "root";
	private final static String PASSWRD = "";//root
	
	
	
			
	public void setUp() throws Exception{
		
	
	Connection conn = getConnection();
	QueryDataSet partDS = new QueryDataSet((IDatabaseConnection) conn);
	partDS.addTable("messagerie","SELECT * FROM employee WHERE salary>=5000");
	FlatXmlDataSet.write(partDS, new FileOutputStream("messagerie.xml"));	
		
		
	databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver","jdbc:mysql://localhost/test","root", "root");
	IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("customer- init.xml"));
	databaseTester.setDataSet(dataSet); databaseTester.onSetup();                                                    
	}                                                                          
	 
	public void testInsert() {
	//your test method here
	}

	
	@Override
	public Connection getConnection() throws SQLException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		connection = (Connection) DriverManager.getConnection(URL, USER, PASSWRD);
		
		return connection;
	}

	
	
	
	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IDataSet createDataSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDataSet createDataSet(String[] arg0) throws SQLException, DataSetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable createQueryTable(String arg0, String arg1) throws DataSetException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable createTable(String arg0) throws DataSetException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable createTable(String arg0, PreparedStatement arg1) throws DataSetException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatabaseConfig getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowCount(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSchema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStatementFactory getStatementFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
	