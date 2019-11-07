package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



import java.io.IOException;



import entities.User;
import entities.User_log;
import interfaces.UserServiceRemote;
//import utils.HeaderFooter;
//import utils.PDFCreator;
import utils.PasswordUtils;


//@Statefull
@Stateless
@LocalBean
public class UserService implements UserServiceRemote{

	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;
	


	 
    @Override
	public Boolean addUser(User user) {
		try {
			entityManager.persist(user);
			return true;
		} catch (PersistenceException e) {
			System.out.println();
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh"+e.getMessage());
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh");
			return false;
		}
		
	}

	@Override
	public Boolean updateUser(User user) {
		try {
			entityManager.merge(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteUser(User user) {
		try {
			entityManager.remove(entityManager.merge(user));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@Override
	public Boolean deleteUser(int id) {
		try {
			entityManager.remove(entityManager.getReference(User.class, id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@Override
	public Boolean deleteUser(String id) {
		try {
			System.out.println("String id="+id);
			System.out.println( " Integer.parseInt(id)="+Integer.parseInt(id));
			entityManager.remove(entityManager.getReference(User.class, Integer.getInteger(id)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public User findUserById(Integer id) {
		User user=null;
		try {
			user=entityManager.find(User.class, id);
			
		} catch (Exception e) {
			
		}
		return user;
	}
	@Override
	public User findUserById(String id) {
		User user=null;
		try {
			user=entityManager.find(User.class, id);
			
		} catch (Exception e) {
			
		}
		return user;
	}
	
	@Override
	public List<User> findAllUsers() {
		Query query=entityManager.createQuery("select u from User u");
		return query.getResultList();
	}

	@Override
	public List<User> listeUsersbyname() {
		Query query=entityManager.createQuery("select h.firsNameDoc from User as h");
		return query.getResultList();
	}

	@Override
	public Long CountUsers() {
		 Query q2 = entityManager.createQuery("SELECT COUNT(e) FROM User e ");
		 Long count = (Long)q2.getSingleResult();
		    return count;
	}

	
	

			@Override
			public User getUserByLoginAndPassword(String login, String password)
			{ TypedQuery<User> query = entityManager.createQuery("Select e from User e "
			+ "where e.login=:login and "+ "e.password=:password", User.class);
			query.setParameter("login", login); 
			query.setParameter("password", PasswordUtils.digestPassword(password));
			User user = null;
			try
			{
			user = query.getSingleResult();
			}
			catch (NoResultException e) 
			{
			Logger.getAnonymousLogger().info("user not found");


			}
			return user;
			}
			
			@Override
			public User getUserByEmailAndPassword(String email, String password)
			{ TypedQuery<User> query = entityManager.createQuery("Select e from User e "
			+ "where e.email=:email and "+ "e.password=:password", User.class);
			query.setParameter("email", email); 
			query.setParameter("password", PasswordUtils.digestPassword(password));
			User user = null;
			try
			{
			user = query.getSingleResult();
			}
			catch (NoResultException e) 
			{
			Logger.getAnonymousLogger().info("user not found");


			}
			return user;
			}
			
			@Override
			public User getUserByEmail(String email)
			{ TypedQuery<User> query = entityManager.createQuery("Select e from User e "
			+ "where e.email=:email ", User.class);
			query.setParameter("email", email); 
			
			User user = null;
			try
			{
			user = query.getSingleResult();
			}
			catch (NoResultException e) 
			{
			Logger.getAnonymousLogger().info("user not found");


			}
			return user;
			}
			
			@Override
			public User getUserByLogin(String login)
			{ TypedQuery<User> query = entityManager.createQuery("Select e from User e where e.login=:login ", User.class);
			query.setParameter("login", login); 
			User user = null;
			try
			{
			user = query.getSingleResult();
			}
			catch (NoResultException e) 
			{
			Logger.getAnonymousLogger().info("user not found");


			}
			return user;
	}
			
			
			
			
			
			
			
			
			
	 
			@Override
			public int checkIfEmailOrLoginExist(String email,String login) {
				System.out.println("mail= "+email+" Login= "+login);
				System.out.println("entityManager="+entityManager);
				 Query q1 = entityManager.createQuery("SELECT COUNT(e) FROM User e where e.email=:email ");
				 q1.setParameter("email", email);
				 Query q2 = entityManager.createQuery("SELECT COUNT(e) FROM User e where e.login=:login");
				 q2.setParameter("login", login);
				 
				 Long countQ1=(Long)q1.getSingleResult();
				 Long countQ2=(Long)q2.getSingleResult();
				 
				 //**********************************************************
				 String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		           java.util.regex.Matcher m = p.matcher(email);
		           
				 //******************************************************
				 if( countQ1!=0 )
				 {
					 System.out.println("email existe");
				     return 1;
				  }
				 else if(countQ2!=0)
				 {
					 System.out.println("login existe");
					 return 2;
				 }
				 else if(!m.matches())
				 {
					 System.out.println("email invalide");
					 return 3;
				 }
				 else if(m.matches()&&countQ1==0&&countQ2==0)
				 {
					 System.out.println("email et login correct");
					 return 0;
				 }
				 return 4;
//			   final List<User> listeTest=entityManager.createQuery("select u from User u").getResultList();
//				System.out.println("22222");
//
//				for (User u:listeTest)
//				{
//					System.out.println(u);
//					if (u.getEmail()==email||u.getLogin()==login)
//						return true;
//				}
//				return false;
				
			}
	 
			
			@Override
			public int checkIfEmailExist(String email) {
				System.out.println("mail= "+email);
				 Query q1 = entityManager.createQuery("SELECT COUNT(e) FROM User e where e.email=:email ");
				 q1.setParameter("email", email);
				 
				 
				 Long countQ1=(Long)q1.getSingleResult();
				 
				 
				 //**********************************************************
				 String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		           java.util.regex.Matcher m = p.matcher(email);
		           
				 //******************************************************
				 if( countQ1!=0 )
				 {
					 System.out.println("email existe");
				     return 1;
				  }
				
				 else if(!m.matches())
				 {
					 System.out.println("email invalide");
					 return 3;
				 }
				 else if(m.matches()&&countQ1==0)
				 {
					 System.out.println("email correct");
					 return 0;
				 }
				 return 4;

			}
			
			
			 
			@Override
			public int checkIfLoginExist(String login) {
				System.out.println(" Login= "+login);
				 Query q2 = entityManager.createQuery("SELECT COUNT(e) FROM User e where e.login=:login");
				 q2.setParameter("login", login);
				 
				 Long countQ2=(Long)q2.getSingleResult();
				 
				  if(countQ2!=0)
				 {
					 System.out.println("login existe");
					 return 2;
				 }

				 else if (login.length()>=4) 
				 {
					 System.out.println(" login correct");
					 return 0;
				 }
				  
				return 4;

				
			} 
			
			@Override
			public User checkUserLoginAndPassword(String login,String password)
			{  
			System.out.println("avant typedQuery");
			TypedQuery<User> query = entityManager.createQuery("Select e from User e "
					+ "where e.login=:login and "+ "e.password=:password", User.class);
					query.setParameter("login", login); 
					query.setParameter("password", password);
					System.out.println("Apres typedQuery et setsParameters");
			
			
//			TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
//	        query.setParameter("login", login);
//	        query.setParameter("password", PasswordUtils.digestPassword(password));
//	        System.out.println("PasswordUtils.digestPassword(password)= "+PasswordUtils.digestPassword(password));

	        return query.getSingleResult();
	        }
			
			
	
			
	

}
