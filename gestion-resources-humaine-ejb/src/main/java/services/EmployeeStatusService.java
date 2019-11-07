package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Employee;
import entities.EmployeeStatus;
import interfaces.EmployeeStatusServiceRemote;


@Stateless
@LocalBean
public class EmployeeStatusService implements EmployeeStatusServiceRemote{

	@PersistenceContext(unitName = "gestion-resources-humaine-ejb")
	public EntityManager em;

	public EmployeeStatusService() {
		super();
	}
	
	@Override
	public void updateEmployeeStatus(EmployeeStatus es)
    {
    	em.merge(es);
    }
	@Override
	public EmployeeStatus getEmpStatusbyCin(int empcin) {
		return em.find(EmployeeStatus.class, empcin);
	}
	
	
	public List<EmployeeStatus> getAllEmployeesStatus()

    {
    	TypedQuery<EmployeeStatus> query= em.createQuery("SELECT c FROM EmployeeStatus c", EmployeeStatus.class);
    	List<EmployeeStatus> results=query.getResultList();
    	return results;
    }

}
