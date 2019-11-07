package resources;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entities.Message;
import services.MessageService;

@Path("/adnene/message")// http://localhost:18080/gestion-resources-humaine-web/api/adnene/message
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@Secured
public class MessageResources {

	// ======================================
	// = Injection Points =
	// ======================================
	@Context
	private UriInfo uriInfo;
	@EJB
	MessageService messageService;

	// ======================================
	// = Business methods =
	// ======================================

	
	@GET
	@Path("/getMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessages() {
		System.out.println("web service getMessages executé");
		List<Message> listeMessages = messageService.findAllMessages();
		if (listeMessages != null)
			return Response.ok(listeMessages).build();
		else
			return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("getMessage/{id}")
	public Response getMessageByID(@PathParam("id") int id) {
		System.out.println("web service getMessageByID executé");
		Message message = messageService.findMessageById(id);
		
		if (message.getDateLecture()==null)
		{message.setDateLecture(new Date());
		messageService.updateMessage(message);}
		
		if (message == null)
			return Response.status(NOT_FOUND).build();
		else
			return Response.ok(message).build();
	}

	@POST
	@Path("/addMessage")
	public Response addMessage(Message message) {
		System.out.println("JSON ADD MESSAGE=" + message);
		message.setDateEnvoie(new Date());
		if (messageService.addMessage(message))
			return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(message.getId())).build()).build();// retourne dans postman header dans Location →http://localhost:18080/gestion-resources-humaine-web/activator/addMessage/6
		else
			return Response.notModified().build();

	}
	
		@PUT
	 	@Path("/updateMessage")
	    public Response updateMessage( Message message) {
			System.out.println("web service updateMessage executé");
			Message messageOld=messageService.findMessageById(message.getId());
			message.setDateEnvoie(messageOld.getDateEnvoie());
			message.setDateLecture(messageOld.getDateLecture());
			
	        if (messageService.updateMessage(message)) {
	            return Response.ok().status(Response.Status.ACCEPTED).build();
	        } else {
	            return Response.notModified().build();
	        }           
	    }
	
	
		@DELETE
		@Path("deleteMessage/{id}")
		public Response deleteMessageByID(@PathParam("id") int id) {
			boolean resultat=messageService.deleteMessage(id);
			System.out.println("web service deleteMessageByID("+id+") executé avec resultat="+resultat);
			
			if (resultat)
				  return Response.ok().status(Response.Status.ACCEPTED).build();
			else
				return Response.notModified().build();
		}
	
	
	

}