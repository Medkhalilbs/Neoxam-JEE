package entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Hobies
 *
 */
@Entity

public class Hobies implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String Type;   
	private String Name;
	private String Level;
	private String Place;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Cv cv3;
	
	
	public Cv getCv(){
		return cv3;
	}
	
	public void setCv(Cv cv)
	{
		this.cv3 = cv;
	}

	public Hobies() {
		super();
	}   
	public String getType() {
		return this.Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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
	public String getPlace() {
		return this.Place;
	}

	public void setPlace(String Place) {
		this.Place = Place;
	}
	@Override
	public String toString() {
		return "Hobies [id=" + id + ", Type=" + Type + ", Name=" + Name + ", Level=" + Level + ", Place=" + Place + "]";
	}
	public Hobies(int id, String type, String name, String level, String place) {
		super();
		this.id = id;
		this.Type = type;
		this.Name = name;
		this.Level = level;
		this.Place = place;
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
		Hobies other = (Hobies) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
   
}
