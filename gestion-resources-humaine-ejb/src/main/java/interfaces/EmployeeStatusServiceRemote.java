package interfaces;

import java.util.List;

import entities.EmployeeStatus;

public interface EmployeeStatusServiceRemote {
	public void updateEmployeeStatus(EmployeeStatus es);
	public EmployeeStatus getEmpStatusbyCin(int empcin);
	public List<EmployeeStatus> getAllEmployeesStatus();
}
