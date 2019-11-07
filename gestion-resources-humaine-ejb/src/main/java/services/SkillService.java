package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Project;
import entities.Skills;
import interfaces.SkillServiceLocal;
import interfaces.SkillServiceRemote;

@Stateless
@LocalBean
public class SkillService implements SkillServiceRemote{
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addSkills(Skills Skill) {
		try {
			entityManager.persist(Skill);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateSkills(Skills Skill) {
		try {
			entityManager.merge(Skill);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteSkills(Skills Skill) {
		try {
			entityManager.remove(entityManager.merge(Skill));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Skills> findAllCv() {
		Query query=entityManager.createQuery("select u from Skills u");
		return query.getResultList();
	}
	
	@Override
	public Skills findCvById(Integer id) {
		Skills Skill=null;
		try {
			Skill=entityManager.find(Skills.class, id);
			
		} catch (Exception e) {
			
		}
		return Skill;
	}

	@Override
	public List<Skills> listeCvbyname() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}
