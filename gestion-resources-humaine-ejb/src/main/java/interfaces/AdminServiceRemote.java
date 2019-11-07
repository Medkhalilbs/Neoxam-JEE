package interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import entities.Administrator;
import entities.User;

@Remote
public interface AdminServiceRemote {
	public Boolean addAdmin(Administrator Admin) ;
	public Boolean updateAdmin(Administrator Admin);
	public Boolean deleteAdmin(Administrator Admin) ;
	public Administrator findAdminById(Integer id) ;
	public List<Administrator> findAllAdmins() ;
	public List<Administrator> listeAdminsbyname();
	public Long CountAdmins() ;
	public Administrator getAdminByCIN(String cin);
	
	
	
}
