package entities;

import entities.User;
import utils.Address;
import utils.Status;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Administrator
 *
 */
@Entity
//@Table(name = "admins")
@DiscriminatorValue(value="admin")
public class Administrator extends User implements Serializable {

	//@NotNull
	//(min=4, max=50)
	private String first_name;
	private String last_name;
	
	private String cin;
	private String picture;
	
//    //@Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",message="{invalid.phonenumber}")
//	private String phone_number;
	
    @Embedded
    @AttributeOverrides(value = {
        @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
        @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
    })
    private Address address;
	private static final long serialVersionUID = 1L;

	public Administrator() {
		super();
	}   
	
	
	
	public Administrator(String email,String login,String password,Date date_creation,Status status,String phone_Number, String first_name, String last_name, String cin,Address address,String picture) {
		super();
		this.email = email;
		this.login = login;
		this.password = password;
		this.registration_date = date_creation;
		this.status = status;
		this.phone_number=phone_Number;
		this.first_name = first_name;
		this.last_name = last_name;
		this.cin = cin;
		this.address=address;
		this.picture=picture;
		
	}
	
	public Administrator(String first_name, String last_name, String cin) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.cin = cin;
		
	}



	public Administrator(String first_name, String last_name, String cin, Address address) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.cin = cin;
		this.address = address;
	}



	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}   
	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}   
   
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
	
	public String getCin() {
		return cin;
	}



	public void setCin(String cin) {
		this.cin = cin;
	}

	
	


	public String getPicture() {
		return picture;
	}



	public void setPicture(String picture) {
		this.picture = picture;
	}



	@Override
	public String toString() {
		return "Administrator [id="+id+"first_name=" + first_name + ", last_name=" + last_name + ", cin=" + cin
				+ ", address=" + address + "]";
	}

	
	
	
   
}
