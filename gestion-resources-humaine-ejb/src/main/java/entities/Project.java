package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Project
 *
 */
@Entity

public class Project implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String Topic;
	private int Duration;
	private Date dateProject;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Cv cv5;
	
	
	public Cv getCv(){
		return cv5;
	}
	
	public void setCv(Cv cv)
	{
		this.cv5 = cv;
	}

	public Project() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}   
	public String getTopic() {
		return this.Topic;
	}

	public void setTopic(String Topic) {
		this.Topic = Topic;
	}   
	public int getDuration() {
		return this.Duration;
	}

	public void setDuration(int Duration) {
		this.Duration = Duration;
	}   
	public Date getDateProject() {
		return this.dateProject;
	}

	public void setDateProject(Date dateProject) {
		this.dateProject = dateProject;
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", Topic=" + Topic + ", Duration=" + Duration
				+ ", dateProject=" + dateProject + "]";
	}
	
	public Project(int id, String title, String topic, int duration, Date dateProject) {
		super();
		this.id = id;
		this.title = title;
		this.Topic = topic;
		this.Duration = duration;
		this.dateProject = dateProject;
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
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
   
}
