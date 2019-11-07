package utils;

import javax.persistence.Embeddable;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Address {
    //@NotNull
    @Size(max = 100)
    private String addressLine1;

    //@NotNull
    @Size(max = 100)
    private String addressLine2;

   //@NotNull
    @Size(max = 100)
    private String city;

    //@NotNull
    @Size(max = 100)
    private String state;

    //@NotNull
    @Size(max = 100)
    private String country;

    //@NotNull
    @Size(max = 6)
    //@Pattern(regexp="^[0-9]+$", message="Wrong zip!") //^pour dire le debut de chaine,$ pour dire la fin de chaine
    private String zipCode;

	private double lat;
	
	private double lng;
    
	private String markerColor;
    
    public Address() {

    }

    public Address(String addressLine1, String addressLine2, String city, 
                   String state, String country, String zipCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }
    
    
    

	public Address(String addressLine1, String addressLine2, String city, String state, String country, String zipCode,
			double lat, double lng) {
		super();
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
		this.lat = lat;
		this.lng = lng;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	


	public String getMarkerColor() {
		return markerColor;
	}

	public void setMarkerColor(String markerColor) {
		this.markerColor = markerColor;
	}

	@Override
	public String toString() {
		return "Address [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", zipCode=" + zipCode + ", lat=" + lat + ", lng=" + lng
				+ "]";
	}

//	@Override
//	public String toString() {
//		return addressLine1 + "," + addressLine2 + "," + city
//				+ ", " + state + "," + country + "," + zipCode;
//	}

	
    
    

}
