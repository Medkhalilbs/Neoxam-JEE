package interfaces;

import java.util.List;

import javax.ejb.Local;


import entities.Cv;

@Local
public interface CvServiceLocal {
	
	public Boolean addCv(Cv Cv) ;
	public Boolean updateCv(Cv Cv);
	public Boolean deleteCv(Cv Cv) ;
	public Cv findCvById(Integer id) ;
	public List<Cv> findAllCv() ;
	public List<Cv> listeCvbyname();
	
	

}
