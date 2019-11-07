package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.User;
import entities.User_log;
@Remote
public interface UserServiceRemote {
	public Boolean addUser(User user) ;
	public Boolean updateUser(User user);
	public Boolean deleteUser(User user) ;
	public User findUserById(Integer id) ;
	public List<User> findAllUsers() ;
	public List<User> listeUsersbyname();
	public Long CountUsers() ;
	public User getUserByLoginAndPassword(String login, String password);
	public int checkIfEmailOrLoginExist(String email,String login);
	public int checkIfLoginExist(String login);
	public int checkIfEmailExist(String email);
	public User checkUserLoginAndPassword(String login,String password);
	public User findUserById(String id);
	public Boolean deleteUser(String id);
	public Boolean deleteUser(int id) ;
	public User getUserByLogin(String login);
	public User getUserByEmailAndPassword(String email, String password);
	public User getUserByEmail(String email);
	
}