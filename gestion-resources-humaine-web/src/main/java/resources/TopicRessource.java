package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Question;
import entities.Topic;
import services.TopicService;

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
	

	@DELETE
	@Path("supprimer/{id}")
	@Produces( MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	public void deletetopicById(@PathParam("id") int id) {
		topic.deletetopicById(id);
		System.out.println("deleeeteeee");
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ajouter")
	public Response addDomain(Topic t) {
		return Response.ok(topic.ajouterTopic(t), MediaType.TEXT_PLAIN).build();
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/ajouter
	
	@PUT
 	@Path("/updateTopic")
    public Response updateUser(Topic t) {

        if (topic.updateTopic(t)) {
        	  return Response.ok().status(Response.Status.ACCEPTED).build();
           // return Response.ok().status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.notModified().build();
        }           
    }
	

	@GET
	@Path("/top")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getTopic(@QueryParam("id") int id) {
		return Response.ok(topic.gettopics(id)).build();
	}
	//localhost:18080/gestion-resources-humaine-web/activator/test/topic/top?id=8
	

	@GET
	@Path("/topicid/{id}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getTopicByid(@PathParam("id") int id) {
		return Response.ok(topic.getTopicById(id)).build();
	}
	//localhost:18080/gestion-resources-humaine-web/api/test/topicid/

	
	@GET
	@Path("/recherche/{dom}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rechechertopic(@PathParam("dom") String dom) {
		return Response.ok(topic.rechechertopic(dom)).build();
	}
	//http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/recherche/Jee
	
	@GET
	@Path("/nbtopicpardomain/{nom}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response nbtopicpardomain(@PathParam("nom") String nom) {
		return Response.ok(topic.nbtopicpardomain(nom)).build();
	}
	//http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/nbtopicpardomain/Web
	
	
	
	
	@DELETE
	@Path("supprimer/{id}")
	@Produces( MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	public void deleteTopicById(@PathParam("id") int id) {
		topic.deletetopicById(id);
		System.out.println("deleeeteeee");
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/supprimer/12
	
	@GET
	@Path("getTopicbyname/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopicByName(@PathParam("name") String name) {
		List<Topic> t = topic.findByNamee(name);
		
			return Response.ok(t, MediaType.APPLICATION_JSON).build();
	}

	
	@GET
	@Path("/getstat")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopictByName() {
		List<Object> top = topic.chartTopic();
		
			return Response.ok(top, MediaType.APPLICATION_JSON).build();
	}
}
