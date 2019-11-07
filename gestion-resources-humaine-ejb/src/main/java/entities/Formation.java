package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

import utils.Address;

/**
 * Entity implementation class for Entity: Formation
 *
 */
@Entity

public class Formation implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Date Start_Date;
	private Date End_Date;   
	private int duration;
	private Address adress_Formation;
	private String Certification;
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Cv cv2;
	
	
	public Cv getCv(){
		return cv2;
	}
	
	public void setCv(Cv cv)
	{
		this.cv2 = cv;
	}

	public Formation() {
		super();
	}   
	public Date getStart_Date() {
		return this.Start_Date;
	}

	public void setStart_Date(Date Start_Date) {
		this.Start_Date = Start_Date;
	}   
	public Date getEnd_Date() {
		return this.End_Date;
	}

	public void setEnd_Date(Date End_Date) {
		this.End_Date = End_Date;
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}   
	
	public Address getAdress_Formation() {
		return this.adress_Formation;
	}

	public void setAdress_Formation(Address adress_Formation) {
		this.adress_Formation = adress_Formation;
	}   
	public String getCertification() {
		return this.Certification;
	}

	public void setCertification(String Certification) {
		this.Certification = Certification;
	}
	@Override
	public String toString() {
		return "Formation [Start_Date=" + Start_Date + ", End_Date=" + End_Date + ", id=" + id + ", duration="
				+ duration + ", adress_Formation=" + adress_Formation + ", Certification=" + Certification + "]";
	}
	public Formation(Date start_Date, Date end_Date, int id, int duration, Address adress_Formation,
			String certification) {
		super();
		this.Start_Date = start_Date;
		this.End_Date = end_Date;
		this.id = id;
		this.duration = duration;
		this.adress_Formation = adress_Formation;
		this.Certification = certification;
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
		Formation other = (Formation) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
   
}
