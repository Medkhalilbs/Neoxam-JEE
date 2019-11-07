package entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: language
 *
 */
@Entity

public class language implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String Lang;
	private String Level;
	private String Type;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Cv cv4;
	
	
	public Cv getCv(){
		return cv4;
	}
	
	public void setCv(Cv cv)
	{
		this.cv4 = cv;
	}

	public language() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getLang() {
		return this.Lang;
	}

	public void setLang(String Lang) {
		this.Lang = Lang;
	}   
	public String getLevel() {
		return this.Level;
	}

	public void setLevel(String Level) {
		this.Level = Level;
	}   
	public String getType() {
		return this.Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
	@Override
	public String toString() {
		return "language [id=" + id + ", Lang=" + Lang + ", Level=" + Level + ", Type=" + Type + "]";
	}
	public language(int id, String lang, String level, String type) {
		super();
		this.id = id;
		this.Lang = lang;
		this.Level = level;
		this.Type = type;
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
		language other = (language) obj;
		if (id != other.id)
			return false;
		return true;
	}
   
	
}
