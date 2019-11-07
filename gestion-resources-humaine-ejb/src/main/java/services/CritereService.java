package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Critere;
import interfaces.CritereServiceLocal;
import interfaces.CritereServiceRemote;

/**
 * Session Bean implementation class CritereService
 */
@Stateless
@LocalBean
public class CritereService implements CritereServiceRemote, CritereServiceLocal {

	/**
	 * Default constructor.
	 */
	@PersistenceContext
	private EntityManager em;

	public CritereService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createCritere(Critere critere) {
		em.persist(critere);
	}

	@Override
	public void updateCritere(Critere critere) {
		em.merge(critere);
	}

	@Override
	public void deleteCritere(Critere critere) {
		em.remove(em.merge(critere));
	}

	@Override
	public Critere getCritere(int id) {
		return em.find(Critere.class, id);
	}

	@Override
	public List<Critere> getCriteres() {
		return em.createQuery("select a from Critere a", Critere.class).getResultList();
	}
	@Override
	public List<Critere> rechercheCriteres(String recherche) {
		return em.createQuery("select a from Critere a where a.DescrCritere LIKE :x OR a.Pourcentage LIKE :x", Critere.class).setParameter("x", "%"+recherche+"%").getResultList();
	}
	

}
