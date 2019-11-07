package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Domain;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;

import services.DomainService;

@Path("/test/domain")
@RequestScoped

public class DomainRessource {
	@EJB
	DomainService domain;

	// DomainServiceLocal domain;

	@GET
	@Path("/afficher")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getDomain() {
		return Response.ok(domain.getlist()).build();
	}
	// Response : reponse jeya m serveur __statut
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/domain/afficher

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ajouter")
	public Response addDomain(Domain d) {
		return Response.ok(domain.ajouterDomain(d), MediaType.TEXT_PLAIN).build();
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/domain/ajouter
	// {
	// "name": "ched",
	// "type": "Technique"
	// }

	// @GET
@DELETE
	@Path("supprimer/{id}")
	@Produces( MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	public void deleteDomainById(@PathParam("id") int id) {
		domain.deleteDomainById(id);
		System.out.println("deleeeteeee");
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/test/domain/supprimer/12

	@GET
	@Path("{nom}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectById(@PathParam("nom") String name) {
		System.out.println("hahahha");
		return Response.ok(domain.finddomainbyname(name), MediaType.APPLICATION_JSON).build();
	}

	// http://localhost:18080/gestion-resources-humaine-web/activator/test/domain/Web

	// ***********************************************************************//
	// @DELETE
	// // @Secured
	// @Path("deleteMessage/{id}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response deleteMessageByID(@PathParam("id") int id) {
	//
	// if (messageService.deleteMessage(id))
	// return Response.ok().status(Response.Status.NO_CONTENT).build();
	// else
	// return Response.notModified().build();
	// }
}
