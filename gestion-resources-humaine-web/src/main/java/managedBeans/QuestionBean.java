package managedBeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


import entities.Question;
import services.QuestionService;

@ManagedBean
@SessionScoped
public class QuestionBean {
	private String enonce;
	private String complexity;
	private Float point;
	private Question question;
	@EJB 
	QuestionService questioneService; 
	public void doAdd() 
	{ 
		//String navigateTo = "null"; 
		 if (questioneService.ajout_question(new Question( enonce,complexity,point))==1)
		 {
			 FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Ajout avec succes")) ;
			 System.out.println("okitou");
			// navigateTo = "/pages/welcome2?faces-redirect=true"; 
		 }

	else {
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Echec de l'ajout")) ;
		 System.out.println("oh no");
		} 
	//return navigateTo; 
	}
	
	public String getEnonce() {
		return enonce;
	}
	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}
	public String getComplexity() {
		return complexity;
	}
	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}
	public Float getPoint() {
		return point;
	}
	public void setPoint(Float point) {
		this.point = point;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public QuestionService getQuestioneService() {
		return questioneService;
	}
	public void setQuestioneService(QuestionService questioneService) {
		this.questioneService = questioneService;
	}
	

}
