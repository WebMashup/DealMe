package me.deal.shared;

public class Location {
	
	public enum State {
		AK, AL, AR, AZ, CA, CO, CT, DC, DE, FL,
		GA, HI, IA, ID, IL, IN, KS, KY, LA, MA,
		MD, ME, MI, MN, MO, MS, MT, NC, ND, NE,
		NH, NJ, NM, NV, NY, OH, OK, OR, PA, RI,
		SC, SD, TN, TX, UT, VA, VT, WA, WI, WV, WY
	}
	
	// Main street address
	private String street1;
	 // Apt., Ste., etc..
	private String street2;
	private String city;
	// Two letter abbreviation
	private State state;
	private String zipCode;
	
	public Location() {
	}
	
	public Location(String street1, String street2,
			String city, State state, String zipCode) {
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	public String getStreet1() {
		return this.street1;
	}
	
	public String getStreet2() {
		return this.street2;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public State getState() {
		return this.state;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
