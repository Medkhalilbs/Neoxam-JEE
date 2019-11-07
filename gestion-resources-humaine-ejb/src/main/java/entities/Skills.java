package entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Skills
 *
 */
@Entity

public class Skills implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String Type;
	private String Name;
	private String Level;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Cv cv6;
	
	
	public Cv getCv(){
		return cv6;
	}
	
	public void setCv(Cv cv)
	{
		this.cv6 = cv;
	}

	public Skills() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getType() {
		return this.Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}   
	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}   
	public String getLevel() {
		return this.Level;
	}

	public void setLevel(String Level) {
		this.Level = Level;
	}
	@Override
	public String toString() {
		return "Skills [id=" + id + ", Type=" + Type + ", Name=" + Name + ", Level=" + Level + "]";
	}
	public Skills(int id, String type, String name, String level) {
		super();
		this.id = id;
		this.Type = type;
		this.Name = name;
		this.Level = level;
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
		Skills other = (Skills) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
   
}
