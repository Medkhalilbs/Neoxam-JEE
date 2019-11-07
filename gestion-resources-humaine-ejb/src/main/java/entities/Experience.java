package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

import utils.Address;

/**
 * Entity implementation class for Entity: Experience
 *
 */
@Entity

public class Experience implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Date Start_Date;
	private Date End_Date;   
	private int Duration;
	private Address Company_Adress;
	private String Company_Name;
	private String Sector;
	private String Department;
	private String Role;
	private String Subject;
	
	@ManyToOne
	private Cv cv1;
	
	
	public Cv getCv(){
		return cv1;
	}
	
	public void setCv(Cv cv)
	{
		this.cv1 = cv;
	}
	
	
	private static final long serialVersionUID = 1L;

	public Experience() {
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
		return this.Duration;
	}

	public void setDuration(int Duration) {
		this.Duration = Duration;
	}   
	public String getCompany_Name() {
		return this.Company_Name;
	}

	public void setCompany_Name(String Company_Name) {
		this.Company_Name = Company_Name;
	}   
	public String getSector() {
		return this.Sector;
	}

	public void setSector(String Sector) {
		this.Sector = Sector;
	}   
	public String getDepartment() {
		return this.Department;
	}

	public void setDepartment(String Department) {
		this.Department = Department;
	}   
	public String getRole() {
		return this.Role;
	}

	public void setRole(String Role) {
		this.Role = Role;
	}   
	public String getSubject() {
		return this.Subject;
	}

	public void setSubject(String Subject) {
		this.Subject = Subject;
	}
	public Address getCompany_Adress() {
		return Company_Adress;
	}
	public void setCompany_Adress(Address company_Adress) {
		Company_Adress = company_Adress;
	}
	@Override
	public String toString() {
		return "Experience [id=" + id + ", Start_Date=" + Start_Date + ", End_Date=" + End_Date + ", Duration="
				+ Duration + ", Company_Adress=" + Company_Adress + ", Company_Name=" + Company_Name + ", Sector="
				+ Sector + ", Department=" + Department + ", Role=" + Role + ", Subject=" + Subject + "]";
	}
	public Experience(int id, Date start_Date, Date end_Date, int duration, Address company_Adress, String company_Name,
			String sector, String department, String role, String subject) {
		super();
		this.id = id;
		this.Start_Date = start_Date;
		this.End_Date = end_Date;
		this.Duration = duration;
		this.Company_Adress = company_Adress;
		this.Company_Name = company_Name;
		this.Sector = sector;
		this.Department = department;
		this.Role = role;
		this.Subject = subject;
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
		Experience other = (Experience) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
   
}
