package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

public class Reclamation  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Topic  topic ;
	@ManyToOne
	private Candidate  candidate ;
	private String etat;
	private String description;
	private Date date_creation;
	private Date date_traitement;
	private String image;
	private String objet;
	@Override
	public String toString() {
		return "Reclamation [id=" + id + ", topic=" + topic + ", candidate=" + candidate + ", etat=" + etat
				+ ", description=" + description + ", date_creation=" + date_creation + ", date_traitement="
				+ date_traitement + ", image=" + image + ", objet=" + objet + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate_creation() {
		return date_creation;
	}
	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}
	public Date getDate_traitement() {
		return date_traitement;
	}
	public void setDate_traitement(Date date_traitement) {
		this.date_traitement = date_traitement;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	public Reclamation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
