package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Critere
 *
 */
@Entity
@Table(name = "t_critere")
public class Critere implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCritere;
	private String DescrCritere;
	private int Pourcentage;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Administrator admin;
	
	public Critere() {
		super();
	}   
	public int getIdCritere() {
		return this.idCritere;
	}

	public void setIdCritere(int idCritere) {
		this.idCritere = idCritere;
	}   
	public String getDescrCritere() {
		return this.DescrCritere;
	}

	public void setDescrCritere(String DescrCritere) {
		this.DescrCritere = DescrCritere;
	}   
	public int getPourcentage() {
		return this.Pourcentage;
	}

	public void setPourcentage(int Pourcentage) {
		this.Pourcentage = Pourcentage;
	}
   
}

