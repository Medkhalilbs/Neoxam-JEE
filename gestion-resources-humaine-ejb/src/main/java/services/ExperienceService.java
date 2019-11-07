package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Cv;
import entities.Experience;
import interfaces.ExperienceServiceLocal;
import interfaces.ExperienceServiceRemote;

@Stateless
@LocalBean
public class ExperienceService implements ExperienceServiceRemote {
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addExperience(Experience Experience) {
		try {
			entityManager.persist(Experience);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateExperience(Experience Experience) {
		try {
			entityManager.merge(Experience);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteExperience(Experience Experience) {
		try {
			entityManager.remove(entityManager.merge(Experience));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Experience> FindAllExperience() {
		Query query=entityManager.createQuery("select u from Experience u");
		return query.getResultList();
	}

	@Override
	public Experience FindExperienceById(Integer id) {
		Experience Exp=null;
		try {
			Exp=entityManager.find(Experience.class, id);
			
		} catch (Exception e) {
			
		}
		return Exp;
	}
	
	@Override
	public List<Experience> ListeExperienceByName() {
		// TODO Auto-generated method stub
		return null;
	}

}
