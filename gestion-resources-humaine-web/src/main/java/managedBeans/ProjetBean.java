package managedBeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.Gson;

import entities.Projet;
import services.ProjetService;

@ManagedBean
@SessionScoped
public class ProjetBean {
private String nom;
private String status;
private String msg="";
@EJB
ProjetService projetService;





public String getMsg() {
	return msg;
}





public void setMsg(String msg) {
	this.msg = msg;
}





public ProjetService getProjetService() {
	return projetService;
}





public void setProjetService(ProjetService projetService) {
	this.projetService = projetService;
}





public String getNom() {
	return nom;
}





public void setNom(String nom) {
	this.nom = nom;
}





public String getStatus() {
	return status;
}





public void setStatus(String status) {
	this.status = status;
}





public void ajouterprojet()
{
	List<Projet> list= this.afficherprojet();
	String msg;
	Boolean exist=false;
	for(Projet p : list)
	{
		if(p.getNom().equals(nom))
		{
			exist=true;
		}
	}
	
	if(exist==false)
	{
		Projet p=new Projet();
		p.setNom(nom);
		p.setStatus(status);
		projetService.ajouterprojet(p);
		this.msg="Projet Ajoute avec succées";
	}
	else
	{
		this.msg="Projet existe";
	}

	

}

public List<Projet>afficherprojet()
{
   return projetService.getprojet();	
}
public void supprimerprojet(int id)
{
	projetService.deleteprojet(id);
}


public String getStatistic()
{
	Gson g=new Gson();
	return g.toJson(projetService.getStatistic());
}
}