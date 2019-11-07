package managedBeans;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

//import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
//import org.primefaces.push.PushContext;
//import org.primefaces.push.PushContextFactory;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import utils.Address;
import entities.Administrator;
import entities.User;
import entities.User_log;
import interfaces.User_logServiceLocal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import services.AdminService;
import services.UserService;
import services.User_logService;
import utils.HeaderFooter;
import utils.KeyGenerator;
import utils.PDFCreator;
import utils.Status;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//h: tout les composant graphique
//f: toute les balise qui seron converti en code java 
//si 2 bean different , on appel le 1managed bean dans le 2eme grace a @Managedproperty loginBean lb; lb.employe.....
@ManagedBean
@SessionScoped
public class UserBean {
	  private static final String TITLE = "TestReport";//le nom du fichier qui sera sauvgarder dans le repertoire du projet
	    public static final String PDF_EXTENSION = ".pdf"; //l'extension du fichier
	    
	    static Properties mailServerProperties;
		static Session getMailSession;
		static MimeMessage generateMailMessage;
		
	    @Inject
	    private KeyGenerator keyGenerator;
	    @Context
	    private UriInfo uriInfo;

	    @Inject
	    private Logger logger;
	    
	    
//	    public static final String ACCOUNT_SID =        //twilit sms 
//	            "AC141c50f80a7ffdeedcef6af13ff3ab8e";
//	    public static final String AUTH_TOKEN =
//	            "ac30b36994289438750f61442493406a";
	    
	    

	private String performedBy; //pour le changement dynamique de la table suivant le role lors de l'ajout
	private String email; 
	private int test;
	private String login; 
	private String password;
	private String confirm_password;
	private User user;
	
	private User user_details;
	private Administrator temporaryAdmin;
	private Administrator admin_details;
	private boolean loggedln; 
	private List<User> listeUsers;
	private List<User> filteredUsers;
	private List<User_log> listeUser_logs;
	private String nom;
	private String prenom;
	private String cin;
	private Address address;
	private String picture;
	private String phone_Number;
	private String mapAddress;
	private double lat;
	private double lng;
	
	// default is false
	// injection de dependences 
	@EJB
	UserService userService;
	@EJB
	User_logService user_logService;
	@EJB
	AdminService adminService;
	
	
	

	public String getMapAddress() {
		return mapAddress;
	}


	public void setMapAddress(String mapAddress) {
		this.mapAddress = mapAddress;
	}


	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}


	public void onPointSelect(ActionEvent event) throws ApiException, InterruptedException, IOException {
		System.out.println("Mise a jour coordonnées MAP");
		FacesContext.getCurrentInstance().addMessage( "form:btn",new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Selected", "Lat:" + getLat() + ", Lng:" + getLng() + " Address: " + getMapAddress()));
//		String [] vals = getMapAddress().split(",");
//		String city = vals[vals.length - 2];
		
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyAjsIpyXFoB7_5_E2DgO_-CTDMHKM0cW4I").build();
		GeocodingApiRequest request = GeocodingApi.newRequest(context); 
		request.latlng(new LatLng( getLat(), getLng())); 
		//request.resultType(AddressType.STREET_ADDRESS); 

		GeocodingResult[] results = request.await(); // try catch?
		
		address=new Address();
		address.setLat(lat);
		address.setLng(lng);
		System.out.println("Avant boucle MAP");
		for(GeocodingResult r : results){
		    for (AddressComponent ac : r.addressComponents) { 
		        for (AddressComponentType acType : ac.types) {
		        	
		        	 if (acType == AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1)
		        	 {
		        		 address.setState(ac.longName);
		        		 System.out.println("STATE="+ac.longName);
		        	}
		        	 else if (acType == AddressComponentType.STREET_NUMBER)
		        	 {
		        		 address.setAddressLine1(ac.longName);
		        		 System.out.println("N maison="+ac.longName);
		        	}
		              else if (acType == AddressComponentType.LOCALITY) 
		              { 
		            	address.setCity(ac.longName);
		                System.out.println("CITY="+ac.longName);
		                
		            } 
		              else if (acType == AddressComponentType.ROUTE) 
		              {
		            	address.setAddressLine2(ac.longName);
		               System.out.println("RUE="+ac.longName); 
		            } 
		              else if (acType == AddressComponentType.COUNTRY) 
		              {
		            	address.setCountry(ac.longName);
		               System.out.println("COUNTRY="+ac.longName); 
		            } 
		              else if (acType == AddressComponentType.POSTAL_CODE) 
		              {
		            	address.setZipCode(ac.longName);
			               System.out.println("CODE POSTAL="+ac.longName); 
			            }
		        }
		    }
		}
   	 System.out.println("Address="+address);

		
	    }
	
	

	
	
	
	
	//******************************************Upload file
	private Part uploadedFile;
	private String folder = System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads";

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


	//*****************************************************************************
	
	
	
	
	
	
	public void updateUser()
	{
		String fileName=null;
		if(uploadedFile!=null)
		{
		try (InputStream input = uploadedFile.getInputStream()) {
		    fileName = uploadedFile.getSubmittedFileName();
			Files.copy(input, new File(folder, fileName).toPath());
			admin_details.setPicture(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		userService.updateUser(user_details);
		adminService.updateAdmin(admin_details);

		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String voirCompte(){
		if (temporaryAdmin!=null)
			admin_details=temporaryAdmin;
		
		
		if (user.getClass().getSimpleName().equals("User"))
			return  "/pages/user/details_user?faces-redirect=true";
		
			else if (user.getClass().getSimpleName().equals("Administrator"))
				return  "/pages/admin/details_user?faces-redirect=true";
			else if (user.getClass().getSimpleName().equals("Candidate"))
				return  "/pages/candidate/details_user?faces-redirect=true";
			else if (user.getClass().getSimpleName().equals("Employe"))
				return  "/pages/employe/details_user?faces-redirect=true";
		
		return ""  ;
	}
	
	
	public String voiUserDetails(User userd ){
		
		user_details=userd;
		System.out.println("userd.getClass().getSimpleName()="+userd.getClass().getSimpleName());
		if(userd.getClass().getSimpleName().equals("Administrator"))
		{
			temporaryAdmin=admin_details;
			
			admin_details=(Administrator)userd;
			System.out.println("temporaryAdmin="+temporaryAdmin);
			System.out.println("admin_details="+admin_details);
		}
		
		if (userd.getClass().getSimpleName().equals("User"))
			return  "/pages/user/details_user?faces-redirect=true";
		
			else if (userd.getClass().getSimpleName().equals("Administrator"))
				return  "/pages/admin/details_user?faces-redirect=true";
			else if (userd.getClass().getSimpleName().equals("Candidate"))
				return  "/pages/candidate/details_user?faces-redirect=true";
			else if (userd.getClass().getSimpleName().equals("Employe"))
				return  "/pages/employe/details_user?faces-redirect=true";
		
		return ""  ;
	}
	
	public List<User_log> getListeUser_logsByUserID(int id) {
		return user_logService.getListeUser_logsByUserID(id);
	}

	public void setListeUser_logsByUserID(List<User_log> listeUser_logs) {
		this.listeUser_logs = listeUser_logs;
	}

	
	
	public List<User> updateListeUsers() {
		listeUsers=userService.findAllUsers();
		System.out.println(listeUsers);
		return listeUsers;
	}
	
	public void removeUser(int id) {
		userService.deleteUser(id);
	}
	
	
	
	public List<User> getListeUsers() {
		listeUsers=userService.findAllUsers();
		return listeUsers;
	}

	

	public void UserRegister() throws MessagingException, IOException {
		//String navigateTo = "null";
		System.out.println("User Register");
		String fileName=null;
		if (uploadedFile!=null)
		{
		try (InputStream input = uploadedFile.getInputStream()) {
			 fileName = uploadedFile.getSubmittedFileName();
			Files.copy(input, new File(folder, fileName).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		boolean ajoutSuccess=false;

		System.out.println("performedBy.equals="+performedBy);
		
		if (performedBy.equals("Utilisateur"))
		{
			ajoutSuccess=userService.addUser(new User(email,login,password,new Date(),Status.pending,phone_Number));
			System.out.println("Ajout user="+ajoutSuccess);
		}
		else if (performedBy.equals("Administrateur"))
		{
			ajoutSuccess=adminService.addAdmin(new Administrator(email,login,password,new Date(),Status.pending,phone_Number,nom,prenom,cin,address,fileName));
			System.out.println("Ajout admin="+ajoutSuccess);
		}	
		else if (performedBy.equals("Candidat"))
		{
			
		}	
		else if (performedBy.equals("Employe"))
		{
			
		}	
		
		
			if(ajoutSuccess)
			{
				
			
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("User successfully added"));
			//navigateTo = "/pages/admin/welcome?faces-redirect=true";
			//notificationPUSH();
			
			
			String token = issueToken(login);
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
			String emailBody = "Vous etes dés a present inscrit dans la base de donnée de neoxam, votre nom d'utilisateur est:"+login+" et votre mot de passe est:"+password +" , cependant votre compte doit etre activer veillez cliquer sur ce lien pour le faire avant 24h:"+
			"<a href=http://localhost:18080/gestion-resources-humaine-web/api/"+login+"/"+token+ ">Lien Activation</a>   http://localhost:18080/gestion-resources-humaine-web/api/"+login+"/"+token+ "<br><br> Cordialement, <br>Admin Neoxam";
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
	
		    

			}
		        
		        
		    
			
			
			
			
			
			
			
			
			
			
		else 
			{
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Adding user failed"));
			}
		//return navigateTo;
	}
	

	
//	public void notificationPUSH()
//	{
//		String summary="Nuevo Elemento";
//		String detail="Ajout a la liste";
//		String CHANNEL="/notify";
//		
//		EventBus eventBus = EventBusFactory.getDefault().eventBus();
//		eventBus.publish(CHANNEL,new FacesMessage(StringEscapeUtils.escapeHtml3(summary),StringEscapeUtils.escapeHtml3(detail)));
//		
//	}
	
	
	
	


    
	private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer("http://localhost:18080/gestion-resources-humaine-web/activator/login")
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }
	
	 private Date toDate(LocalDateTime localDateTime) {
	        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int checkIfEmailAndLoginConstraint() {
		int result =userService.checkIfEmailOrLoginExist( email, login);
		
		RequestContext reqCtx = RequestContext.getCurrentInstance(); 
		       
		reqCtx.addCallbackParam("returnedValue", result);

		return result;
	}
	
	
	

	
	public void createUserLogPDF(int id) throws IOException
	{
		

        Document document = null;
        try {
        //Document is not auto-closable hence need to close it separately
    	    List<User_log> dataObjList = user_logService.getListeUser_logsByUserID(id);
    	    System.out.println(dataObjList);
            document = new Document(PageSize.A4);            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
                    new File(TITLE + PDF_EXTENSION)));
            HeaderFooter event = new HeaderFooter();
            event.setHeader("Test Report");
            writer.setPageEvent(event);
            document.open();
            PDFCreator.addMetaData(document, TITLE);
            PDFCreator.addTitlePage(document, TITLE);
            PDFCreator.addContent(document, dataObjList);
            System.out.println();
        }catch (DocumentException | FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("FileNotFoundException occurs.." + e.getMessage());
        }finally{
            if(null != document){
                document.close();
            }
        }
        
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("*****************************************Current relative path is: " + s);
        //File file = new File("C:\\Users\\X\\devstudio\\wildfly-9.0.1.Final2\\bin\\TestReport.pdf");
        System.out.println("System.getProperty('jboss.home.dir')="+System.getProperty("jboss.home.dir"));
        File file = new File(System.getProperty("jboss.home.dir")+"\\bin\\TestReport.pdf");

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletResponse response = 
                (HttpServletResponse) facesContext.getExternalContext().getResponse();

        response.reset();   // Reset the response in the first place
        response.setHeader("Content-Type", "application/pdf");

        OutputStream responseOutputStream = response.getOutputStream();

        InputStream fileInputStream = new FileInputStream(file);

        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) 
        {
            responseOutputStream.write(bytesBuffer, 0, bytesRead);
        }

        responseOutputStream.flush();

        fileInputStream.close();
        responseOutputStream.close();

        facesContext.responseComplete();
        
        
        
		
	}
	
	
	
	
	
	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public String getLogin() {
		return login;
	}



	public void setLogin(String login) {
		this.login = login;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public boolean isLoggedln() {
		return loggedln;
	}



	public void setLoggedln(boolean loggedln) {
		this.loggedln = loggedln;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getConfirm_password() {
		return confirm_password;
	}





	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	public User getUser_details() {
		return user_details;
	}

	public void setUser_details(User user_details) {
		this.user_details = user_details;
	}


	public String getPerformedBy() {
		return performedBy;
	}


	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getCin() {
		return cin;
	}


	public void setCin(String cin) {
		this.cin = cin;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getPhone_Number() {
		return phone_Number;
	}


	public void setPhone_Number(String phone_Number) {
		this.phone_Number = phone_Number;
	}


	public Administrator getAdmin_details() {
		return admin_details;
	}


	public void setAdmin_details(Administrator admin_details) {
		this.admin_details = admin_details;
	}
	
	
	
	
	
}
