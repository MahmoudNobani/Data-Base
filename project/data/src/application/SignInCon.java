package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SignInCon implements Initializable {
	

	private String dbURL;
	private static String dbUsername = "root"; // mysql user name
	private static String dbPassword = "1234"; // mysql password
	private static String URL = "127.0.0.1"; // location of db server
	private static String port = "3306"; // constant
	private static String dbName = "employeeDB"; // most likely will not change
	private static Connection con;
	private static String job;
	private static int emp;
	
    @FXML
    private TextField ID;

    @FXML
    private PasswordField pass;

    @FXML
    private Button login;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }
    
    public void login(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        //Window owner = submitButton.getScene().getWindow();

        if (ID.getText().isEmpty()) {
            //showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter your email id");
            return;
        }
        if (pass.getText().isEmpty()) {
            //showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter a password");
            return;
        }

        // JdbcDao jdbcDao = new JdbcDao();
        boolean flag = checkUser();

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {
        	System.out.println("found");
        	//System.out.println(job);
        	if (job.equals("gm")) {
        		System.out.println("gm1");
        		gmcon c = new gmcon();
        		c.startUIgm(emp);
        	}
        	else if (job.equals("manager")) {
        		manager c1 = new manager();
        		c1.startUI(emp);
        	}
        	else if (job.equals("employee")) {
        		rEmp c2 = new rEmp();
        		c2.startUI(emp);
        	}
            //XMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation(getClass().getResource("Ibra.fxml"));
            //Scene scene = new Scene(fxmlLoader.load());
            //Stage stage = new Stage();
            //stage.setTitle("Add Supplier");
            //stage.setScene(scene);
            //stage.show();
        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    
    public boolean checkUser() throws ClassNotFoundException, SQLException {
    	connectDB();
    	try { 
            String sqlQuery = "select * from employee where Emp_id = ? and passW = ?";
            PreparedStatement addStatment = con.prepareStatement(sqlQuery);
            addStatment.setString(1, ID.getText());
            addStatment.setString(2, pass.getText());
            ResultSet rs=addStatment.executeQuery();
            if (rs.next()) {
            	String sqlQuery2 = "select * from employee where Emp_id = ?";
            	PreparedStatement st = con.prepareStatement(sqlQuery2);
            	st.setString(1, ID.getText());
            	System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDate(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getDate(7)+" "+rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11));
                job=rs.getString(9);
                emp=rs.getInt(1);
            	return true;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    	
    	
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
    
    
    
    
}