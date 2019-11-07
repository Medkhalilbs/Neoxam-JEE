package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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


public class Question implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String enonce;
	private String reponsejuste;
	private String complexity;
	private double point;
//	@Column(name="arguments")
//	@ElementCollection(fetch =FetchType.EAGER)
//	private List<String> listereponse=new ArrayList<String>();
	//@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="question",cascade = CascadeType.ALL)
	private List<Answer> listereponse;
	
	private String type;
	private String indice; 
	@ManyToOne
	private Topic topic ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEnonce() {
		return enonce;
	}
	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}
	public String getComplexity() {
		return complexity;
	}
	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double d) {
		this.point = d;
	}

//
//	public List<Answer> getListereponse() {
//		return listereponse;
//	}
//	public void setListereponse(List<Answer> listereponse) {
//		this.listereponse = listereponse;
//	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIndice() {
		return indice;
	}
	public void setIndice(String indice) {
		this.indice = indice;
	}

	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", enonce=" + enonce + ", complexity=" + complexity + ", point=" + point
				+ ", listereponse="  + ", type=" + type + ", indice=" + indice 
				+ ", topic=" + topic + "]";
	}
	

	public Question(int id, String enonce, String complexity, Float point, ArrayList<String> listereponse, String type,
			String indice, String etat, Topic topic) {
		super();
		this.id = id;
		this.enonce = enonce;
		this.complexity = complexity;
		this.point = point;
		
		this.type = type;
		this.indice = indice;
		this.topic = topic;
	}
	public Question() {
		super();
	}
	public List<Answer> getListereponse() {
		return listereponse;
	}
	public void setListereponse(List<Answer> listereponse) {
		this.listereponse = listereponse;
	}
	public Question(int id, String enonce)
	{
		super();
		this.id = id;
		this.enonce = enonce;
	}
	public Question( String enonce, String complexity , float point)
	{
		super();
		this.enonce = enonce;
		this.complexity = complexity;
		this.point = point;
	}
	public Question(int id, String complexity,String enonce)
	{
		super();
	
		this.complexity = complexity;
		this.enonce = enonce;
	}
//	
//	
	public String getReponsejuste() {
		return reponsejuste;
	}
	public void setReponsejuste(String reponsejuste) {
		this.reponsejuste = reponsejuste;
	}
	
	
}
