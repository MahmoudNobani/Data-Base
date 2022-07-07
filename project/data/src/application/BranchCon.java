package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class BranchCon implements Initializable{
	
	private String dbURL;
	private static String dbUsername = "root"; // mysql user name
	private static String dbPassword = "1234"; // mysql password
	private static String URL = "127.0.0.1"; // location of db server
	private static String port = "3306"; // constant
	private static String dbName = "employeeDB"; // most likely will not change
	private static Connection con;
	
	ObservableList<branch> oblist = FXCollections.observableArrayList();
	
	    @FXML
	    private TableColumn<branch, String> Address;
	    
	    @FXML
	    private TableView<branch> Table;

	    @FXML
	    private TableColumn<branch, String> ID;

	    @FXML
	    private TableColumn<?, String> Line;

	    @FXML
	    private Button add;

	    @FXML
	    private Button delete;

	    @FXML
	    private Button refresh;

	    @FXML
	    private TextField txtAdd;

	    @FXML
	    private TextField txtLine;

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
			Line.setCellValueFactory(new PropertyValueFactory<>("Line"));
			Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
			
			showtable();
			
			Table.setEditable(true);
			
			Line.setCellFactory(TextFieldTableCell.forTableColumn());
			Address.setCellFactory(TextFieldTableCell.forTableColumn());
		}
		
		
		public void refresh(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
			Table.getItems().clear();
			showtable();
		}
		
		 public void ADD(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {

				connectDB();
				try {
				PreparedStatement st;
				String sql = "insert into Branch (address,Landline) values(?,?)"+";";
				st = con.prepareStatement(sql);
				st.setString(1, txtAdd.getText());
				st.setString(2, txtLine.getText());
				st.execute();
				JOptionPane.showMessageDialog(null, "addition succesfull");
				} catch (Exception e) {
			        JOptionPane.showMessageDialog(null, e);
			}	

			}
		 
			public void delete(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
				  ObservableList<branch> singleEmp;
				  singleEmp= Table.getSelectionModel().getSelectedItems();
				  ArrayList<branch> rows = new ArrayList<>(singleEmp);
				  //singleEmp.forEach(allEmp);
				  
				    
				    connectDB();
					
				    try {
				    	String sql = "delete from Branch where branch_id ="+rows.get(0).getID()+ ";";
					    //PreparedStatement stmt = null;
				    	Statement stmt = con.createStatement();
					    //
					    //stmt.setString(1,Emp_id.getCellData(index));			
					    stmt.execute(sql);
					    JOptionPane.showMessageDialog(null, "Delete succesfull");
					    Table.getItems().clear();
					    showtable();
					} catch (Exception e) {
					        JOptionPane.showMessageDialog(null, e);
					}		 		    
				
			}
		
			
			 @FXML
			 public void updateaddress(TableColumn.CellEditEvent<branch, String> Ev)
			    {
				 try {
					branch e = Table.getSelectionModel().getSelectedItem();
				    e.setAddress(Ev.getNewValue());
				    String s = "'" + e.getAddress() + "'";
				    System.out.print(s);
			        connectDB();
			        String mystring = "update Branch set address = " + s + " where branch_id = "+e.getID()+";";
			        PreparedStatement st = con.prepareStatement(mystring);
			        st.executeUpdate();
			        con.close();
			        //personSelected.(edittedCell.getNewValue().toString());
			    } catch (Exception e) {
			        JOptionPane.showMessageDialog(null, e);
			}	
			    
		}
			 
				public void startUI() throws IOException {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("branch.fxml"));
					
				       // fxmlLoader.setLocation();
				        Scene scene = new Scene(fxmlLoader.load());
				        Stage stage = new Stage();
				        stage.setTitle("New Window");
				        stage.setScene(scene);
				        stage.show();
				}
			 
			 @FXML
			 public void updateline(TableColumn.CellEditEvent<branch, String> Ev)
			    {
				 try {
					branch e = Table.getSelectionModel().getSelectedItem();
				    e.setLine(Ev.getNewValue());
				    String s = "'" + e.getLine() + "'";
				    System.out.print(s);
			        connectDB();
			        String mystring = "update Branch set Landline = " + s + " where branch_id = "+e.getID()+";";
			        PreparedStatement st = con.prepareStatement(mystring);
			        st.executeUpdate();
			        con.close();
			        //personSelected.(edittedCell.getNewValue().toString());
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
		
		private void showtable() {
			try {
				connectDB();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String mystring = "select * from Branch";

			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(mystring);
				while (rs.next()) {
					branch s = new branch(rs.getInt(1), rs.getString(2), rs.getString(3));
					oblist.add(s);
					Table.setItems(oblist);
					
					// System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+"
					// "+rs.getDate(4)+" "+rs.getString(5)+" "+rs.getInt(6)+" "+rs.getDate(7)+"
					// "+rs.getFloat(8)+" "+rs.getString(9)+" "+rs.getInt(10)+" "+rs.getString(11));
					

				}
				
				
				rs.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}

}
