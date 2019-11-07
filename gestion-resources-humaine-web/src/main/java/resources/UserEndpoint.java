package resources;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import services.UserService;
import utils.KeyGenerator;
import utils.PasswordUtils;
import utils.Secured;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import entities.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;


import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;


/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Path("/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class UserEndpoint {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @Context
    private UriInfo uriInfo;

    @Inject
    private Logger logger;

    @Inject
    private KeyGenerator keyGenerator;
    @EJB
	UserService userService;
    
    @PersistenceContext
    private EntityManager em;
	

    // ======================================
    // =          Business methods          =
    // ======================================

    
//    @GET
//    @Path("/add_user")
//    @Produces("text/html")
//    public Response foo(@Context HttpServletRequest request,@Context HttpServletResponse response) throws IOException
//    {
//    
//        	
//        	
//        	
//    	  {
//    		    String myJsfPage = "/index.html";
//    		    String contextPath = request.getContextPath();
//    		    response.sendRedirect(contextPath + myJsfPage);
//    		    return Response.status(Status.ACCEPTED).build();
//    		
//    }
//    }
    
    
    
    
    
    
    @GET
    @Path("/{id}/{token}")
    @Produces("text/html")
    public Response foo(@PathParam("id") String login,@PathParam("token") String token) throws IOException, URISyntaxException
    {

    	
    
        try {

            // Validate the token
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("#### valid token : " + token);
        	User user=userService.getUserByLogin(login);
        	user.setStatus(utils.Status.active);
        	userService.updateUser(user);

        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
           
        }
        return Response.temporaryRedirect(new URI("http://localhost:18080/gestion-resources-humaine-web/login.jsf")).build();

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    	  
//    	    @GET
//    	    @Path("/foo/hoo")
//    	    @Produces(MediaType.TEXT_PLAIN)
//    	    public Response foo(@CookieParam("jwt") Cookie cookie) {
//    	        if (cookie == null) {
//    	            return Response.serverError().entity("ERROR").build();
//    	        } else {
//    	            return Response.ok(cookie.getValue()).build();
//    	        }
//    	    }
    	  
    	  
    	  
    
    
    @GET
    @Path("/login/{login}/{password}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response authenticateUser(@PathParam("login") String login,@PathParam("password") String password) {

        try {

            logger.info("#### login/password : " + login + "/" + password);

            // Authenticate the user using the credentials provided
            //authenticate(login, password);

            // Issue a token for the user
            String token = issueToken(login);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
            //return Response.ok().cookie(new NewCookie("jwt", token)).entity(token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String login, String password) throws Exception {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
        query.setParameter("login", login);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        User user = query.getSingleResult();

        if (user == null)
            throw new SecurityException("Invalid user/password");
    }

    private String issueToken(String login) {
    	
    	System.out.println("uriInfo.getAbsolutePath().toString()="+uriInfo.getAbsolutePath().toString());
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }

//    @POST
//    public Response create(User user) {
//        em.persist(user);
//        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build()).build();
//    }

//    @GET
//    @Path("/{id}")
//    public Response findById(@PathParam("id") String id) {
//        User user = em.find(User.class,Integer.getInteger(id) );
//
//        if (user == null)
//            return Response.status(NOT_FOUND).build();
//
//        return Response.ok(user).build();
//    }

//    @GET
//    //@Secured
//    @Path("/listeUsers")
//    public Response findAllUsers() {
//        TypedQuery<User> query = em.createNamedQuery(User.FIND_ALL, User.class);
//        List<User> allUsers = query.getResultList();
//
//        if (allUsers == null)
//            return Response.status(NOT_FOUND).build();
//
//        return Response.ok(allUsers).build();
//    }

//    @DELETE
//    @Path("/{id}")
//    public Response remove(@PathParam("id") String id) {
//        em.remove(em.getReference(User.class, id));
//        return Response.noContent().build();
//    }

    // ======================================
    // =          Private methods           =
    // ======================================

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}