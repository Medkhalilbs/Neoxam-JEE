package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Employee implements Serializable {
	
	@Id
	private int Cin;
	@Temporal(TemporalType.DATE)
	private Date Recrutment_date;
	private int childrens_nbr;
	@Enumerated(EnumType.STRING)
	private Role position_held;
	private int Salary;
	private int bonus_pts;
	private int nbr_holidays;
	private int nbr_late;
	private String mail;
	
	public Employee()
	{
		
	}
	public Employee(int cin, Date recrutment_date, int childrens_nbr, Role position_held, int salary, int bonus_pts,
			int nbr_holidays, int nbr_late, String mail) {
		super();
		Cin = cin;
		Recrutment_date = recrutment_date;
		this.childrens_nbr = childrens_nbr;
		this.position_held = position_held;
		Salary = salary;
		this.bonus_pts = bonus_pts;
		this.nbr_holidays = nbr_holidays;
		this.nbr_late = nbr_late;
		this.mail = mail;
	}
	public int getCin() {
		return Cin;
	}
	public void setCin(int cin) {
		Cin = cin;
	}
	public Date getRecrutment_date() {
		return Recrutment_date;
	}
	public void setRecrutment_date(Date recrutment_date) {
		Recrutment_date = recrutment_date;
	}
	public int getChildrens_nbr() {
		return childrens_nbr;
	}
	public void setChildrens_nbr(int childrens_nbr) {
		this.childrens_nbr = childrens_nbr;
	}
	public Role getPosition_held() {
		return position_held;
	}
	public void setPosition_held(Role position_held) {
		this.position_held = position_held;
	}
	public int getSalary() {
		return Salary;
	}
	public void setSalary(int salary) {
		Salary = salary;
	}
	public int getBonus_pts() {
		return bonus_pts;
	}
	public void setBonus_pts(int bonus_pts) {
		this.bonus_pts = bonus_pts;
	}
	public int getNbr_holidays() {
		return nbr_holidays;
	}
	public void setNbr_holidays(int nbr_holidays) {
		this.nbr_holidays = nbr_holidays;
	}
	public int getNbr_late() {
		return nbr_late;
	}
	public void setNbr_late(int nbr_late) {
		this.nbr_late = nbr_late;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "Employee [Cin=" + Cin + ", Recrutment_date=" + Recrutment_date + ", childrens_nbr=" + childrens_nbr
				+ ", position_held=" + position_held + ", Salary=" + Salary + ", bonus_pts=" + bonus_pts
				+ ", nbr_holidays=" + nbr_holidays + ", nbr_late=" + nbr_late + ", mail=" + mail + "]";
	}
	
	

	

}
