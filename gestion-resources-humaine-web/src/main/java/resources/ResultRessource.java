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

import entities.Result;
import entities.Topic;
import services.ResultService;
import services.TopicService;


@Path("/test/result")
@RequestScoped
public class ResultRessource {
	
	@EJB
	ResultService result;

	@GET
	@Path("/afficher")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getResult() {
		System.out.println("result");
		return Response.ok(result.findAllResult()).build();
		// Response : reponse jeya m serveur __statut
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/afficher
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ajouter")
	public Response addResult(Result t) {
		return Response.ok(result.addResult(t), MediaType.TEXT_PLAIN).build();
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/topic/ajouter
	

	@GET
	@Path("/top/{id}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getResult(@PathParam("id") int id) {
		return Response.ok(result.findResultById(id)).build();
	}
	//localhost:18080/gestion-resources-humaine-web/activator/test/topic/top?id=8


}
