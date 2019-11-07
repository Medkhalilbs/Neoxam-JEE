package resources;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entities.User_log;
import services.User_logService;

@Path("/adnene/user_log")// http://localhost:18080/gestion-resources-humaine-web/api/adnene/user_log
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@Secured
public class User_logResources {

	// ======================================
	// = Injection Points =
	// ======================================
	@Context
	private UriInfo uriInfo;
	@EJB
	User_logService user_logService;


	// ======================================
	// = Business methods =
	// ======================================

	
	@GET
	@Path("/getUser_Logs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser_Logs() {
		System.out.println("web service user_log executé");
		List<User_log> User_log = user_logService.findAllUser_logs();
		if (User_log != null)
			return Response.ok(User_log).build();
		else
			return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("getUser_log/{id}")
	public Response getUser_logByID(@PathParam("id") int id) {
		User_log user_log = user_logService.findUser_logById(id);
		if (user_log == null)
			return Response.status(NOT_FOUND).build();
		else
			return Response.ok(user_log).build();
	}

	@GET
	@Path("getUser_logsByUserID/{id}")
	public Response getUser_logsByUserID(@PathParam("id") int id) {
//		System.out.println("looking for user_log of user_id="+id);
		List<User_log> user_logsOfUserID = user_logService.getListeUser_logsByUserID(id);
		if (user_logsOfUserID == null)
			return Response.status(NOT_FOUND).build();
		else
			return Response.ok(user_logsOfUserID).build();
	}
	
	@POST
	@Path("/addUser_log")
	public Response addUser_log(User_log user_log) {
		System.out.println("JSON user_log=" + user_log);

		if (user_logService.addUser_log(user_log))
			return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user_log.getId())).build()).build();// retourne dans postman header dans Location →http://localhost:18080/gestion-resources-humaine-web/activator/addMessage/6
		else
			return Response.notModified().build();

	}
	

	
	

	
	

}