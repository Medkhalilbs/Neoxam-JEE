package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Project;

@Local
public interface ProjectServiceLocal {
	
	public Boolean addProject(Project Projects) ;
	public Boolean updateProject(Project Projects);
	public Boolean deleteProject(Project Projects) ;
	public List<Project> findAllProjects() ;
	public List<Project> listeProjectsbyname();
	public Project findProjectsById(Integer id) ;

}
