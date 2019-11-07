package resources;
//package services;
//
//
//
//import utils.Secured;
//
//import javax.ejb.EJB;
//import javax.inject.Inject;
//import javax.ws.rs.CookieParam;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Cookie;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.logging.Logger;
//
//import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
//
///**
// * @author Antonio Goncalves
// *         http://www.antoniogoncalves.org
// *         --
// */
//@Path("/")
////@Produces(TEXT_PLAIN)
//public class EchoEndpoint {
//
//    // ======================================
//    // =          Injection Points          =
//    // ======================================
//
//    @Inject
//    private Logger logger;
//    @EJB
//    UserService userService;
//
//    // ======================================
//    // =          Business methods          =
//    // ======================================
//
////    @GET
////    @Secured
////    public Response echo(@QueryParam("message") String message) {
////        return Response.ok().entity(message == null ? "no message" : message).build();
////    }
//
//
//    
//    @GET
//    //@Secured
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response echo() {
//        return Response.ok(userService.findAllUsers()).build();
//    }
//    
  
//    
//    
//    
//    
//    @POST
//    @Secured
//    public Response echoWithJWTToken2(@QueryParam("message") String message) {
//        return Response.ok().entity(message == null ? "no message" : message).build();
//    }
//    
//    
//    
//    @GET
//    @Path("/foo")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response foo(@CookieParam("jwt") Cookie cookie) {
//        if (cookie == null) {
//            return Response.serverError().entity("ERROR").build();
//        } else {
//            return Response.ok(cookie.getValue()).build();
//        }
//    }
//    
//    
//    
//}