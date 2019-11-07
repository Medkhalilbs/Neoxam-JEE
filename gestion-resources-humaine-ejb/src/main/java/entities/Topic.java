package entities;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity


public class Topic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@JsonIgnore
	@OneToMany(mappedBy="topic",cascade={CascadeType.REMOVE},fetch =FetchType.EAGER)
	private Set<Result> result;
	private String nom;
	private String image;
	private int dureeTopic;
	private int nombre_question;
	@ManyToOne
	private Domain domain ;
	
	@JsonIgnore
	@OneToMany(mappedBy="topic",fetch =FetchType.EAGER,cascade={CascadeType.REMOVE})
	private Set<Reclamation> listeReclamation ;
	
	@JsonIgnore
	@OneToMany(mappedBy="topic",cascade={CascadeType.REMOVE},fetch =FetchType.EAGER)
	private Set<Question> listequestion ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDureeTopic() {
		return dureeTopic;
	}
	public void setDureeTopic(int dureeTopic) {
		this.dureeTopic = dureeTopic;
	}
	public int getNombre_question() {
		return nombre_question;
	}
	public void setNombre_question(int nombre_question) {
		this.nombre_question = nombre_question;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	
	
public Set<Result> getResult() {
		return result;
	}
	public void setResult(Set<Result> result) {
		this.result = result;
	}
	public Set<Reclamation> getListeReclamation() {
		return listeReclamation;
	}
	public void setListeReclamation(Set<Reclamation> listeReclamation) {
		this.listeReclamation = listeReclamation;
	}
public Set<Question> getListequestion() {
		return listequestion;
	}
	public void setListequestion(Set<Question> listequestion) {
		this.listequestion = listequestion;
	}
	//	@Override
//	public String toString() {
//		return "Topic [id=" + id + ", dureeTopic=" + dureeTopic + ", nombre_question=" + nombre_question + ", domain="
//				+ domain + ", listequestion=" + listequestion + "]";
//	}
//	public Topic(int id, Time dureeTopic, int nombre_question, Domain domain, Set<Question> listequestion) {
//		super();
//		this.id = id;
//		this.dureeTopic = dureeTopic;
//		this.nombre_question = nombre_question;
//		this.domain = domain;
//		this.listequestion = listequestion;
//	}
	public Topic() {
		super();
		
	}
	public Topic(String nom2, int dureeTopic2, int nombre_question2,Domain d) {
		super();
		this.nom=nom2;
		this.dureeTopic=dureeTopic2;
		this.nombre_question=nombre_question2;
		this.domain=d;
	}
	

public Topic(String nom2, int dureeTopic2, int nombre_question2, Domain d1, String folder) {
	super();
	this.nom=nom2;
	this.dureeTopic=dureeTopic2;
	this.nombre_question=nombre_question2;
	this.domain=d1;
	this.image=folder;
	}
	//	public Topic(String nom2, int dureeTopic2, int nombre_question2, Domain d1, String uploadedFile) {
//		super();
//		this.nom=nom2;
//		this.dureeTopic=dureeTopic2;
//		this.nombre_question=nombre_question2;
//		this.domain=d1;
//		this.image=uploadedFile;
//		
//	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
	
	
	
	
	


}
