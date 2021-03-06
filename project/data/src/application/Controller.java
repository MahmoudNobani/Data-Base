package application;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Controller implements Initializable {

	private String dbURL;
	private static String dbUsername = "root"; // mysql user name
	private static String dbPassword = "1234"; // mysql password
	private static String URL = "127.0.0.1"; // location of db server
	private static String port = "3306"; // constant
	private static String dbName = "employeeDB"; // most likely will not change
	private static Connection con;
	
	@FXML
	Button add;
	@FXML
	Button show;
	@FXML
	Button remove;
	@FXML
	Button search;
	
	@FXML
	TableView<Employees> Table;
	@FXML
	TableColumn<Employees, String> Emp_id;
	@FXML
	TableColumn<Employees, String> Efname;
	@FXML
	TableColumn<Employees, String> Elname;
	@FXML
	TableColumn<Employees, String> dob;
	@FXML
	TableColumn<Employees, String> phoneN;
	@FXML
	TableColumn<Employees, String> address;
	@FXML
	TableColumn<Employees, Date> hire_date;
	@FXML
	TableColumn<Employees, String> base_salary;
	@FXML
	TableColumn<Employees, String> job_desc;
	@FXML
	TableColumn<Employees, String> Branch_id;
	@FXML
	TableColumn<Employees, String> passW;
	
	@FXML
    TextField SearchBar;
	
	@FXML
    TextField SearchID;



	ObservableList<Employees> oblist = FXCollections.observableArrayList();
	ObservableList<Employees> list1,list2;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// TODO Auto-generated method stub
		Emp_id.setCellValueFactory(new PropertyValueFactory<>("Emp_id"));
		Efname.setCellValueFactory(new PropertyValueFactory<>("Efname"));
		Elname.setCellValueFactory(new PropertyValueFactory<>("Elname"));
		dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
		phoneN.setCellValueFactory(new PropertyValueFactory<>("phoneN"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		hire_date.setCellValueFactory(new PropertyValueFactory<>("hire_date"));
		job_desc.setCellValueFactory(new PropertyValueFactory<>("job_desc"));
		base_salary.setCellValueFactory(new PropertyValueFactory<>("base_salary"));
		Branch_id.setCellValueFactory(new PropertyValueFactory<>("Branch_id"));
		passW.setCellValueFactory(new PropertyValueFactory<>("passW"));
		
		 
		

		showtable();
		try {
			search_user();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Table.setEditable(true);
		Efname.setCellFactory(TextFieldTableCell.forTableColumn());
		Elname.setCellFactory(TextFieldTableCell.forTableColumn());
		phoneN.setCellFactory(TextFieldTableCell.forTableColumn());
		address.setCellFactory(TextFieldTableCell.forTableColumn());
		passW.setCellFactory(TextFieldTableCell.forTableColumn());
		base_salary.setCellFactory(TextFieldTableCell.forTableColumn());
		Branch_id.setCellFactory(TextFieldTableCell.forTableColumn());
		//dob.setCellFactory(TextFieldTableCell.forTableColumn());
		
		
		//Emp_id.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	
	public void refresh(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		//Table.getItems().clear();
		showtable();
		search_user();
	}

	@FXML
	public void addEmp(ActionEvent event) throws IOException, InvocationTargetException, ClassNotFoundException, SQLException{
		
		Controller2 c = new Controller2();
		c.startUI();
		search_user();
		System.out.print("\n\nah\n\n");
	}
	
	public void delete(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		  ObservableList<Employees> singleEmp;
		  singleEmp= Table.getSelectionModel().getSelectedItems();
		  ArrayList<Employees> rows = new ArrayList<>(singleEmp);
		  //singleEmp.forEach(allEmp);
		  
		    
		    connectDB();
			
		    try {
		    	String sql = "delete from Employee where Emp_id ="+rows.get(0).getEmp_id()+ ";";
			    //PreparedStatement stmt = null;
		    	Statement stmt = con.createStatement();
			    //
			    //stmt.setString(1,Emp_id.getCellData(index));			
			    stmt.execute(sql);
			    JOptionPane.showMessageDialog(null, "Delete");
			   // Table.getItems().clear();
			    showtable();
			    search_user();
			} catch (Exception e) {
			        JOptionPane.showMessageDialog(null, e);
			}		 		    
		
	}
	
	
	
	public void search(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		connectDB();
		ObservableList<Employees> list = FXCollections.observableArrayList();
		//Table.getItems().clear();
		try {
		String sql = "select * from Employee where Emp_id = "+SearchID.getText()+";";
		//System.out.print(sql);
		Statement stmt = con.createStatement();
		//int x=Integer.parseInt();
		//System.out.print(SearchID.getText());
		//st.setString(1, "1");
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		Employees s = new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4),
				rs.getString(5), rs.getString(6), rs.getDate(7), rs.getString(8), rs.getString(9), rs.getString(10),
				rs.getString(11));
		 //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDate(4)+" "+rs.getString(5)+" "+rs.getInt(6)+" "+rs.getDate(7)+" "+rs.getFloat(8)+" "+rs.getString(9)+" "+rs.getInt(10)+" "+rs.getString(11));
		list.add(s);
		Table.setItems(list);
	   }
		catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	   }	
	}
	
	
	
	public void startUI() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interface1.fxml"));
		
	       // fxmlLoader.setLocation();
	        Scene scene = new Scene(fxmlLoader.load());
	        Stage stage = new Stage();
	        stage.setTitle("New Window");
	        stage.setScene(scene);
	        stage.show();
	}
 
	
	
	
	@FXML
    void search_user() throws ClassNotFoundException, SQLException {    
		
		connectDB();
		ObservableList<Employees> list = FXCollections.observableArrayList();
		
		String mystring = "select * from employee";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(mystring);
			while (rs.next()) {
				Employees s = new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getString(5), rs.getString(6), rs.getDate(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11));
				list.add(s);
			}

			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        Table.setItems(list);
        FilteredList<Employees> filteredData = new FilteredList<>(list, b -> true);  
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredData.setPredicate(person -> {
        		if (newValue == null || newValue.isEmpty()) {
        			return true;
        			}
        		
        		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        				
        		String lowerCaseFilter = newValue.toLowerCase();
        		//String salary=Float.toString(person.getBase_salary());
        		String id=Integer.toString(person.getEmp_id());
        	//	String branch=Integer.toString(person.getBranch_id());
        		String bday=dateFormat.format(person.getDob());
        		String hire=dateFormat.format(person.getHire_date());
        		
        		if (person.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
        			return true; // Filter matches address
        		} else if (person.getPassW().toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches password
        		}else if (person.getEfname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches first name
        		}
        		else if (person.getElname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches last name
        		}
        		
        		else if (person.getBase_salary().toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches salary
        		}
        		else if (id.toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches emp id
        		}
        		else if (person.getBranch_id().toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches branch id
        		}
        		else if (bday.toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches bday
        		}
        		else if (hire.toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches hire day
        		}
        		else if (person.getJob_desc().toLowerCase().indexOf(lowerCaseFilter) != -1) {
        			return true; // Filter matches branch id
        		}
                else  
                     return false; // Does not match.
        		});
        	});  
        SortedList<Employees> sortedData = new SortedList<>(filteredData);  
        sortedData.comparatorProperty().bind(Table.comparatorProperty());  
        Table.setItems(sortedData);     
        }
	
	
	 public void updateFname(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			//ObservableList<Employees> singleEmp;
			//singleEmp= Table.getSelectionModel().getSelectedItems();
			//ArrayList<Employees> rows = new ArrayList<>(singleEmp);
			//rows.get(0).setEfname(edittedCell.getNewValue().toString());
			Employees e = Table.getSelectionModel().getSelectedItem();
		    e.setEfname(Ev.getNewValue());
		    String s = "'" + e.getEfname() + "'";
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set Efname = " + s + " where Emp_id = "+e.getEmp_id()+";";
	        PreparedStatement st = con.prepareStatement(mystring);
	        st.executeUpdate();
	        con.close();
	        //personSelected.(edittedCell.getNewValue().toString());
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	
	    
}
	 
	 @FXML
	 public void updateLname(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			Employees e = Table.getSelectionModel().getSelectedItem();
		    e.setElname(Ev.getNewValue());
		    String s = "'" + e.getElname() + "'";
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set Elname = " + s + " where Emp_id = "+e.getEmp_id()+";";
	        PreparedStatement st = con.prepareStatement(mystring);
	        st.executeUpdate();
	        con.close();
	        //personSelected.(edittedCell.getNewValue().toString());
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	
	    
}
	 
	 @FXML
	 public void updatephoneN(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			Employees e = Table.getSelectionModel().getSelectedItem();
		    e.setPhoneN(Ev.getNewValue());
		    String s = "'" + e.getPhoneN() + "'";
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set phoneN = " + s + " where Emp_id = "+e.getEmp_id()+";";
	        PreparedStatement st = con.prepareStatement(mystring);
	        st.executeUpdate();
	        con.close();
	        //personSelected.(edittedCell.getNewValue().toString());
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	
	    
}
	 
	 @FXML
	 public void updateaddress(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			Employees e = Table.getSelectionModel().getSelectedItem();
		    e.setAddress(Ev.getNewValue());
		    String s = "'" + e.getAddress() + "'";
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set address = " + s + " where Emp_id = "+e.getEmp_id()+";";
	        PreparedStatement st = con.prepareStatement(mystring);
	        st.executeUpdate();
	        con.close();
	        //personSelected.(edittedCell.getNewValue().toString());
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	
	    
}
	 
	 
	 @FXML
	 public void updatepassW(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			Employees e = Table.getSelectionModel().getSelectedItem();
		    e.setPassW(Ev.getNewValue());
		    String s = "'" + e.getPassW() + "'";
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set passW = " + s + " where Emp_id = "+e.getEmp_id()+";";
	        PreparedStatement st = con.prepareStatement(mystring);
	        st.executeUpdate();
	        con.close();
	        //personSelected.(edittedCell.getNewValue().toString());
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	
	    
}

	 
	 @FXML
	 public void updateSalary(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			Employees e = Table.getSelectionModel().getSelectedItem();
		    e.setBase_salary(Ev.getNewValue());
		    String s = "'" + e.getBase_salary() + "'";
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set base_salary = " + s + " where Emp_id = "+e.getEmp_id()+";";
	        PreparedStatement st = con.prepareStatement(mystring);
	        st.executeUpdate();
	        con.close();
	        //personSelected.(edittedCell.getNewValue().toString());
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	
	    
}
	 
	 @FXML
	 public void updateBranch(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			Employees e = Table.getSelectionModel().getSelectedItem();
		    e.setBranch_id(Ev.getNewValue());
		    String s = "'" + e.getBranch_id() + "'";
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set Branch_id = " + s + " where Emp_id = "+e.getEmp_id()+";";
	        PreparedStatement st = con.prepareStatement(mystring);
	        st.executeUpdate();
	        con.close();
	        //personSelected.(edittedCell.getNewValue().toString());
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	}	
	    
}
	 
	 @FXML
	 public void updatedob(TableColumn.CellEditEvent<Employees, String> Ev)
	    {
		 try {
			Employees e = Table.getSelectionModel().getSelectedItem();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			Date d = formatter.parse(Ev.getNewValue());
		    e.setDob(d);
		    String s = "'" + e.getDob() + "'";
		    java.sql.Date sqlDate = new java.sql.Date(e.getDob().getTime());
		    System.out.print(s);
	        connectDB();
	        String mystring = "update employee set Dob = '" + sqlDate + "' where Emp_id = "+e.getEmp_id()+";";
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
		String mystring = "select * from employee";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(mystring);
			while (rs.next()) {
				Employees s = new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4),
						rs.getString(5), rs.getString(6), rs.getDate(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11));
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
