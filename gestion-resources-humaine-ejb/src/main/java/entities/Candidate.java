package entities;

import entities.User;
import utils.Address;
import utils.Status;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity implementation class for Entity: Candidate
 *
 */

@Entity
public class Candidate extends User implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@OneToMany(mappedBy="candidate",cascade={CascadeType.ALL},fetch =FetchType.EAGER)
	private Set<Result> result;
	@JsonIgnore
	@OneToMany(mappedBy="candidate",cascade={CascadeType.ALL},fetch =FetchType.EAGER)
	private Set<Result> reclamation;
	
	private String first_name;
	private String last_name;
	private String Birthdate;
	private int ProfilValide;
	private String SocialState;
	private String DriverLience;
	private int Age;
	private String Description;
	  @Embedded
	    @AttributeOverrides(value = {
	        @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
	        @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
	    })
	    private Address address;
	  
	@JsonIgnore
	@OneToMany(mappedBy="Candidates", cascade = {CascadeType.ALL})
	private Set<Cv> Cvs;
	  
	
	public Set<Cv> getCvs() {
		return Cvs;
	}


	public void setCvs(Set<Cv> cvs) {
		Cvs = cvs;
	}


	public Candidate() {
		super();
	}


	public String getFirst_name() {
		return first_name;
	}
	
	public String getBirthdate() {
		return Birthdate;
	}


	public void setBirthdate(String birthdate) {
		Birthdate = birthdate;
	}



	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}




	public int isProfileValide() {
		return ProfilValide;
	}


	public void setProfileValide(int profileValide) {
		ProfilValide = profileValide;
	}


	public String getSocialState() {
		return SocialState;
	}


	public void setSocialState(String socialState) {
		SocialState = socialState;
	}


	public String getDriverLience() {
		return DriverLience;
	}


	public void setDriverLience(String driverLience) {
		DriverLience = driverLience;
	}


	public int getAge() {
		return Age;
	}


	public void setAge(int age) {
		Age = age;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Candidate [first_name=" + first_name + ", last_name=" + last_name + ", Age=" + Age + ", Description=" + Description +", Date de naissance = "+ Birthdate + ", address=" + address + "]";
	}


	
	
	
	public Candidate(String first_name, String last_name,  String socialState, String Birthdate, String driverLience,
			int age, String description, Address address) {
		super();
		this.Birthdate= Birthdate;
		this.first_name = first_name;
		this.last_name = last_name;
		this.SocialState = socialState;
		this.DriverLience = driverLience;
		this.Age = age;
		this.Description = description;

	}


	public Candidate(String first_name, String last_name,  int profileValide,
			String socialState,String Birthdate ,String driverLience, int age, String description, Address address) {
		super();
		this.Birthdate= Birthdate;
		this.first_name = first_name;
		this.last_name = last_name;
		this.ProfilValide = profileValide;
		this.SocialState = socialState;
		this.DriverLience = driverLience;
		this.Age = age;
		this.Description = description;
		this.address = address;
	}


	public Candidate(String email, String login, String password, Date registration_date, Status status) {
		super(email, login, password, registration_date, status);
		// TODO Auto-generated constructor stub
	}


	public Candidate(String email, String login, String password, Date registration_date) {
		super(email, login, password, registration_date);
		// TODO Auto-generated constructor stub
	}


	public Set<Result> getResult() {
		return result;
	}


	public void setResult(Set<Result> result) {
		this.result = result;
	}


	public Set<Result> getReclamation() {
		return reclamation;
	}


	public void setReclamation(Set<Result> reclamation) {
		this.reclamation = reclamation;
	}


	
   
}
