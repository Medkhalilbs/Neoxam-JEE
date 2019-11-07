package entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="idtopic",referencedColumnName="id")
	private Topic topic;
	
	
	@ManyToOne
	@JoinColumn(name="idcandidate",referencedColumnName="id")
	private Candidate candidate;
	
	
	private Date date;
	private double score;
	private String statut;
	private String result;
//	@Column(name="reponse_question_condidat")
//	@ElementCollection(fetch =FetchType.EAGER)
//	private List<Answer> reponsecandidat =new ArrayList<Answer>();
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	

//		public List<Answer> getReponsecandidat() {
//		return reponsecandidat;
//	}
//	public void setReponsecandidat(List<Answer> reponsecandidat) {
//		this.reponsecandidat = reponsecandidat;
//	}


	public Result() {
		super();
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Result [id=" + id + ", topic=" + topic + ", candidate=" + candidate + ", date=" + date + ", score="
				+ score + ", statut=" + statut + ", result=" + result + "]";
	}
	
	
	
	

}
