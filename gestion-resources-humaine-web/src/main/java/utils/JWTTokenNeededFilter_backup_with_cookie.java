//package utils;
//
//import io.jsonwebtoken.Jwts;
//import managedBeans.LoginBean;
//import utils.KeyGenerator;
//
//import javax.annotation.Priority;
//import javax.ejb.EJB;
//import javax.inject.Inject;
//import javax.servlet.annotation.WebFilter;
//import javax.ws.rs.NotAuthorizedException;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//import java.security.Key;
//import java.util.logging.Logger;
//
///**
// * @author Antonio Goncalves
// *         http://www.antoniogoncalves.org
// *         --
// */
//@Provider
//@Secured
//@Priority(Priorities.AUTHENTICATION)
//
//public class JWTTokenNeededFilter implements ContainerRequestFilter {
//	
//    // ======================================
//    // =          Injection Points          =
//    // ======================================
//
//    @Inject
//    private Logger logger;
//
//    @Inject
//    private KeyGenerator keyGenerator;
//
//    // ======================================
//    // =          Business methods          =
//    // ======================================
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//
//    	
//    	System.out.println("cookie="+requestContext.getCookies());
//    	String authorizationCookie=requestContext.getCookies().get("jwt").toString();
//    	System.out.println("authorizationCookie="+authorizationCookie);
//    	
//    	
//    	
//        // Get the HTTP Authorization header from the request
//        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//        logger.info("#### authorizationHeader : " + authorizationHeader);
//
//        // Check if the HTTP Authorization header is present and formatted correctly
////        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
////            logger.severe("#### invalid authorizationHeader : " + authorizationHeader);
////            throw new NotAuthorizedException("Authorization header must be provided");
////        }
//
//        // Extract the token from the HTTP Authorization header
//        //String token = authorizationHeader.substring("Bearer".length()).trim();
//        String token = authorizationCookie.substring("jwt=".length()).trim();
//        System.out.println("extracted token="+token);
//        try {
//
//            // Validate the token
//            Key key = keyGenerator.generateKey();
//            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
//            logger.info("#### valid token : " + token);
//
//        } catch (Exception e) {
//            logger.severe("#### invalid token : " + token);
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}