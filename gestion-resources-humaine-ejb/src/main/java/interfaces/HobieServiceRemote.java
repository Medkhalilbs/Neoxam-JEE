package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Hobies;

@Remote
public interface HobieServiceRemote {
	
	public Boolean addHobie(Hobies Hobie) ;
	public Boolean updateHobie(Hobies Hobie);
	public Boolean deleteHobie(Hobies Hobie) ;
	public List<Hobies> findAllHobies() ;
	public List<Hobies> listeHobiesbyname();
	public Hobies findHobiesById(Integer id) ;

}
