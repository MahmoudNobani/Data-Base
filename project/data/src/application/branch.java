package application;

public class branch {
	
	private int ID;
    private String Address;
    private String Line;
    
    public branch(int ID, String Address, String line) {
    	this.Address=Address;
    	this.ID=ID;
    	this.Line=line;
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getLine() {
		return Line;
	}

	public void setLine(String line) {
		Line = line;
	}
    

}
