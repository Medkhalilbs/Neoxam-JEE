package interfaces;

import java.io.File;
import java.util.List;

import javax.ejb.Local;

import entities.Candidate;


@Local
public interface CandidateServiceLocal {
	
	public Boolean addCandidate(Candidate Candidate) ;
	public Boolean updateCandidate(Candidate Candidate);
	public Boolean deleteCandidate(Candidate Candidate) ;
	public List<Candidate> findAllCandidate() ;
	public List<Candidate> listeCandidatebyname();
	public Candidate findCandidateById(Integer id) ;
	public Boolean deleteCandidateWithId(int condidatId);
	public Candidate parsePDFtoCandidate(File file);
	
	

}
