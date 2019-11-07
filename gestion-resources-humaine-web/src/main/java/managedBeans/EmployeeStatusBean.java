package managedBeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import entities.EmployeeStatus;
import services.EmployeeStatusService;

@ManagedBean
@SessionScoped
public class EmployeeStatusBean {
	
	private int Cin;
	private String employee_name;
	private String Department;
	private String Job;
	private int N1;
	private String Evalutaion;
	private String Carrer_interview;
	private String team_leader;
	private int employeetobeupadted;
	private List<EmployeeStatus> employesStatus;
@EJB
EmployeeStatusService employeeStautService;



public void update(EmployeeStatus es)
{
	
	this.setEmployee_name(es.getEmployee_name());
	this.setDepartment(es.getDepartment());
	this.setJob(es.getJob());
	this.setN1(es.getN1());
	this.setEvalutaion(es.getEvalutaion());
	this.setCarrer_interview(es.getCarrer_interview());
	this.setTeam_leader(es.getteam_leader());
	this.setEmployeetobeupadted(es.getCin());
	System.out.println(employeetobeupadted);
	
}


public EmployeeStatus getempstat(int empcin)
{  

return  employeeStautService.getEmpStatusbyCin(empcin);

}

public List<EmployeeStatus> getEmployesStatus() {
	employesStatus = employeeStautService.getAllEmployeesStatus();
	return employesStatus;
}
public void setEmployesStatus(List<EmployeeStatus> employesStatus) {
	this.employesStatus = employesStatus;
}
public int getEmployeetobeupadted() {
	return employeetobeupadted;
}



public void setEmployeetobeupadted(int employeetobeupadted) {
	this.employeetobeupadted = employeetobeupadted;
}

public void findEmpStatByDep()
{
	

}

public String upadateemployeestatus()
{
	EmployeeStatus es = new EmployeeStatus(employee_name,Department,Job,N1,Evalutaion,Carrer_interview,team_leader,employeetobeupadted);
	
    employeeStautService.updateEmployeeStatus(es);
    return "/pages/Skima/listemployee?faces-redirect=true";
}

public String upadateemployeestatus2()
{
	
employeeStautService.updateEmployeeStatus(new EmployeeStatus(employee_name,Department,Job,N1,Evalutaion,Carrer_interview,team_leader,employeetobeupadted));
return "/pages/Skima/listemployeestatus?faces-redirect=true";
}




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
public String getTeam_leader() {
	return team_leader;
}
public void setTeam_leader(String team_leader) {
	this.team_leader = team_leader;
}
public EmployeeStatusService getEmployeeStautService() {
	return employeeStautService;
}
public void setEmployeeStautService(EmployeeStatusService employeeStautService) {
	this.employeeStautService = employeeStautService;
} 



}
