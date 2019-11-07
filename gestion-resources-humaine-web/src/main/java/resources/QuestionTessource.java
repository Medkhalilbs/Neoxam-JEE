package resources;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Answer;
import entities.Question;
import services.QuestionService;
import services.TopicService;
@Path("/test/question")
@RequestScoped

public class QuestionTessource {
	
@EJB
QuestionService question;
@EJB
TopicService topic;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ajouter")
	public Response ajout_question(Question q)  {
		return Response.ok(question.ajout_question(q) , MediaType.TEXT_PLAIN).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ajouterreponse")
	public Response ajout_reponse( Answer r) {
		return Response.ok(question.ajout_reponse(r) , MediaType.TEXT_PLAIN).build();
	}
	
	@GET
	@Path("/afficherquestion/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response afficherquestionpartopic(@PathParam("id") int id) {
		return Response.ok(topic.getquestions(id)).build();
	}
	
	//http://localhost:18080/gestion-resources-humaine-web/activator/test/question/afficherquestion/4
	

	
	@GET
	@Path("/getlistereponsequestions/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getlistereponsequestions(@PathParam("id") int id) {
		return Response.ok(topic.getlistereponsequestions(id)).build();
	}
	//http://localhost:18080/gestion-resources-humaine-web/activator/test/question/getlistereponsequestions/9
	
	
	
	
	
	

}
