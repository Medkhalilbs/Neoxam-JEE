package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Employee;
import entities.EmployeeStatus;
import interfaces.EmployeeServiceRemote;


@Stateless
@LocalBean
public class EmployeeService implements EmployeeServiceRemote {
	
	@PersistenceContext(unitName = "gestion-resources-humaine-ejb")
	public EntityManager em;

	public EmployeeService() {
		super();
	}
	
	public boolean ajouterEmployeandstatus(Employee employee) {
		try
		{
			EmployeeStatus es = new EmployeeStatus();
			es.setCin(employee.getCin());
		
			em.persist(employee);
			em.persist(es);

			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		

	}
	
    public List<Employee> getAllEmployees()
    {
    	TypedQuery<Employee> query= em.createQuery("SELECT c FROM Employee c", Employee.class);
    	List<Employee> results=query.getResultList();
    	return results;
    }
    
    public void deleteEmployee(int Cin)
    {
    	Employee employee = em.find(Employee.class, Cin);
    	EmployeeStatus employeestatus = em.find(EmployeeStatus.class, Cin);
	    em.remove(employee);
		em.remove(employeestatus);
    }
    


}
