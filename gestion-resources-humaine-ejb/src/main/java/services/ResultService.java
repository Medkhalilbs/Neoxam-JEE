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

import entities.Result;
import entities.User;
import entities.User_log;
import interfaces.ResultServiceRemote;
import interfaces.UserServiceRemote;
//import utils.HeaderFooter;
//import utils.PDFCreator;
import utils.PasswordUtils;


//@Statefull
@Stateless
@LocalBean
public class ResultService implements ResultServiceRemote{

	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;
	


	 
    @Override
	public Boolean addResult(Result result) {
		try {
			entityManager.persist(result);
			return true;
		} catch (PersistenceException e) {
			System.out.println();
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh"+e.getMessage());
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh");
			return false;
		}
		
	}

	@Override
	public Boolean updateResult(Result result) {
		try {
			entityManager.merge(result);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteResult(Result result) {
		try {
			entityManager.remove(entityManager.merge(result));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@Override
	public Boolean deleteResult(int id) {
		try {
			entityManager.remove(entityManager.getReference(Result.class, id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@Override
	public Boolean deleteResult(String id) {
		try {
			System.out.println("String id="+id);
			System.out.println( " Integer.parseInt(id)="+Integer.parseInt(id));
			entityManager.remove(entityManager.getReference(Result.class, Integer.getInteger(id)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Result findResultById(Integer id) {
		Result result=null;
		try {
			result=entityManager.find(Result.class, id);
			System.out.println(result);
			
		} catch (Exception e) {
			
		}
		return result;
	}
	@Override
	public Result findResultById(String id) {
		Result result=null;
		try {
			result=entityManager.find(Result.class, id);
			
		} catch (Exception e) {
			
		}
		return result;
	}
	
	@Override
	public List<Result> findAllResult() {
		Query query=entityManager.createQuery("select u from Result u");
		System.out.println( query.getResultList());

		return query.getResultList();
	}

	@Override
	public List<User> listeResultsbyname() {
		Query query=entityManager.createQuery("select h.firsNameDoc from Result as h");
		return query.getResultList();
	}

	@Override
	public Long CountResults() {
		 Query q2 = entityManager.createQuery("SELECT COUNT(e) FROM Result e ");
		 Long count = (Long)q2.getSingleResult();
		    return count;
	}



//			@Override
//			public Result getUserByLoginAndPassword(String login, String password)
//			{ TypedQuery<User> query = entityManager.createQuery("Select e from Result e "
//			+ "where e.login=:login and "+ "e.password=:password", Result.class);
//			query.setParameter("login", login); 
//			query.setParameter("password", PasswordUtils.digestPassword(password));
//			Result result = null;
//			try
//			{
//			result = query.getSingleResult();
//			}
//			catch (NoResultException e) 
//			{
//			Logger.getAnonymousLogger().info("result not found");
//
//
//			}
//			return result;
//	}
			
		
}
