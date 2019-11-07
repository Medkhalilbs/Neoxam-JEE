package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Cv;
import interfaces.CvServiceRemote;

@Stateless
@LocalBean
public class CvService implements CvServiceRemote{
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addCv(Cv Cv) {
		try {
			entityManager.persist(Cv);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateCv(Cv Cv) {
		try {
			entityManager.merge(Cv);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteCv(Cv Cv) {
		try {
			entityManager.remove(entityManager.merge(Cv));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Cv findCvById(Integer id) {
		Cv cv=null;
		try {
			cv=entityManager.find(Cv.class, id);
			
		} catch (Exception e) {
			
		}
		return cv;
	}

	@Override
	public List<Cv> findAllCv() {
		Query query=entityManager.createQuery("select u from Cv u");
		return query.getResultList();
	}

	@Override
	public List<Cv> listeCvbyname() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
