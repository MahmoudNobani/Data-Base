package application;

public class Inv {
	
	private int dvd_id;
	private String title;
	private String purchase_price; 
	private String drental_price;
	private String type1;
	private String p_year;
	private String rating;
	private String branch_id;

	public Inv(int dvd_id, String title, String purchase_price, String drental_price, String type1, String p_year,String rating,String s) {
		this.dvd_id = dvd_id;
		this.title = title;
		this.purchase_price = purchase_price;
		this.drental_price = drental_price;
		this.type1 = type1;
		this.p_year = p_year;
		this.rating = rating;
		this.branch_id=s;
	}

	public int getDvd_id() {
		return dvd_id;
	}

	public void setDvd_id(int dvd_id) {
		this.dvd_id = dvd_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(String purchase_price) {
		this.purchase_price = purchase_price;
	}

	public String getDrental_price() {
		return drental_price;
	}

	public void setDrental_price(String drental_price) {
		this.drental_price = drental_price;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getP_year() {
		return p_year;
	}

	public void setP_year(String p_year) {
		this.p_year = p_year;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	
	

}