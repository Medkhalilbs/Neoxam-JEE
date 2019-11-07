package managedBeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import services.EmployeeStatusService;

@ManagedBean
@SessionScoped
public class EvalutaionTestBean {
	
	
	private String Department;
	private String Deadline;
    private String Title;
	
	@EJB
	EmployeeStatusService employeeStautService;
	
	
	

}
