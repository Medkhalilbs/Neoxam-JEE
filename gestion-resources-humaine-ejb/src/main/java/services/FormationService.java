package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Experience;
import entities.Formation;
import interfaces.FormationServiceLocal;
import interfaces.FormationServiceRemote;

@Stateless
@LocalBean
public class FormationService implements FormationServiceRemote{
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addFormation(Formation Formation) {
		try {
			entityManager.persist(Formation);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateFormation(Formation Formation) {
		try {
			entityManager.merge(Formation);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteFormation(Formation Formation) {
		try {
			entityManager.remove(entityManager.merge(Formation));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Formation> findAllFormation() {
		Query query=entityManager.createQuery("select u from Formation u");
		return query.getResultList();
	}

	@Override
	public Formation findFormationById(Integer id) {
		
		Formation Form=null;
		try {
			Form=entityManager.find(Formation.class, id);
			
		} catch (Exception e) {
			
		}
		return Form;
	}
	
	@Override
	public List<Formation> listeFormationbyname() {
		// TODO Auto-generated method stub
		return null;
	}



}
