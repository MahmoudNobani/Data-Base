package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addManEmp implements Initializable {
	
	private String dbURL;
	private static String dbUsername = "root"; // mysql user name
	private static String dbPassword = "1234"; // mysql password
	private static String URL = "127.0.0.1"; // location of db server
	private static String port = "3306"; // constant
	private static String dbName = "employeeDB"; // most likely will not change
	private static Connection con;
	
	private static int brID;

    @FXML
    private TextField Taddress;

    @FXML
    private TextField Tbday;

    @FXML
    private TextField Tfname;

    @FXML
    private TextField Tlname;

    @FXML
    private TextField Tpassword;

    @FXML
    private TextField Tpn;

    @FXML
    private TextField Tsalary;

    @FXML
    private Button finish;

    @FXML
    private ComboBox<String> job;

    @FXML
    private Label l1;
 
    @FXML
    public void finishADD(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	
    	
    	
    	
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		System.out.print("\n\n"+Tfname.getText());
		
		connectDB();
		try {
		PreparedStatement st;
		String sql = "insert into Employee (Efname,Elname,Dob,phoneN,address,hire_date,base_salary,job_desc,Branch_id,passW) values(?,?,?,?,?,?,?,'employee', "+brID+" ,?)"+";";
		st = con.prepareStatement(sql);
		st.setString(1, Tfname.getText());
		st.setString(2, Tlname.getText());
		st.setString(3, Tbday.getText());
		st.setString(4, Tpn.getText());
		st.setString(5, Taddress.getText());
		st.setDate(6, sqlDate);
		st.setString(7, Tsalary.getText());
		st.setString(8, Tpassword.getText());
		st.execute();
		JOptionPane.showMessageDialog(null, "add");
		} catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	

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
	
	public void startUI(int x) throws IOException {
		brID = x;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addForMan.fxml"));
		
	       // fxmlLoader.setLocation();
	        Scene scene = new Scene(fxmlLoader.load());
	        Stage stage = new Stage();
	        stage.setTitle("New Window");
	        stage.setScene(scene);
	        stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
