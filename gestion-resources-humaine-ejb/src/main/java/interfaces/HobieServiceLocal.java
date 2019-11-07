package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Hobies;

@Local
public interface HobieServiceLocal {
	
	public Boolean addHobie(Hobies Hobie) ;
	public Boolean updateHobie(Hobies Hobie);
	public Boolean deleteHobie(Hobies Hobie) ;
	public List<Hobies> findAllHobies() ;
	public List<Hobies> listeHobiesbyname();
	public Hobies findHobiesById(Integer id) ;


}
