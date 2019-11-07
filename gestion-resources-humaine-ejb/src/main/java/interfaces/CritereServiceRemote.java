package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Critere;

@Remote
public interface CritereServiceRemote {

	void createCritere(Critere critere);
	void updateCritere(Critere critere);
	void deleteCritere(Critere critere);
	Critere getCritere(int id);
	List<Critere> getCriteres();
	public List<Critere> rechercheCriteres(String recherche);
	
}
