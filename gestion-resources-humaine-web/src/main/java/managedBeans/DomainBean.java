package managedBeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entities.Domain;
import entities.Topic;
import entities.TypeDomain;
import services.DomainService;
@ManagedBean
@SessionScoped
public class DomainBean {
	private String name;
	private TypeDomain type;
	private String console;
	
	@EJB 
	DomainService domainService; 
	
	public String doAdd() 
	
	{ 
		String navigateTo = null;
		//String navigateTo = "null"; 
		 if (domainService.ajouterDomain(new Domain(name,TypeDomain.valueOf(console)))==1)
		 {
			 FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Ajout du theme avec succes")) ;
			 System.out.println("okitou");
			 navigateTo = "/pages/chahnez/afficherDomain?faces-redirect=true"; 
		 }

	else {
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Echec de l'ajout")) ;
		 System.out.println("oh no");
		 navigateTo = "/pages/chahnez/afficherDomain?faces-redirect=true"; 
		}
		return navigateTo; 
	}
	
	public List<Domain> afficherdomain() {
		return domainService.getlist();

	}
	
	public String supprmier(int domainld) {
	String	navigateTo = null;
		domainService.deleteDomainById(domainld);
		navigateTo = "/pages/chahnez/afficherDomain?faces-redirect=true"; 
		return navigateTo ;
	}
	
	public void execute() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Executed", "Using RemoteCommand."));
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TypeDomain getType() {
		return type;
	}
	public void setType(TypeDomain type) {
		this.type = type;
	}

	public String getConsole() {
		return console;
	}
	public void setConsole(String console) {
		this.console = console;
	}
	
	
	
	
	
	

}
