package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Administrator;
import interfaces.AdminServiceLocal;
import interfaces.AdminServiceRemote;

@Stateless
@LocalBean
public class AdminService implements AdminServiceRemote{
	
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

    @Override
	public Boolean addAdmin(Administrator admin) {
		try {
			entityManager.persist(admin);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}

	@Override
	public Boolean updateAdmin(Administrator admin) {
		try {
			entityManager.merge(admin);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteAdmin(Administrator admin) {
		try {
			entityManager.remove(entityManager.merge(admin));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Administrator findAdminById(Integer id) {
		Administrator admin=null;
		try {
			admin=entityManager.find(Administrator.class, id);
			
		} catch (Exception e) {
			
		}
		return admin;
	}

	
	@Override
	public List<Administrator> findAllAdmins() {
		Query query=entityManager.createQuery("select u from Administrator u");
		return query.getResultList();
	}

	@Override
	public List<Administrator> listeAdminsbyname() {
		Query query=entityManager.createQuery("select h.firsNameDoc from Admin as h");
		return query.getResultList();
	}

	@Override
	public Long CountAdmins() {
		 Query q2 = entityManager.createQuery("SELECT COUNT(e) FROM Admin e ");
		 Long count = (Long)q2.getSingleResult();
		    return count;
	}

	@Override
	public Administrator getAdminByCIN(String cin) {
		return entityManager.find(Administrator.class, cin);
	}

	 
	

}
