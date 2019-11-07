package services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.OptimisticLockException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;

import entities.Message;
import entities.User_log;
import interfaces.MessageServiceLocal;
import interfaces.MessageServiceRemote;
import utils.PasswordUtils;
//@Statefull
@Stateless
@LocalBean
public class MessageService implements MessageServiceRemote{
	
	
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;
	


	 
    @Override
	public Boolean addMessage(Message message) {
		try {
			entityManager.persist(message);
			return true;
		} catch (PersistenceException e) {
			System.out.println();
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh"+e.getMessage());
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh");
			return false;
		}
		
	}

	@Override
	public Boolean updateMessage(Message message) {
		try {
			entityManager.merge(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteMessage(Message message) {
		try {
			
			
			entityManager.remove(entityManager.merge(message));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@Override
	public Boolean deleteMessage(int id) {
		try {
			 int isSuccessful = entityManager.createQuery("delete from Message p where p.id=:id").setParameter("id", id).executeUpdate();
	
//			Message message=entityManager.find(Message.class, id);
//			message.setUser(null);
//			entityManager.merge(message);
//			entityManager.flush();
//			Message message1=entityManager.find(Message.class, id);
//			entityManager.remove(message1);
		
			return true;
		} catch (Exception e) {
			System.out.println("Exception deleteMessage :"+e.getMessage());
			return false;
		}
	}
	
	
	@Override
	public Boolean deleteMessage(String id) {
		try {
			entityManager.remove(entityManager.getReference(Message.class, id));
			return true;
		} catch (Exception e) {
			System.out.println("Exception deleteMessage :"+e.getMessage());
			return false;
		}
	}

	@Override
	public Message findMessageById(Integer id) {
		Message message=null;
		try {
			message=entityManager.find(Message.class, id);
			
		} catch (Exception e) {
			
		}
		return message;
	}
	@Override
	public Message findMessageById(String id) {
		Message message=null;
		try {
			message=entityManager.find(Message.class, id);
			
		} catch (Exception e) {
			
		}
		return message;
	}
	
	@Override
	public List<Message> findAllMessages() {
		Query query=entityManager.createQuery("select u from Message u");
		return query.getResultList();
	}

	@Override
	public List<Message> listeMessagesByname() {
		Query query=entityManager.createQuery("select h.firsNameDoc from Message as h");
		return query.getResultList();
	}

	@Override
	public Long CountMessages() {
		 Query q2 = entityManager.createQuery("SELECT COUNT(e) FROM Message e ");
		 Long count = (Long)q2.getSingleResult();
		    return count;
	}


	 
			
			
	

}
