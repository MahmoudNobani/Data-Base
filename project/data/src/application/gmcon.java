package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class gmcon implements Initializable {
	
	private String dbURL;
	private static String dbUsername = "root"; // mysql user name
	private static String dbPassword = "1234"; // mysql password
	private static String URL = "127.0.0.1"; // location of db server
	private static String port = "3306"; // constant
	private static String dbName = "employeeDB"; // most likely will not change
	private static Connection con;
	
	private static int empID;
	
	
    @FXML
    Label ID;

    @FXML
    private Label address;
    
    @FXML
    private Label lname;

    @FXML
    private Label salary;

    @FXML
    private Label branchID;

    @FXML
    private Label fname;

    @FXML
    private Button inv;
    
    @FXML
    private Button branch;
    
    @FXML
    private Button emp;

    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub	
		try {
			labels();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void branch(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
		BranchCon c1= new BranchCon();
		c1.startUI();
	}
	
	public void e(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
		Controller c2= new Controller();
		c2.startUI();
		
	}
	
	public void i(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
		invCon c3= new invCon();
		c3.startUI();
	}
	
	private void connectDB() throws ClassNotFoundException, SQLException {

		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection(dbURL, p);

	}
	
	public void startUIgm(int x) throws IOException, ClassNotFoundException, SQLException {
		empID=x;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gm.fxml"));
		
	       // fxmlLoader.setLocation();
	        Scene scene = new Scene(fxmlLoader.load());
	        Stage stage = new Stage();
	        stage.setTitle("New Window");
	        stage.setScene(scene);
	        stage.show();
	    
	       // labels();    
	}
	
	public void labels() throws SQLException, ClassNotFoundException {
		 connectDB();
		 String sql = "select * from employee where Emp_id = "+empID;
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(sql);
		 System.out.println("2");
		 while (rs.next()) {
			 
			 //System.out.println();
	         ID.setText(rs.getString(1));
	         address.setText(rs.getString(6));
	         lname.setText(rs.getString(3));
	         fname.setText(rs.getString(2));
	         salary.setText(rs.getString(8));
	         branchID.setText("branch:"+rs.getString(10));
		 }
        
	}
	
}
