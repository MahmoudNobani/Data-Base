
package application;

import java.util.Date;

public class Employees {
	private int Emp_id;
    private String Efname;
    private String Elname;
    private Date dob;
    private String phoneN;
    private String address;
    private Date hire_date;
    private String base_salary;
    private String job_desc;
    private String Branch_id;
    private String passW;
    
    public Employees(int s1, String s2, String s3, Date s4, String s5, String s6, Date s7, String s8, String s9, String s10, String s11) {
    	this.Emp_id=s1;
    	this.Efname=s2;
    	this.Elname=s3;
    	this.dob=s4;
    	this.phoneN=s5;
    	this.address=s6;
    	this.hire_date=s7;
    	this.base_salary=s8;
    	this.job_desc=s9;
    	this.Branch_id=s10;
    	this.passW=s11; 
    }

	public int getEmp_id() {
		return Emp_id;
	}

	public void setEmp_id(int emp_id) {
		Emp_id = emp_id;
	}

	public String getEfname() {
		return Efname;
	}

	public void setEfname(String efname) {
		Efname = efname;
	}

	public String getElname() {
		return Elname;
	}

	public void setElname(String elname) {
		Elname = elname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoneN() {
		return phoneN;
	}

	public void setPhoneN(String phoneN) {
		this.phoneN = phoneN;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public String getBase_salary() {
		return base_salary;
	}

	public void setBase_salary(String base_salary) {
		this.base_salary = base_salary;
	}

	public String getJob_desc() {
		return job_desc;
	}

	public void setJob_desc(String job_desc) {
		this.job_desc = job_desc;
	}

	public String getBranch_id() {
		return Branch_id;
	}

	public void setBranch_id(String branch_id) {
		Branch_id = branch_id;
	}

	public String getPassW() {
		return passW;
	}

	public void setPassW(String passW) {
		this.passW = passW;
	}
    
    
}
