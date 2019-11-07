package entities;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import utils.PasswordUtils;
import utils.Status;


/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name = "messages")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) + 	@GeneratedValue =table nommé comme dans @Table pour chaque class , les champ de la classe mere= (id,email,login,password,registration_date,role,status),les champ de la classe fille=(id,email,login,password,registration_date,role,status,house_number,street,city,country,state,zipCode,first_name,last_name,phone_number) et on a une table nomé hibernate_sequence contien 1 colonne next_val qui se cree automatiquement
//@Inheritance(strategy=InheritanceType.JOINED) + 	@GeneratedValue=table nommé comme dans @Table pour chaque class , les champ de la classe mere=(id,email,login,password,registration_date,role,status),les champ de la classe fille=(house_number,street,city,country,state,zipCode,first_name,last_name,phone_number,id)et on a une table nomé hibernate_sequence contien 1 colonne next_val qui se cree automatiquement
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Message.class)

public class Message implements Serializable {


 @JsonView(Views.Internal.class)
 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String sujet;
	private String destinataire;
    private String contenu;
    private String statusMessage;
    private String visibiliteMessage;
    @Temporal(TemporalType.TIMESTAMP)
    private  Date dateEnvoie;
    @Temporal(TemporalType.TIMESTAMP)
    private  Date dateLecture;
    
//    @JsonBackReference
    @ManyToOne
    private User user;
	
	private static final long serialVersionUID = 1L;

	public Message() {
		super();
	}

	public Message(String sujet, String destinataire, String contenu, String statusMessage, String visibiliteMessage,
			Date dateEnvoie, Date dateLecture, User user) {
		super();
		this.sujet = sujet;
		this.destinataire = destinataire;
		this.contenu = contenu;
		this.statusMessage = statusMessage;
		this.visibiliteMessage = visibiliteMessage;
		this.dateEnvoie = dateEnvoie;
		this.dateLecture = dateLecture;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getVisibiliteMessage() {
		return visibiliteMessage;
	}

	public void setVisibiliteMessage(String visibiliteMessage) {
		this.visibiliteMessage = visibiliteMessage;
	}

	public Date getDateEnvoie() {
		return dateEnvoie;
	}

	public void setDateEnvoie(Date dateEnvoie) {
		this.dateEnvoie = dateEnvoie;
	}

	public Date getDateLecture() {
		return dateLecture;
	}

	public void setDateLecture(Date dateLecture) {
		this.dateLecture = dateLecture;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", sujet=" + sujet + ", destinataire=" + destinataire + ", contenu=" + contenu
				+ ", statusMessage=" + statusMessage + ", visibiliteMessage=" + visibiliteMessage + ", dateEnvoie="
				+ dateEnvoie + ", dateLecture=" + dateLecture + ", user=" + user + "]";
	}   
	
	
	


	
	
   
}
