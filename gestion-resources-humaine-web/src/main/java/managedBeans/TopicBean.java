package managedBeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import entities.Answer;
import entities.Domain;
import entities.Question;
import entities.Topic;
import services.DomainService;
import services.QuestionService;
import services.TopicService;

@ManagedBean
@SessionScoped

public class TopicBean {

	private int dureeTopic;
	private int idtopic;
	private int nombre_question;
	private Domain domain;
	List<String> cs = new ArrayList<String>();
	private List<Question> listequestion;
	private String nom;
	private String dom;
	private String console;
	private String enoncequest;
	private String complexity;
	private String indice;
	private int point;
	private int idquestion;
	private String repexact;
	private String enoncerep;
	private Part uploadedFile;
	private String folder = "c:\\files";
    private UIComponent mybutton;
    String fileName;



	@EJB
	TopicService topicService;
	@EJB
	DomainService domainTopic;
	@EJB
	QuestionService questionService;
//
	public String ajouterquestion() throws IOException {
		String navigateTo = null;
		Question q = new Question();
		q.setTopic(topicService.getTopicById(idtopic));

		if (questionService.nombredequestionbyId(idtopic) < topicService.getTopicById(idtopic).getNombre_question()) {
			q.setEnonce(enoncequest);
			q.setComplexity(complexity);
			q.setIndice(indice);
			q.setPoint(point);

			questionService.ajout_question(q);
			navigateTo = "/pages/chahnez/afficherquestion?faces-redirect=true";

		} else {
			System.out.println("nombre question est sature pour ce sujet");
			navigateTo = "/pages/chahnez/afficherquestion?faces-redirect=true";
		//	FacesContext.getCurrentInstance().addMessage("chahnez:btn", new FacesMessage("Erreur","nombre question est sature pour ce sujet"));
		//	FacesContext.getCurrentInstance().addMessage("form:bnt", new FacesMessage("Echec de l'ajout"));
//		    FacesMessage message = new FacesMessage("Invalid password length");
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(mybutton.getClientId(context), message);
			FacesContext.getCurrentInstance().addMessage("myForm:btn", new FacesMessage("nombre question est sature pour ce sujet"));
		}
		return navigateTo;

	}

//
	public String doAdd() throws IOException {
		Domain d1 = new Domain();
		String navigateTo = null;
		d1 = domainTopic.finddomainbyname(console);
		
		if (topicService.ajouterTopic(new Topic(nom, dureeTopic, nombre_question, d1)) == 1) {
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Ajout sujet avec succes"));
			System.out.println("okitou");
			
			// FacesContext.getCurrentInstance().getExternalContext().dispatch("/pages/chahnez/afficherTopic.xhtml");
			navigateTo = "/pages/chahnez/afficherTopic?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Echec de l'ajout"));
			System.out.println("oh no");
			navigateTo = "/pages/chahnez/afficherTopic?faces-redirect=true";

		}
		return navigateTo;
	}


//	public void saveFile() {
//
//		try (InputStream input = uploadedFile.getInputStream()) {
//			String fileName = uploadedFile.getSubmittedFileName();
//			Files.copy(input, new File(folder, fileName).toPath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	//
	public String ajouterreponse() {
		String navigateTo = null;
		Answer a = new Answer();
		a.setType_reponse(enoncerep);
		a.setQuestion(topicService.reponseparidquest(idquestion));
		topicService.ajouterreponse(a, idquestion);
		  navigateTo = "/pages/chahnez/afficherreponse?faces-redirect=true";
		  return navigateTo;
	}
//
	public String valid(int id) {
		idtopic = id;
		return "validerexam?faces-redirect=true";

	}
	public void mmb(int id) {
		Question a = topicService.reponseparidquest(id);
		System.out.println(a.getId());
		System.out.println(repexact);
		a.setReponsejuste(repexact);
		topicService.updatequestion(a);
	}

	public String affrecherche() {

		return "RechercheTopic?faces-redirect=true";
	}
	//
	public List<Topic> afficherrechtopic() {
		return topicService.rechechertopic(dom);

	}

//
	public void supprmier(int topicId) {
		topicService.deletetopicById(topicId);
	}
//
	public List<Topic> affichertopic() {
		return topicService.getlist();

	}
//
	public List<Question> getquestions() {
		System.out.println(idtopic);
		return topicService.getquestions(idtopic);
	}
	
	public String afficherquestion(int id) {
		this.idtopic = id;
		return "afficherquestion?faces-redirect=true";
	}

	public String afficherrep(int id) {
		idquestion = id;
		return "afficherreponse?faces-redirect=true";
	}
	
	public List<Answer> getreponsesquest() {
		return topicService.reponseparidquest(idquestion).getListereponse();
	}
//
	public List<Answer> getreponsesquest1(int id) {
		return topicService.reponseparidquest(id).getListereponse();
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public int getDureeTopic() {
		return dureeTopic;
	}

	public void setDureeTopic(int dureeTopic) {
		this.dureeTopic = dureeTopic;
	}

	public int getNombre_question() {
		return nombre_question;
	}

	public void setNombre_question(int nombre_question) {
		this.nombre_question = nombre_question;
	}

	public int getIdtopic() {
		return idtopic;
	}

	public void setIdtopic(int idtopic) {
		this.idtopic = idtopic;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public List<Question> getListequestion() {
		return listequestion;
	}

	public void setListequestion(List<Question> listequestion) {
		this.listequestion = listequestion;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public TopicService getTopicService() {
		return topicService;
	}

	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}

	public DomainService getDomainTopic() {
		return domainTopic;
	}

	public void setDomainTopic(DomainService domainTopic) {
		this.domainTopic = domainTopic;
	}

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public String getEnoncequest() {
		return enoncequest;
	}

	public void setEnoncequest(String enoncequest) {
		this.enoncequest = enoncequest;
	}

	public int getIdquestion() {
		return idquestion;
	}

	public void setIdquestion(int idquestion) {
		this.idquestion = idquestion;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}



	public List<Domain> domain() {
		return domainTopic.getlist();

	}

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	
	public List<String> getCs() {
		return cs;
	}

	public void setCs(List<String> cs) {
		this.cs = cs;
	}

	public String getRepexact() {
		return repexact;
	}

	public void setRepexact(String repexact) {
		this.repexact = repexact;
	}

	public String getEnoncerep() {
		return enoncerep;
	}

	public void setEnoncerep(String enoncerep) {
		this.enoncerep = enoncerep;
	}


	public String getFolder() {
		return folder;
	}


	public void setFolder(String folder) {
		this.folder = folder;
	}


	public UIComponent getMybutton() {
		return mybutton;
	}


	public void setMybutton(UIComponent mybutton) {
		this.mybutton = mybutton;
	}

}
