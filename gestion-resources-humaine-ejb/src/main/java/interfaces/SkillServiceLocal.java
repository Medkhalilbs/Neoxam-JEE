package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Skills;

@Local
public interface SkillServiceLocal {
	
	public Boolean addSkills(Skills Skill) ;
	public Boolean updateSkills(Skills Skill);
	public Boolean deleteSkills(Skills Skill) ;
	public List<Skills> findAllCv() ;
	public List<Skills> listeCvbyname();
	public Skills findCvById(Integer id) ;

}
