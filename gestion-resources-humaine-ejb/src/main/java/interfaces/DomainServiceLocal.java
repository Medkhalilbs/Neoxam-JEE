package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Domain;
import entities.TypeDomain;

@Local
public interface DomainServiceLocal {

	int ajouterDomain(Domain dep);

	void deleteDomainById(int domainld);

	Domain getDomainById(int domainId);

	void mettreAjourDomain(String name, TypeDomain typee, int domainId);


	public List<Domain> getlist();

}
