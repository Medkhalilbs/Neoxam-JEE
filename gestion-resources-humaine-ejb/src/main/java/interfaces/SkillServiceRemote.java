package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Skills;

@Remote
public interface SkillServiceRemote {
	
	public Boolean addSkills(Skills Skill) ;
	public Boolean updateSkills(Skills Skill);
	public Boolean deleteSkills(Skills Skill) ;
	public List<Skills> findAllCv() ;
	public List<Skills> listeCvbyname();
	public Skills findCvById(Integer id) ;


}
