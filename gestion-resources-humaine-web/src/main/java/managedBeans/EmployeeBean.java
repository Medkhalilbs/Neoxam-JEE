package managedBeans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import entities.Employee;
import entities.Role;
import services.EmployeeService;

@ManagedBean
@SessionScoped
public class EmployeeBean {
	
	private int Cin;
	private Date Recrutment_date;
	private int childrens_nbr;
	private Role position_held;
	private int Salary;
	private int bonus_pts;
	private int nbr_holidays;
	private int nbr_late;
	private String mail;
	private List<Employee> employes;
	
	@EJB
	EmployeeService employeeService; 
	
	
   public String doAdd() 
	
	{ 
	   String navigateTo = null;
		
		 if ( employeeService.ajouterEmployeandstatus(new Employee(Cin,Recrutment_date,childrens_nbr,position_held,Salary,bonus_pts,nbr_holidays,nbr_late,mail)))
		 {
			 FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Ajout d´un nouveau employe effectuer avec succes")) ;
			 System.out.println("okitou");
			 navigateTo = "/pages/Skima/listemployee?faces-redirect=true"; 
		 }

	else {
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Echec de l'ajout")) ;
		 System.out.println("oh no");
			navigateTo = "/pages/Skima/AjoutEmployee?faces-redirect=true"; 
		}
		return navigateTo; 
	}
   
   
public String addview(){
	
	return "/pages/Skima/AjoutEmployee?faces-redirect=true";
}  

public String listview(){
	
	return "/pages/Skima/listemployeestatus?faces-redirect=true";
}  
public String listviewemp(){
	
	return "/pages/Skima/listemployee?faces-redirect=true";
}  
   
   

public List<Employee> getEmployes() {
	employes = employeeService.getAllEmployees();
	return employes;
}

public void deleteemployeeandstatus(int cin)
{
employeeService.deleteEmployee(cin);
} 

public void setEmployes(List<Employee> employes) {
	this.employes = employes;
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


public EmployeeService getEmployeeService() {
	return employeeService;
}


public void setEmployeeService(EmployeeService employeeService) {
	this.employeeService = employeeService;
}
	
}
