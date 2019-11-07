package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class EmployeeStatus implements Serializable {
	@Id
	private int Cin;
	private String employee_name;
	private String Department;
	private String Job;
	private int N1;
	private String Evalutaion;
	private String Carrer_interview;
	private String team_leader;
	
	public int getCin() {
		return Cin;
	}
	public void setCin(int cin) {
		Cin = cin;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getJob() {
		return Job;
	}
	public void setJob(String job) {
		Job = job;
	}
	public int getN1() {
		return N1;
	}
	public void setN1(int n1) {
		N1 = n1;
	}
	public String getEvalutaion() {
		return Evalutaion;
	}
	public void setEvalutaion(String evalutaion) {
		Evalutaion = evalutaion;
	}
	public String getCarrer_interview() {
		return Carrer_interview;
	}
	public void setCarrer_interview(String carrer_interview) {
		Carrer_interview = carrer_interview;
	}
	public String getteam_leader() {
		return team_leader;
	}
	public void setteam_leader(String team_leader) {
		this.team_leader = team_leader;
	}
	@Override
	public String toString() {
		return "EmployeeStatus [Cin=" + Cin + ", employee_name=" + employee_name + ", Department=" + Department
				+ ", Job=" + Job + ", N1=" + N1 + ", Evalutaion=" + Evalutaion + ", Carrer_interview="
				+ Carrer_interview + ", team_leader=" + team_leader + "]";
	}
	
	public EmployeeStatus(String employee_name, String department, String job, int n1, String evalutaion,
			String carrer_interview, String team_leader, int employeeStautService) {
		super();
		this.employee_name = employee_name;
		Department = department;
		Job = job;
		N1 = n1;
		Evalutaion = evalutaion;
		Carrer_interview = carrer_interview;
		this.team_leader = team_leader;
		this.Cin = employeeStautService;
	}
	public EmployeeStatus() {
		super();
	}
	
	
	

}
