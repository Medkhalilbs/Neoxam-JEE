package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Project;
import entities.User;
import interfaces.ProjectServiceLocal;
import interfaces.ProjectServiceRemote;


@Stateless
@LocalBean
public class ProjectService implements ProjectServiceRemote {
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addProject(Project Projects) {
		try {
			entityManager.persist(Projects);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateProject(Project Projects) {
		try {
			entityManager.merge(Projects);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteProject(Project Projects) {
		try {
			entityManager.remove(entityManager.merge(Projects));
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	@Override
	public List<Project> findAllProjects() {
		Query query=entityManager.createQuery("select u from Project u");
		return query.getResultList();
	}
	
	@Override
	public Project findProjectsById(Integer id) {
		Project project=null;
		try {
			project=entityManager.find(Project.class, id);
			
		} catch (Exception e) {
			
		}
		return project;
	}
	


	@Override
	public List<Project> listeProjectsbyname() {
	 return null;
	}


	
	

}
