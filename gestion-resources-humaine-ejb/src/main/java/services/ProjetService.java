package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Projet;
import entities.Topic;
import interfaces.ProjetServiceRemote;

@Stateless
@LocalBean
public class ProjetService implements ProjetServiceRemote {
	@PersistenceContext(unitName = "gestion-resources-humaine-ejb")
	public EntityManager em;
	public ProjetService()
	{}
	public void ajouterprojet(Projet p)
	{
		em.persist(p);
	}
	public List<Projet> getprojet()
	{
		TypedQuery<Projet> query = em.createQuery("Select t from Projet t", Projet.class);
		
		return query.getResultList();
	}
	public Projet findbyid(int id)
	{
TypedQuery<Projet> query = em.createQuery("Select t from Projet t where t.id=:id", Projet.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	public void deleteprojet(int id)
	{
		Projet projet = findbyid(id);
		em.remove(projet);
	}
	public List<Object> getStatistic()
	{
         @SuppressWarnings("unchecked")
		List<Object> results = em.createQuery("SELECT status,count(*) as y FROM Projet  GROUP BY status").getResultList();
		
		return results;
	}
}
