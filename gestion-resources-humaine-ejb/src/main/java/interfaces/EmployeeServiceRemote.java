package interfaces;

import java.util.List;

import entities.Employee;

public interface EmployeeServiceRemote {
	
	 public boolean ajouterEmployeandstatus(Employee employee);
	 public void deleteEmployee(int Cin);
	 public List<Employee> getAllEmployees();

}
