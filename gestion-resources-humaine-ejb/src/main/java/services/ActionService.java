package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Action;
import interfaces.ActionServiceLocal;
import interfaces.ActionServiceRemote;

/**
 * Session Bean implementation class ActionService
 */
@Stateless
@LocalBean
public class ActionService implements ActionServiceRemote, ActionServiceLocal {

	/**
	 * Default constructor.
	 */

	@PersistenceContext
	private EntityManager em;

	public ActionService() {
	}

	@Override
	public void createAction(Action action) {
		em.persist(action);
	}

	@Override
	public void updateAction(Action action) {
		em.merge(action);
	}

	@Override
	public void deleteAction(Action action) {
		em.remove(em.merge(action));
	}

	@Override
	public Action getAction(int id) {
		return em.find(Action.class, id);
	}

	@Override
	public List<Action> getActions() {
		return em.createQuery("select a from Action a", Action.class).getResultList();
	}

}
