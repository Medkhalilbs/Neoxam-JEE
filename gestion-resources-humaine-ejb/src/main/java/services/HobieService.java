package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Hobies;
import entities.User;
import interfaces.HobieServiceLocal;
import interfaces.HobieServiceRemote;

@Stateless
@LocalBean
public class HobieService implements HobieServiceRemote{
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addHobie(Hobies Hobie) {
		try {
			entityManager.persist(Hobie);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateHobie(Hobies Hobie) {
		try {
			entityManager.merge(Hobie);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteHobie(Hobies Hobie) {
		try {
			entityManager.remove(entityManager.merge(Hobie));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Hobies> findAllHobies() {
		Query query=entityManager.createQuery("select u from Hobie u");
		return query.getResultList();
	}
	
	@Override
	public Hobies findHobiesById(Integer id) {
		Hobies Hobi=null;
		try {
			Hobi=entityManager.find(Hobies.class, id);
			
		} catch (Exception e) {
			
		}
		return Hobi;
	}

	@Override
	public List<Hobies> listeHobiesbyname() {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
