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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity


public class Domain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private TypeDomain type;
	@JsonIgnore
	@OneToMany(mappedBy="domain",fetch =FetchType.EAGER,cascade={CascadeType.REMOVE})

	private List<Topic> listeTopic ;
	
	public void AddTopic(Topic t){
	t.setDomain(this);
	this.listeTopic.add(t);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TypeDomain getType() {
		return type;
	}
	public void setType(TypeDomain type) {
		this.type = type;
	}
	
	
	public List<Topic> getListeTopic() {
		return listeTopic;
	}
	public void setListeTopic(ArrayList<Topic> listetopic2) {
		this.listeTopic = listetopic2;
	}

	@Override
	public String toString() {
		return  name ;
	}
	
	
	public Domain(int id, String name, TypeDomain type, List<Topic> listeTopic) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
//		this.listeTopic = listeTopic;
	}
	public Domain() {
		super();
	}
	public Domain(String name, TypeDomain type) {
		super();
		this.name = name;
		this.type = type;
	}
	

}
