package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Critere;

@Local
public interface CritereServiceLocal {
	
	void createCritere(Critere critere);
	void updateCritere(Critere critere);
	void deleteCritere(Critere critere);
	Critere getCritere(int id);
	List<Critere> getCriteres();
	List<Critere> rechercheCriteres(String recherche);
}
