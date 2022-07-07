package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDB {
	
	   private String DbURL;
	   private final String DbUsername;
	   private final String DbPassword;
	   private final String URl;
	   private final String port;
	   private final String DBname;
	
	public ConnectDB () {
		 this.URl = "127.0.0.1";
	     this.port = "3306";
	     this.DBname = "employeeDB";
	     this.DbUsername = "root";
	     this.DbPassword = "1234";
	}
	
	Connection connect() throws ClassNotFoundException, SQLException {

		DbURL = "jdbc:mysql://" + this.URl + ":" + this.port + "/" + this.DBname + "?verifyServerCertificate=false";
        Properties properties = new Properties();
        properties.setProperty("user", DbUsername);
        properties.setProperty("password", DbPassword);
        properties.setProperty("useSSl", "false");
        properties.setProperty("autoReconnect", "true");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DbURL, properties);

	}
}
