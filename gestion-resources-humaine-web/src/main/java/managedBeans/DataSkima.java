package managedBeans;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import entities.Role;
@ManagedBean
@ApplicationScoped
public class DataSkima {
	public Role[] getRole(){
		return Role.values();
	}
}
