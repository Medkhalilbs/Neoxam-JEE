package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Formation;

@Local
public interface FormationServiceLocal {
	
	public Boolean addFormation(Formation Formation) ;
	public Boolean updateFormation(Formation Formation);
	public Boolean deleteFormation(Formation Formation) ;
	public List<Formation> findAllFormation() ;
	public List<Formation> listeFormationbyname();
	public Formation findFormationById(Integer id) ;


}
