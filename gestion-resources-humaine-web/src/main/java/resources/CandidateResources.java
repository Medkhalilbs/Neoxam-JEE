package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpRequest;

import entities.Candidate;
import okhttp3.RequestBody;
import services.CandidateService;


@Path("/candidate")
public class CandidateResources {
	
	@EJB
	CandidateService candidateService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Candidate candidate) {
		System.out.println("ADD METHOD STARTED");
		if (candidateService.addCandidate(candidate))
			return Response.status(Status.CREATED).entity(candidate).build();
		else
			return Response.status(Status.NOT_IMPLEMENTED).entity("not Created").build();

	}
	
	@DELETE
	//@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam(value="id")String id) {

		//Request request = Request.this ;
		//System.out.println(Request.);
		if(id != null){
			if (candidateService.deleteCandidateWithId( Integer.valueOf(id) ) )
				return Response.status(Status.OK).entity("Deleted candidate " + id ).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Candidate with id "+id+" NOT FOUND").build();
		}else{			
			return Response.status(Response.Status.NOT_FOUND).entity("Candidate id NOT FOUND").build();
		}

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Candidate candidate) {
		System.out.println("PUT METHOD STARTED");
		if (candidateService.updateCandidate(candidate)) {
			return Response.status(Status.OK).entity(candidate).build();
		} else {

			return Response.status(Status.BAD_REQUEST).entity("Updating Candidate with id "+candidate.getId()+" =>  NOT FOUND").build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam(value="id")String id) {
		if(id == null){
			List<Candidate> listeCandidate = candidateService.findAllCandidate();
			if (listeCandidate != null)
				return Response.ok(listeCandidate).build();
			else
				return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			Candidate c = candidateService.findCandidateById(Integer.valueOf(id));
			if (c != null) {
				return Response.status(Status.OK).entity(c).build();
			}else{
				return Response.status(Status.NOT_FOUND).entity("Candidated with id "+id+" NOT FOUND").build();
			}
		}
	}	
}
