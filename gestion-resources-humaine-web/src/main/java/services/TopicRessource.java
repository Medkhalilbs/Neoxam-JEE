package services;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Question;
import entities.Topic;

@Path("/test/topic")
@RequestScoped

public class TopicRessource {
	@EJB
	TopicService topic;

	@GET
	@Path("/afficher")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getTopic() {
		System.out.println("topic");
		return Response.ok(topic.getlist()).build();
		// Response : reponse jeya m serveur __statut
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/afficher

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ajouter")
	public Response addDomain(Topic t) {
		return Response.ok(topic.ajouterTopic(t), MediaType.TEXT_PLAIN).build();
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/ajouter
	

	@GET
	@Path("/top")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getTopic(@QueryParam("id") int id) {
		return Response.ok(topic.gettopics(id)).build();
	}
	//localhost:18080/gestion-resources-humaine-web/activator/test/topic/top?id=8

	
	@GET
	@Path("/recherche/{dom}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rechechertopic(@PathParam("dom") String dom) {
		return Response.ok(topic.rechechertopic(dom)).build();
	}
	//http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/recherche/Jee
	
	
	 @DELETE
	    @Path("supprimer/{id}")
	    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	    public void deletetopicById(@PathParam("id") int id) {
		 topic.deletetopicById(id);
	    }
	//http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/supprimer/12
	

}
