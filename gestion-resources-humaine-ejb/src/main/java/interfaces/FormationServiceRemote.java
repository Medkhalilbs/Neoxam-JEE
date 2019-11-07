package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Formation;

@Remote
public interface FormationServiceRemote {
	
	public Boolean addFormation(Formation Formation) ;
	public Boolean updateFormation(Formation Formation);
	public Boolean deleteFormation(Formation Formation) ;
	public List<Formation> findAllFormation() ;
	public List<Formation> listeFormationbyname();
	public Formation findFormationById(Integer id) ;

}
