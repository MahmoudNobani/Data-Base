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

public class invCon implements Initializable{
	
	private String dbURL;
	private static String dbUsername = "root"; // mysql user name
	private static String dbPassword = "1234"; // mysql password
	private static String URL = "127.0.0.1"; // location of db server
	private static String port = "3306"; // constant
	private static String dbName = "employeeDB"; // most likely will not change
	private static Connection con;
	
	ObservableList<Inv> oblist = FXCollections.observableArrayList();
	
	    @FXML
	    private TableView<Inv> Table;

	    @FXML
	    private TableColumn<Inv, String> title;
	    
	    @FXML
	    private TableColumn<Inv, String> type1;

	    @FXML
	    private TableColumn<Inv, String> branch_id;

	    @FXML
	    private TableColumn<Inv, String> drental_price;

	    @FXML
	    private TableColumn<Inv, String> dvd_id;

	    @FXML
	    private TableColumn<Inv, String> p_year;

	    @FXML
	    private TableColumn<Inv, String> purchase_price;

	    @FXML
	    private TableColumn<Inv, String> rating;

	    @FXML
	    private Button refresh;

	    @FXML
	    private Button remove;

	    @FXML
	    private Button search;
	    
	    @FXML
	    private Button add;

	    @FXML
	    private TextField txtgenre;

	    @FXML
	    private TextField txtname;

	    @FXML
	    private TextField txtprice;

	    @FXML
	    private TextField txtrate;

	    @FXML
	    private TextField txtrent;

	    @FXML
	    private TextField txtsearch;

	    @FXML
	    private TextField txtyear;
	    
	    @FXML
	    private TextField bID;
	    
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
	    	title.setCellValueFactory(new PropertyValueFactory<>("title"));
	    	type1.setCellValueFactory(new PropertyValueFactory<>("type1"));
	    	branch_id.setCellValueFactory(new PropertyValueFactory<>("branch_id"));
	    	drental_price.setCellValueFactory(new PropertyValueFactory<>("drental_price"));
	    	
	    	dvd_id.setCellValueFactory(new PropertyValueFactory<>("dvd_id"));
	    	p_year.setCellValueFactory(new PropertyValueFactory<>("p_year"));
	    	purchase_price.setCellValueFactory(new PropertyValueFactory<>("purchase_price"));
	    	rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
			
			showtable();
			
			Table.setEditable(true);
			
			title.setCellFactory(TextFieldTableCell.forTableColumn());
			type1.setCellFactory(TextFieldTableCell.forTableColumn());
			branch_id.setCellFactory(TextFieldTableCell.forTableColumn());
		    drental_price.setCellFactory(TextFieldTableCell.forTableColumn());
		    purchase_price.setCellFactory(TextFieldTableCell.forTableColumn());
		    rating.setCellFactory(TextFieldTableCell.forTableColumn());
		    p_year.setCellFactory(TextFieldTableCell.forTableColumn());
		    
	    }
	    
	    public void refresh(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
			Table.getItems().clear();
			showtable();
		}
	    
	    
	    @FXML
	    public void ADD(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
	 
			connectDB();
			try {
			PreparedStatement st;
			String sql = "insert into inventory (title,purchase_price,drental_price,type1,p_year,rating,branch_id) values (?,?,?,?,?,?,?)"+";";
			st = con.prepareStatement(sql);
			st.setString(1, txtname.getText());
			st.setString(2, txtprice.getText());
			st.setString(3, txtrent.getText());
			st.setString(4, txtgenre.getText());
			st.setString(4, txtgenre.getText());
			st.setString(5, txtrate.getText());
			st.setString(6, txtyear.getText());
			st.setString(7, bID.getText());
			st.execute();
			JOptionPane.showMessageDialog(null, "add");
			} catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		}	

		}
	    
		public void delete(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
			  ObservableList<Inv> x;
			  x= Table.getSelectionModel().getSelectedItems();
			  ArrayList<Inv> rows = new ArrayList<>(x);
			  //singleEmp.forEach(allEmp);
			  
			    
			    connectDB();
				
			    try {
			    	String sql = "delete from inventory where dvd_id ="+rows.get(0).getDvd_id()+ ";";
				    //PreparedStatement stmt = null;
			    	Statement stmt = con.createStatement();
				    //
				    //stmt.setString(1,Emp_id.getCellData(index));			
				    stmt.execute(sql);
				    JOptionPane.showMessageDialog(null, "Delete");
				   // Table.getItems().clear();
				   // showtable(); 
				} catch (Exception e) {
				        JOptionPane.showMessageDialog(null, e);
				}		 		    
			
		}
		
		
		public void startUI() throws IOException {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inv.fxml"));
			
		       // fxmlLoader.setLocation();
		        Scene scene = new Scene(fxmlLoader.load());
		        Stage stage = new Stage();
		        stage.setTitle("New Window");
		        stage.setScene(scene);
		        stage.show();
		}
	 
		
		public void search(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
			System.out.print("hi");
			connectDB();
			ObservableList<Inv> list = FXCollections.observableArrayList();
			Table.getItems().clear();
			try {
			String st = "'" + txtsearch.getText() + "'";
			System.out.print(st);
			String sql = "select * from inventory where title = "+st+";";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
			Inv s = new Inv(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
			 //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDate(4)+" "+rs.getString(5)+" "+rs.getInt(6)+" "+rs.getDate(7)+" "+rs.getFloat(8)+" "+rs.getString(9)+" "+rs.getInt(10)+" "+rs.getString(11));
			list.add(s);
			Table.setItems(list);
			}
		   }
			catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		   }	
		}
		
		 @FXML
		 public void updateTitle(TableColumn.CellEditEvent<branch, String> Ev)
		    {
			 try {
				Inv e = Table.getSelectionModel().getSelectedItem();
			    e.setTitle(Ev.getNewValue());
			    String s = "'" + e.getTitle() + "'";
			    System.out.print(s);
		        connectDB();
		        String mystring = "update inventory set title = " + s + " where dvd_id = "+e.getDvd_id()+";";
		        PreparedStatement st = con.prepareStatement(mystring);
		        st.executeUpdate();
		        con.close();
		        //personSelected.(edittedCell.getNewValue().toString());
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		}	
		    
	}
		 
		 @FXML
		 public void updatePurchase_price(TableColumn.CellEditEvent<branch, String> Ev)
		    {
			 try {
				Inv e = Table.getSelectionModel().getSelectedItem();
			    e.setPurchase_price(Ev.getNewValue());
			    String s = "'" + e.getPurchase_price() + "'";
			    System.out.print(s);
		        connectDB();
		        String mystring = "update inventory set purchase_price = " + s + " where dvd_id = "+e.getDvd_id()+";";
		        PreparedStatement st = con.prepareStatement(mystring);
		        st.executeUpdate();
		        con.close();
		        //personSelected.(edittedCell.getNewValue().toString());
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		}	
		    
	}
		 
		 @FXML
		 public void updateRent(TableColumn.CellEditEvent<branch, String> Ev)
		    {
			 try {
				Inv e = Table.getSelectionModel().getSelectedItem();
			    e.setDrental_price(Ev.getNewValue());
			    String s = "'" + e.getDrental_price() + "'";
			    System.out.print(s);
		        connectDB();
		        String mystring = "update inventory set drental_price = " + s + " where dvd_id = "+e.getDvd_id()+";";
		        PreparedStatement st = con.prepareStatement(mystring);
		        st.executeUpdate();
		        con.close();
		        //personSelected.(edittedCell.getNewValue().toString());
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		}	
		    
	}
		 
		 
		 @FXML
		 public void updateGenre(TableColumn.CellEditEvent<branch, String> Ev)
		    {
			 try {
				Inv e = Table.getSelectionModel().getSelectedItem();
			    e.setType1(Ev.getNewValue());
			    String s = "'" + e.getType1() + "'";
			    System.out.print(s);
		        connectDB();
		        String mystring = "update inventory set type1 = " + s + " where dvd_id = "+e.getDvd_id()+";";
		        PreparedStatement st = con.prepareStatement(mystring);
		        st.executeUpdate();
		        con.close();
		        //personSelected.(edittedCell.getNewValue().toString());
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		}	
		    
	}
		 
		 @FXML
		 public void updatep_year(TableColumn.CellEditEvent<branch, String> Ev)
		    {
			 try {
				Inv e = Table.getSelectionModel().getSelectedItem();
			    e.setP_year(Ev.getNewValue());
			    String s = "'" + e.getP_year() + "'";
			    System.out.print(s);
		        connectDB();
		        String mystring = "update inventory set p_year = " + s + " where dvd_id = "+e.getDvd_id()+";";
		        PreparedStatement st = con.prepareStatement(mystring);
		        st.executeUpdate();
		        con.close();
		        //personSelected.(edittedCell.getNewValue().toString());
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		}	
		    
	}
		 
		 @FXML
		 public void updaterating(TableColumn.CellEditEvent<branch, String> Ev)
		    {
			 try {
				Inv e = Table.getSelectionModel().getSelectedItem();
			    e.setRating(Ev.getNewValue());
			    String s = "'" + e.getRating() + "'";
			    System.out.print(s);
		        connectDB();
		        String mystring = "update inventory set rating = " + s + " where dvd_id = "+e.getDvd_id()+";";
		        PreparedStatement st = con.prepareStatement(mystring);
		        st.executeUpdate();
		        con.close();
		        //personSelected.(edittedCell.getNewValue().toString());
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		}	
		    
	}
		 
		 @FXML
		 public void updatebranch_id(TableColumn.CellEditEvent<branch, String> Ev)
		    {
			 try {
				Inv e = Table.getSelectionModel().getSelectedItem();
			    e.setBranch_id(Ev.getNewValue());
			    String s = "'" + e.getBranch_id() + "'";
			    System.out.print(s);
		        connectDB();
		        String mystring = "update inventory set branch_id = " + s + " where dvd_id = "+e.getDvd_id()+";";
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
			String mystring = "select * from inventory";

			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(mystring);
				while (rs.next()) {
					Inv s = new Inv(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
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
