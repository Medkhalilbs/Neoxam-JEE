package entities;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import utils.PasswordUtils;
import utils.Status;


/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name = "users",uniqueConstraints={@UniqueConstraint(columnNames={"login","email"})})
@NamedQueries({
    @NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u ORDER BY u.email DESC"),
    @NamedQuery(name = User.FIND_BY_LOGIN_PASSWORD, query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password"),
    @NamedQuery(name = User.COUNT_ALL, query = "SELECT COUNT(u) FROM User u")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
@DiscriminatorValue(value="user")
@DiscriminatorColumn(name="role")
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) + 	@GeneratedValue =table nommé comme dans @Table pour chaque class , les champ de la classe mere= (id,email,login,password,registration_date,role,status),les champ de la classe fille=(id,email,login,password,registration_date,role,status,house_number,street,city,country,state,zipCode,first_name,last_name,phone_number) et on a une table nomé hibernate_sequence contien 1 colonne next_val qui se cree automatiquement
//@Inheritance(strategy=InheritanceType.JOINED) + 	@GeneratedValue=table nommé comme dans @Table pour chaque class , les champ de la classe mere=(id,email,login,password,registration_date,role,status),les champ de la classe fille=(house_number,street,city,country,state,zipCode,first_name,last_name,phone_number,id)et on a une table nomé hibernate_sequence contien 1 colonne next_val qui se cree automatiquement
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Message.class)
public class User implements Serializable {

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String FIND_ALL = "User.findAll";
    public static final String COUNT_ALL = "User.countAll";
    public static final String FIND_BY_LOGIN_PASSWORD = "User.findByLoginAndPassword";

    // ======================================
    // =             Attributes             =
    // ======================================
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@GeneratedValue

	protected int id;
	
    @NotNull
    @Email
    //@Column(unique = true)
    //@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."+"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"+"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",message="{invalid.email}")
    protected  String email;
    
    @NotNull
    //@Column(unique = true)
    protected  String login;
    protected  String password;
    
    //@Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$",message="{invalid.phonenumber}")
    protected String phone_number;
    @Temporal(TemporalType.TIMESTAMP)
    protected  Date registration_date;
	
//  @Enumerated(EnumType.ORDINAL) cad dans la bd stock l'index du type de l'enumeration comme entier (int)
    @Enumerated(EnumType.STRING)
    protected Status status;
    
//    @JsonManagedReference
    @JsonIgnore
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE},fetch =FetchType.EAGER)
	private Set<User_log> listeUser_logs;
	
//	 @JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE},fetch =FetchType.EAGER)
	private Set<Message> listeMessage;


	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}   
	

	
	// ======================================
    // =         Lifecycle methods          =
    // ======================================

    @PrePersist
    private void setUUID() {
        password = PasswordUtils.digestPassword(password);
    }
	
	
	public User(String email, String login, String password, Date registration_date) {
		super();
		this.email = email;
		this.login = login;
		this.password = password;
		this.registration_date = registration_date;
	}
	
	





	public User(String email, String login, String password, Date registration_date, Status status) {
		super();
		this.email = email;
		this.login = login;
		this.password = password;
		this.registration_date = registration_date;
		this.status = status;
	}

	public User(String email, String login, String password, Date registration_date, Status status, String phone_number) {
		super();
		this.email = email;
		this.login = login;
		this.password = password;
		this.registration_date = registration_date;
		this.status = status;
		this.phone_number=phone_number;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public Date getRegistration_date() {
		return this.registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}





	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", login=" + login + ", password=" + password
				+ ", registration_date=" + registration_date + ", status=" + status + "]";
	}


	public Set<User_log> getSeteUser_logs() {
		return listeUser_logs;
	}


	public void setSeteUser_logs(Set<User_log> listeUser_logs) {
		this.listeUser_logs = listeUser_logs;
	}


	public Set<Message> getSeteMessage() {
		return listeMessage;
	}


	public void setSeteMessage(Set<Message> listeMessage) {
		this.listeMessage = listeMessage;
	}


	public String getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@JsonProperty()
	public String getRole() {
	    return this.getClass().getSimpleName();
	}
	
   
}
