package managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entities.Critere;
import interfaces.CritereServiceLocal;


@ManagedBean
@ViewScoped
public class CretereBean {
	
	@EJB
	private CritereServiceLocal cs;
	private Critere critere,critereToUpdate;
	private List<Critere> criteres;
	
	private String recherche;
	private boolean hidden;
	int idCritere;
	
	@PostConstruct
	void init(){
		critere = new Critere();
		setCriteres(cs.getCriteres());
	}
	
	public void doCreate(){
		cs.createCritere(critere);
		criteres = cs.getCriteres();
		critere = new Critere();
	}
	
	public void doSupprimer(){
		cs.deleteCritere(cs.getCritere(idCritere));
		setCriteres(cs.getCriteres());
		idCritere = 0;
	}
	
	public void doSelect(){
		critereToUpdate = cs.getCritere(idCritere);
	}
	
	public void doUpdate(){
		cs.updateCritere(critereToUpdate);
		setCriteres(cs.getCriteres());
		critereToUpdate = new Critere();
		idCritere = 0;
	}
	public void doListerRecherche(){
		criteres =cs.rechercheCriteres(recherche);
		
		
	}

	public Critere getCritere() {
		return critere;
	}

	public void setCritere(Critere critere) {
		this.critere = critere;
	}

	public List<Critere> getCriteres() {
		return criteres;
	}

	public void setCriteres(List<Critere> criteres) {
		this.criteres = criteres;
	}

	public int getIdCritere() {
		return idCritere;
	}

	public void setIdCritere(int idCritere) {
		this.idCritere = idCritere;
	}

	public Critere getCritereToUpdate() {
		return critereToUpdate;
	}

	public void setCritereToUpdate(Critere critereToUpdate) {
		this.critereToUpdate = critereToUpdate;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getRecherche() {
		return recherche;
	}

	public void setRecherche(String recherche) {
		this.recherche = recherche;
	}

	
	
}
