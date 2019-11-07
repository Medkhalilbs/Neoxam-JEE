package managedBeans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import utils.Status;

@ManagedBean
@ApplicationScoped
public class UserData {
	public Status[] getStatus()
	{
		return Status.values();
	}

}
