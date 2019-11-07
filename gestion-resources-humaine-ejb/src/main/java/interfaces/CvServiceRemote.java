package interfaces;

import java.util.List;

import javax.ejb.Remote;
import entities.Cv;

@Remote
public interface CvServiceRemote {
	public Boolean addCv(Cv Cv) ;
	public Boolean updateCv(Cv Cv);
	public Boolean deleteCv(Cv Cv) ;
	public Cv findCvById(Integer id) ;
	public List<Cv> findAllCv() ;
	public List<Cv> listeCvbyname();
	

}
