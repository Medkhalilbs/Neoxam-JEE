package services;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.User;
import entities.User_log;
import interfaces.User_logServiceLocal;
import interfaces.User_logServiceRemote;

@Stateless
@LocalBean
public class User_logService implements User_logServiceRemote{
	
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

    @Override
	public Boolean addUser_log(User_log user_log) {
		try {
			entityManager.persist(user_log);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public Boolean updateUser_log(User_log user_log) {
		try {
			entityManager.merge(user_log);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteUser_log(User_log user_log) {
		try {
			entityManager.remove(entityManager.merge(user_log));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public User_log findUser_logById(Integer id) {
		User_log User_log=null;
		try {
			User_log=entityManager.find(User_log.class, id);
			
		} catch (Exception e) {
			
		}
		return User_log;
	}
	
	
	@Override
	public List<User_log> getListeUser_logsByUserID(int id)
	{ TypedQuery<User_log> query = entityManager.createQuery("Select e from User_log e where e.user.id=:user_id ", User_log.class);
	query.setParameter("user_id", id); 
	
	List<User_log> userlog = null;
	try
	{
	userlog = query.getResultList();
	}
	catch (NoResultException e) 
	{
	Logger.getAnonymousLogger().info("user not found");


	}
	return userlog;
}

	
	@Override
	public List<User_log> findAllUser_logs() {
		Query query=entityManager.createQuery("select u from User_log u");
		return query.getResultList();
	}



	@Override
	public Long CountUser_logs() {
		 Query q2 = entityManager.createQuery("SELECT COUNT(e) FROM User_log e ");
		 Long count = (Long)q2.getSingleResult();
		    return count;
	}

 
	@Override
	public User_log getLastUser_Log(int user_id) {
		User_log user_log=null;
		try{
		TypedQuery<User_log> query = entityManager.createQuery("Select e from User_log e where e.user.id=:user_id ORDER BY e.id DESC", User_log.class);
		query.setParameter("user_id", user_id); 
		query.setMaxResults(1);
		user_log=(User_log)query.getSingleResult();
		}
		catch(NoResultException e)
		{System.out.println(e.getMessage());}
		
		    return user_log;
	}
	 
	
	@Override
	public User_log getUser_LogForLogoutTimeSet(int user_id,String sessionid) {
		TypedQuery<User_log> query = entityManager.createQuery("Select e from User_log e where e.user.id=:user_id and e.session_id=:session_id ORDER BY e.id DESC", User_log.class);
		query.setParameter("user_id", user_id); 
		query.setParameter("session_id", sessionid); 
		query.setMaxResults(1);
		    return (User_log)query.getSingleResult();
	}
	

}
