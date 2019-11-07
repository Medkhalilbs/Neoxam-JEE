package entities;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class EvalutaionTest implements Serializable {

	@Id
	private String Department;
	private String Deadline;
    private String Title;
    
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getDeadline() {
		return Deadline;
	}
	public void setDeadline(String deadline) {
		Deadline = deadline;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	@Override
	public String toString() {
		return "EvalutaionTest [Department=" + Department + ", Deadline=" + Deadline + ", Title=" + Title + "]";
	}
	public EvalutaionTest(String department, String deadline, String title) {
		super();
		Department = department;
		Deadline = deadline;
		Title = title;
	}
	public EvalutaionTest() {
		
	}
	
	
    
}
