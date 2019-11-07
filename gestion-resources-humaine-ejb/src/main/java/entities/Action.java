package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Null;

/**
 * Entity implementation class for Entity: Action
 *
 */
@Entity
@Table(name = "t_action")
public class Action implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAction;
	private String description;
	private TypeAction type;
	private double pourcentage;
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne
	private Administrator admin;
	
	@Override
	public String toString() {
		return "Action [idAction=" + idAction + ", description=" + description + ", type=" + type.name() ;
	}
	public Action() {
		super();
	}   
	public int getIdAction() {
		return this.idAction;
	}

	public void setIdAction(int idAction) {
		this.idAction = idAction;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Enumerated(EnumType.STRING)
	public TypeAction getType() {
		return type;
	}

	public void setType(TypeAction type) {
		this.type = type;
	}
	public double getPourcentage() {
		return pourcentage;
	}
	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
	}
	
   
}
