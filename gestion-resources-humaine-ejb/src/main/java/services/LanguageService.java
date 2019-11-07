package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;
import entities.language;
import interfaces.LanguageServiceLocal;
import interfaces.LanguageServiceRemote;

@Stateless
@LocalBean
public class LanguageService implements LanguageServiceRemote{
	
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addlanguage(language Language) {
		try {
			entityManager.persist(Language);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updatelanguage(language Language) {
		try {
			entityManager.merge(Language);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deletelanguage(language Language) {
		try {
			entityManager.remove(entityManager.merge(Language));
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	@Override
	public List<language> findAllLanguage() {
		Query query=entityManager.createQuery("select u from language u");
		return query.getResultList();
	}
	
	@Override
	public language findLanguageById(Integer id) {
		language lang=null;
		try {
		   lang=entityManager.find(language.class, id);
			
		} catch (Exception e) {
			
		}
		return lang;
	}

	@Override
	public List<language> listeLanguagebyname() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
	

}
