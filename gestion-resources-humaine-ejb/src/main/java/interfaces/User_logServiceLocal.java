package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.User_log;

@Local
public interface User_logServiceLocal {
	public Boolean addUser_log(User_log User_log) ;
	public Boolean updateUser_log(User_log User_log);
	public Boolean deleteUser_log(User_log User_log) ;
	public User_log findUser_logById(Integer id) ;
	public List<User_log> findAllUser_logs() ;
	public Long CountUser_logs() ;
	public List<User_log> getListeUser_logsByUserID(int id);
	public User_log getLastUser_Log(int user_id);
	public User_log getUser_LogForLogoutTimeSet(int user_id,String sessionid);
	
	
	
}
