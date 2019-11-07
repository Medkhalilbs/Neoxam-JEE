package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity implementation class for Entity: User_log
 *
 */
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Message.class)
public class User_log implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @GeneratedValue
	private int id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date login_timestamp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logout_timestamp;
	private String pc_id;
	private String session_id;
	private String ip_address;
	private String country;
	
//	@JsonBackReference
	@ManyToOne
	private User user;
	private static final long serialVersionUID = 1L;

	public User_log() {
		super();
	}

	public User_log(Date login_timestamp, Date logout_timestamp, String pc_id, String session_id, String ip_address,
			User user) {
		super();
		this.login_timestamp = login_timestamp;
		this.logout_timestamp = logout_timestamp;
		this.pc_id = pc_id;
		this.session_id = session_id;
		this.ip_address = ip_address;
		this.user = user;
	}

	public User_log(Date login_timestamp, String pc_id, String session_id, String ip_address, User user) {
		super();
		this.login_timestamp = login_timestamp;
		this.pc_id = pc_id;
		this.session_id = session_id;
		this.ip_address = ip_address;
		this.user = user;
	}
	
	
	public User_log(Date login_timestamp, String pc_id, String session_id, String ip_address, User user,String country) {
		super();
		this.country=country;
		this.login_timestamp = login_timestamp;
		this.pc_id = pc_id;
		this.session_id = session_id;
		this.ip_address = ip_address;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLogin_timestamp() {
		return login_timestamp;
	}

	public void setLogin_timestamp(Date login_timestamp) {
		this.login_timestamp = login_timestamp;
	}

	public Date getLogout_timestamp() {
		return logout_timestamp;
	}

	public void setLogout_timestamp(Date logout_timestamp) {
		this.logout_timestamp = logout_timestamp;
	}

	public String getPc_id() {
		return pc_id;
	}

	public void setPc_id(String pc_id) {
		this.pc_id = pc_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	
	
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	@Override
	public String toString() {
		return "User_log [id=" + id + ", login_timestamp=" + login_timestamp + ", logout_timestamp=" + logout_timestamp
				+ ", pc_id=" + pc_id + ", session_id=" + session_id + ", ip_address=" + ip_address + ", user=" + user
				+ "]";
	}

}
