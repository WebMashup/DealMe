package me.deal.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Location implements Serializable {
	
	/* public enum State {
		AK, AL, AR, AZ, CA, CO, CT, DC, DE, FL,
		GA, HI, IA, ID, IL, IN, KS, KY, LA, MA,
		MD, ME, MI, MN, MO, MS, MT, NC, ND, NE,
		NH, NJ, NM, NV, NY, OH, OK, OR, PA, RI,
		SC, SD, TN, TX, UT, VA, VT, WA, WI, WV, WY
	} */
	
	// Main street address
	private String address;
	private String city;
	// Two letter abbreviation
	private String state;
	private String zipCode;
	
	public Location() {
	}
	
	public Location(String address, String city, String state, String zipCode) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
