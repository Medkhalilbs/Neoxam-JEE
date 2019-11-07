package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Domain;
import entities.TypeDomain;
import interfaces.DomainServiceRemote;

/**
 * Session Bean implementation class DomainService
 */
@Stateless
@LocalBean
public class DomainService implements DomainServiceRemote {
	@PersistenceContext(unitName = "gestion-resources-humaine-ejb")
	public EntityManager em;

	/**
	 * Default constructor.
	 */
	public DomainService() {
	}

	@Override
	public int ajouterDomain(Domain domain) {
		em.persist(domain);
		return domain.getId();
	}

	@Override
	public void deleteDomainById(int domainld) {

		Domain domain = em.find(Domain.class, domainld);
		em.remove(domain);

	}

	@Override
	public Domain getDomainById(int domainId) {
		return em.find(Domain.class, domainId);
	}

	@Override
	public void mettreAjourDomain(String name, TypeDomain typee, int domainId) {
		Domain domain = em.find(Domain.class, domainId);
		domain.setName("name");
		domain.setType(typee);
	}

	@Override
	public List<Domain> getlist() {
		TypedQuery<Domain> query = em.createQuery("Select t from Domain t ", Domain.class);
		return query.getResultList();

	}


	public Domain finddomainbyname(String nom) {
		TypedQuery<Domain> query = em.createQuery("Select d from Domain d where"
				+ " d.name=:nom ", Domain.class);
		query.setParameter("nom", nom);
		return query.getSingleResult();
	}

}
