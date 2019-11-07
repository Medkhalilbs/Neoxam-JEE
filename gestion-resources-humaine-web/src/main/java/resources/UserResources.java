package resources;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Administrator;
import entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import services.AdminService;
import services.UserService;
import smile.clustering.HierarchicalClustering;
import smile.clustering.linkage.CompleteLinkage;
import smile.clustering.linkage.SingleLinkage;
import smile.clustering.linkage.UPGMALinkage;
import smile.clustering.linkage.UPGMCLinkage;
import smile.clustering.linkage.WPGMALinkage;
import smile.clustering.linkage.WPGMCLinkage;
import smile.clustering.linkage.WardLinkage;
import smile.math.Math;
import smile.plot.Dendrogram;
import smile.plot.Palette;
import smile.plot.PlotCanvas;
import utils.Address;
import utils.ClusteringDemo;
import utils.HierarchicalClusteringDemo;
import utils.KeyGenerator;
import utils.Secured;

@Path("/adnene/user")// http://localhost:18080/gestion-resources-humaine-web/api/adnene/user
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@Secured
public class UserResources {
    static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	// ======================================
	// = Injection Points =
	// ======================================
	@Context
	private UriInfo uriInfo;
	@EJB
	UserService userService;
	@EJB
	AdminService adminService;
	// ======================================
	// = Business methods =
	// ======================================
	ObjectMapper mapper = new ObjectMapper();
    @Inject
    private KeyGenerator keyGenerator;
    static double[][] dataset = null;
    static List<String> colors = Arrays.asList("#000000", "#00FF00", "#0000FF", "#FF0000", "#01FFFE", "#FFA6FE", "#FFDB66", "#006401", "#010067", "#95003A", "#007DB5", "#FF00F6", "#FFEEE8", "#774D00", "#90FB92", "#0076FF", "#D5FF00", "#FF937E", "#6A826C", "#FF029D", "#FE8900", "#7A4782", "#7E2DD2", "#85A900", "#FF0056", "#A42400", "#00AE7E", "#683D3B", "#BDC6FF", "#263400", "#BDD393", "#00B917", "#9E008E", "#001544", "#C28C9F", "#FF74A3", "#01D0FF", "#004754", "#E56FFE", "#788231", "#0E4CA1", "#91D0CB", "#BE9970", "#968AE8", "#BB8800", "#43002C", "#DEFF74", "#00FFC6", "#FFE502", "#620E00", "#008F9C", "#98FF52", "#7544B1", "#B500FF", "#00FF78", "#FF6E41", "#005F39", "#6B6882", "#5FAD4E", "#A75740", "#A5FFD2", "#FFB167", "#009BFF", "#E85EBE");
//    @Inject
//    private Logger logger;
    


	@GET
	//@Secured
	public Response getUsers()  {
		List<User> listeUsers = userService.findAllUsers();
		if (listeUsers!=null)
			return Response.ok(listeUsers).build();
		else
			return Response.status(Response.Status.NOT_FOUND).build();

	}

	@GET
	// @Secured
	@Path("/getUser")
	public Response getUserByID1(@QueryParam("id") int id) {
		User user = userService.findUserById(id);
		if (user != null)
			return Response.ok(user).build();
		else
			return Response.status(Response.Status.NOT_FOUND).build();
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/getUser?id=1

	@GET
	@Path("/getUser/{id}")
	public Response getUserByID2(@PathParam("id") int id) {
		User user = userService.findUserById(id);
		if (user == null)
			return Response.status(NOT_FOUND).build();
		else
			return Response.ok(user).build();
	}
	// http://localhost:18080/gestion-resources-humaine-web/activator/getUser/1


	
	
	
	@POST
	@Path("/addUser")
	public Response addUser(User user) {
		System.out.println("JSON user=" + user);
		String password=user.getPassword();
		if (userService.addUser(user))
			{
			String token = issueToken(user.getLogin());
			try{
			// Step1
			System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			System.out.println("Mail Server Properties have been setup successfully..");
	 
			// Step2
			System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("ladnene@yahoo.fr"));
			generateMailMessage.setSubject("Service inscription Neoxam");
			String emailBody = "Vous etes dés a present inscrit dans la base de donnée de neoxam, votre nom d'utilisateur est:"+user.getLogin()+" et votre mot de passe est:"+password +" , cependant votre compte doit etre activer veillez cliquer sur ce lien pour le faire avant 24h:"+
			"<a href=http://localhost:18080/gestion-resources-humaine-web/api/"+user.getLogin()+"/"+token+ ">Lien Activation</a>   http://localhost:18080/gestion-resources-humaine-web/api/"+user.getLogin()+"/"+token+ "<br><br> Cordialement, <br>Admin Neoxam";
			generateMailMessage.setContent(emailBody, "text/html");
			System.out.println("Mail Session has been created successfully..");
	 
			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
	 
			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "makerlab2018@gmail.com", "maker2018");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			}
			catch(Exception e)
			{
				System.out.println("exception mail:"+e.getMessage());
			}
			
			return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build()).build();// retourne dans postman header dans Location →http://localhost:18080/gestion-resources-humaine-web/api/adnene/addUser/6
		
			}
		else
			return Response.notModified().build();

	}
	
	
	
	//@Secured
	@POST
	@Path("/addAdministrator")
	public Response addAdministrator(Administrator admin) {
		String password=admin.getPassword();
		System.out.println("JSON admin=" + admin);
		admin.setRegistration_date(new Date());
		if (userService.addUser(admin))
		{
			String token = issueToken(admin.getLogin());
			try{
			// Step1
			System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			System.out.println("Mail Server Properties have been setup successfully..");
	 
			// Step2
			System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("ladnene@yahoo.fr"));
			generateMailMessage.setSubject("Service inscription Neoxam");
			String emailBody = "Vous etes dés a present inscrit dans la base de donnée de neoxam, votre nom d'utilisateur est:"+admin.getLogin()+" et votre mot de passe est:"+password +" , cependant votre compte doit etre activer veillez cliquer sur ce lien pour le faire avant 24h:"+
			"<a href=http://localhost:18080/gestion-resources-humaine-web/api/"+admin.getLogin()+"/"+token+ ">Lien Activation</a>    http://localhost:18080/gestion-resources-humaine-web/api/"+admin.getLogin()+"/"+token+ "<br><br> Cordialement, <br>Admin Neoxam";
			generateMailMessage.setContent(emailBody, "text/html");
			System.out.println("Mail Session has been created successfully..");
	 
			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
	 
			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "makerlab2018@gmail.com", "maker2018");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			}
			catch(Exception e)
			{
				System.out.println("exception mail:"+e.getMessage());
			}
			return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(admin.getId())).build()).build();// retourne dans postman header dans Location →http://localhost:18080/gestion-resources-humaine-web/api/adnene/addUser/6
		}
		else
			return Response.notModified().build();

	}
		@PUT
	 	@Path("/updateUser")
	    public Response updateUser(User user) {
			System.out.println("Update JSON user=" + user);
			User tempUser = userService.findUserById(user.getId());
			user.setRegistration_date(tempUser.getRegistration_date());
	        if (userService.updateUser(user)) {
	        	  return Response.ok().status(Response.Status.ACCEPTED).build();
	           // return Response.ok().status(Response.Status.NO_CONTENT).build();
	        } else {
	            return Response.notModified().build();
	        }           
	    }
		
		@PUT
	 	@Path("/updateAdmin")
	    public Response updateAdmin(Administrator admin) {
			System.out.println("Update JSON admin=" + admin);
			User tempUser = userService.findUserById(admin.getId());
			admin.setRegistration_date(tempUser.getRegistration_date());
			
	        if (userService.updateUser(admin)) {
	        	  return Response.ok().status(Response.Status.ACCEPTED).build();
	           // return Response.ok().status(Response.Status.NO_CONTENT).build();
	        } else {
	            return Response.notModified().build();
	        }           
	    }
		
		
		
	
	
		@DELETE
		@Path("/deleteUser/{id}")
		public Response deleteUserByID(@PathParam("id") int id) {
			
			if (userService.deleteUser(id))
				  return Response.ok().status(Response.Status.ACCEPTED).build();
				// return Response.ok().status(Response.Status.NO_CONTENT).build();
			else
				return Response.notModified().build();
		}
	
		@GET
		@Path("/getUserByEmailAndPassword/{email}/{password}")
		public Response rechercherUserByMailAndPassword(@PathParam("email") String email,@PathParam("password") String password) {
			
			User user=userService.getUserByEmailAndPassword(email, password);
			if (user!=null)
			{
				System.out.println("rechercherUserByMailAndPassword executé avec success: userEmail="+email+"/userPassword="+password);
				  return Response.ok(user).build();
			}
			else
			{
				System.out.println("rechercherUserByMailAndPassword executé echec: userEmail="+email+"/userPassword="+password);
				return Response.noContent().build();
			}
		}	
		
		
		@GET
		@Path("/getUserByEmail/{email}")
		public Response rechercherUserByMail(@PathParam("email") String email) {
			
			User user=userService.getUserByEmail(email);
			if (user!=null)
			{
				System.out.println("rechercherUserByMail executé avec success: userEmail="+email);
				  return Response.ok(user).build();
			}
			else
			{
				System.out.println("rechercherUserByMail executé echec: userEmail="+email);
				return Response.noContent().build();
			}
		}
		
		
		@GET
		@Path("/getUserByLogin/{login}")
		public Response rechercherUserByLogin(@PathParam("login") String login) {
			
			User user=userService.getUserByLogin(login);
			if (user!=null)
			{
				System.out.println("rechercherUserByLogin executé avec success: userLogin="+login);
				  return Response.ok(user).build();
			}
			else
			{
				System.out.println("rechercherUserByLogin executé echec: userLogin="+login);
				return Response.noContent().build();
			}
		}
	

		
		private String issueToken(String login) {
	        Key key = keyGenerator.generateKey();
	        String jwtToken = Jwts.builder()
	                .setSubject(login)
	                .setIssuer("http://localhost:18080/gestion-resources-humaine-web/activator/login")
	                .setIssuedAt(new Date())
	                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
	                .signWith(SignatureAlgorithm.HS512, key)
	                .compact();
	        //logger.info("#### generating token for a key : " + jwtToken + " - " + key);
	        System.out.println("#### generating token for a key : " + jwtToken + " - " + key);
	        return jwtToken;

	    }
		 private Date toDate(LocalDateTime localDateTime) {
		        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		    }
		 
		 
		 
		 
		 
		 
		 
			@GET
			@Path("/getMarkersListe/{clusterNumber}/{typeAlgo}/{vwidth}/{vheight}")
			public Response getMarkersListe(@PathParam("clusterNumber") int clusterNumber,@PathParam("typeAlgo") String typeAlgo,@PathParam("vwidth") int vwidth,@PathParam("vheight") int vheight) {
				if (clusterNumber==0)
					clusterNumber=39;
				if(vwidth==0||vheight==0)
				{
					vwidth=1920;
					vheight=1080;
				}

				
		        
				
				System.out.println("clusterNumber="+clusterNumber+" / typeAlgo="+typeAlgo);
				 double[][] cluster=null;
				List<Administrator> listeUsers = adminService.findAllAdmins();
				
				dataset = new double[listeUsers.size()][2];
				
				for (int i=0;i<listeUsers.size();i++)
				{	//System.out.println("user("+i+")="+listeUsers.get(i));
					Address adresse=listeUsers.get(i).getAddress();
				if(adresse!=null)
					{
					dataset[i][0]=adresse.getLat();
					dataset[i][1]=adresse.getLng();
					}
				}
				
				System.out.println("dataset deepToString="+Arrays.deepToString(dataset));
				System.out.println("dataset toString="+Arrays.toString(dataset));

			
				
				
				 int n = dataset.length;
				 System.out.println("taille dataset=dataset.length="+n);
			        double[][] proximity = new double[n][];
			        for (int i = 0; i < n; i++) {
			            proximity[i] = new double[i+1];
			            for (int j = 0; j < i; j++)
			                {
			            	//proximity[i][j] = Math.distance(dataset[i], dataset[j]);
			            	proximity[i][j] = distance(dataset[i][0], dataset[j][0],dataset[i][1],dataset[j][1],0,0);
			            //System.out.println("distance entre " +listeUsers.get(i).getLogin()+"/"+ listeUsers.get(j).getLogin()+"("+dataset[i][0]+","+dataset[i][1]+") et "+"("+dataset[j][0]+","+dataset[j][1]+")="+proximity[i][j]);
			            }
			        }
			        
			        
			        System.out.println("proximity="+proximity);
			        
			        HierarchicalClustering hac = null;
			        String hclustRmethod="";
//"ward.D", "ward.D2", "single", "complete", "average" (= UPGMA), "mcquitty" (= WPGMA), "median" (= WPGMC) or "centroid" (= UPGMC)		
			        
			        if(typeAlgo.equals("Single"))
			        	{hac = new HierarchicalClustering(new SingleLinkage(proximity));
			        	hclustRmethod="single";}
			        else if(typeAlgo.equals("Complete"))
			        	{hac = new HierarchicalClustering(new CompleteLinkage(proximity));
			        	hclustRmethod="complete";}
			        else if(typeAlgo.equals("UPGMA"))
					    {hac = new HierarchicalClustering(new UPGMALinkage(proximity));
		        		hclustRmethod="average";}
			        else if(typeAlgo.equals("WPGMA"))
					    {hac = new HierarchicalClustering(new WPGMALinkage(proximity));
			        	hclustRmethod="mcquitty";}
			        else if(typeAlgo.equals("UPGMC"))
					    {hac = new HierarchicalClustering(new UPGMCLinkage(proximity));
	        			hclustRmethod="centroid";}
			        else if(typeAlgo.equals("WPGMC"))
					    {hac = new HierarchicalClustering(new WPGMCLinkage(proximity));
	        			hclustRmethod="median";}
			        else if(typeAlgo.equals("Ward"))
					    {hac = new HierarchicalClustering(new WardLinkage(proximity));
	        			hclustRmethod="ward";}
			        
//*****************************************************************************************************************
			        //Rserve()
					try {
						//Runtime.getRuntime().exec("C:\\Program Files\\RStudio\\bin\\rstudio.exe C:\\Users\\X\\Desktop\\rscript.R");
						RConnection c = new RConnection("localhost", 6311);
						System.out.println(c.eval("R.version.string").asString());
						//Rengine re = new Rengine (new String[]{"--no-save"}, false, null); 
						if(c.isConnected()) {
				            System.out.println("Connected to RServe.");
				            if(c.needLogin()) {
				                System.out.println("Providing Login");
				                c.login("username", "password");
				            }
				            
				            c.eval("vwidth="+vwidth);
				            c.eval("vheight="+vheight);
				            System.out.println("typeAlgo="+'"'+hclustRmethod+'"');
				            c.eval("typeAlgo="+'"'+hclustRmethod+'"');
				            
				            
				            REXP x;
				            System.out.println("Reading script...");
				            File file = new File("C:\\rscript.R");
				            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				                for(String line; (line = br.readLine()) != null; ) {
				                    System.out.println(line);
				                    x = c.eval(line);         // evaluates line in R
				                    System.out.println(x);    // prints result
				                }
				            } catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

				        } else {
				            System.out.println("Rserve could not connect");
				        }

				        c.close();
				        System.out.println("Session Closed");
			        } catch ( RserveException | REXPMismatchException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					
//*********************************************************************************************************************************			        
//			        JPanel pane = new JPanel();
//			        PlotCanvas plot = Dendrogram.plot("Dendrogram", hac.getTree(), hac.getHeight());
//			        plot.setTitle("Dendrogram");


			        
//			        plot.setSize(new Dimension(1000, 1000));
//			        try {
//						plot.save(new File(file));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
////			        pane.add(plot);
////			        createImage(pane);
			        
			        int[] membership = hac.partition(clusterNumber);
			        
			        System.out.println("membership="+membership);
			        
//			        int[] clusterSize = new int[clusterNumber];
//			        for (int i = 0; i < membership.length; i++) {
//			            clusterSize[membership[i]]++;
//			        }
//			        
			        
			        for (int k = 0; k < clusterNumber; k++) 
			        {
			            //cluster = new double[clusterSize[k]][];
			            
			            if(clusterNumber<=64)
			            
			            	for (int i = 0, j = 0; i < dataset.length; i++) 
			            
					            {
					                if (membership[i] == k) {
					                    //cluster[j++] = dataset[i];
					                    listeUsers.get(i).getAddress().setMarkerColor(colors.get(k));
					                    
					                }
					            }
			               
			            else 
			            	for (int x = 0, z = 0; x < dataset.length; x++) 
			            		{
					                if (membership[x] == k) 
					                {
					                    //cluster[z++] = dataset[x];
					                    //Color couleur =  Color.getHSBColor(k / clusterNumber, 1, 1); 
					                    listeUsers.get(x).getAddress().setMarkerColor("#"+Integer.toHexString( Palette.COLORS[k % Palette.COLORS.length].getRGB()).substring(2));
					                    //System.out.println("*********************************************************************k % Palette.COLORS.length="+k+"%"+Palette.COLORS.length+"="+(k % Palette.COLORS.length)  );
					                   // System.out.print("*********************************************************************k="+k+"/ k%40="+(k % Palette.COLORS.length)+"/Palette.COLORS[k % Palette.COLORS.length ]="+Palette.COLORS[k % Palette.COLORS.length ]+"/ couleur="+"#"+Integer.toHexString( Palette.COLORS[k % Palette.COLORS.length ].getRGB()).substring(2));
					                    //System.out.println( " / Palette.COLORS[k % Palette.COLORS.length ].getRGB()="+Palette.COLORS[k % Palette.COLORS.length ].getRGB()+ " / login=" +listeUsers.get(i).getLogin());
					                    
					                } 	
			            		}
			            
//			            plot.points(cluster, pointLegend, Palette.COLORS[k % Palette.COLORS.length]);
//			            System.out.println("color code="+"#"+Integer.toHexString( Palette.COLORS[k % Palette.COLORS.length].getRGB()).substring(2));

			        }
			        
			        
			        

				
//				 ClusteringDemo demo = new HierarchicalClusteringDemo();
//			        JFrame f = new JFrame("Agglomerative Hierarchical Clustering");
//			        f.setSize(new Dimension(1000, 1000));
//			        f.setLocationRelativeTo(null);
//			        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			        f.getContentPane().add(demo);
//			        f.setVisible(true);
			        
			        
			        String folder=System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\";
			        try {
						
			        
			        	
			        	Files.copy(new File("C:\\Users\\X\\Documents", "Plot.png").toPath(), new File(folder, "Plot.png").toPath(),StandardCopyOption.REPLACE_EXISTING);

						Files.copy(new File("C:\\", "Plot.png").toPath(), new File(folder, "Plot.png").toPath(),StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						e.printStackTrace();
					}
			        
			        
			        
			        System.out.println("complete");
			        
					  return Response.ok(listeUsers).build();
				
			}	 
		 
		 
		 
		 
		 
			public static double distance(double lat1, double lat2, double lon1,
			        double lon2, double el1, double el2) {

			    final int R = 6371; // Radius of the earth

			    double latDistance = Math.toRadians(lat2 - lat1);
			    double lonDistance = Math.toRadians(lon2 - lon1);
			    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			    double distance = R * c * 1000; // convert to meters

			    double height = el1 - el2;

			    distance = Math.pow(distance, 2) + Math.pow(height, 2);

			    return Math.sqrt(distance);
			}
		 
		 
			public BufferedImage createImage(JPanel panel) {
				
				String folder = System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\dendrogramme.png";
			    System.out.println("dendrogramme upload folder="+folder);
//				int w = panel.getWidth();
//			    int h = panel.getHeight();
			    
			    BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
			    Graphics2D g = image.createGraphics();
			    panel.print(g);
			    g.dispose();
			    try { 
			    	
			        ImageIO.write(image, "png", new File(folder)); 
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    
			    
			    
			    return image;
			}
		 
		 
}