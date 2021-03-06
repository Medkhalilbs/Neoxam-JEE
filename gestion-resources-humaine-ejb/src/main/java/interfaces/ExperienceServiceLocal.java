package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Experience;

@Local
public interface ExperienceServiceLocal {
	
	public Boolean addExperience(Experience Experience) ;
	public Boolean updateExperience(Experience Experience);
	public Boolean deleteExperience(Experience Experience) ;
	public List<Experience> FindAllExperience() ;
	public List<Experience> ListeExperienceByName();
	public Experience FindExperienceById(Integer id) ;


}
