package managedBeans;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import entities.Administrator;
import entities.User;
import entities.User_log;
import interfaces.User_logServiceLocal;
import services.AdminService;
import services.UserService;
import services.User_logService;
import utils.Address;
import utils.JSessionStaticID;
import utils.Status;


@ManagedBean
//@RequestScoped
@SessionScoped
public class LoginBean {
    public static final String API_KEY = "6034d18f";
    public static final String API_SECRET = "z3HibStTdXLjkcc4";
	
	
//	@ManagedProperty("#{param.login}")
	private String login;

//	@ManagedProperty("#{param.password}")
	private String password;
	   
	
    @ManagedProperty(value="#{userBean}")//this is EL name of your bean 
    private UserBean injectedUserBean;
    
    
    
    
    

	public UserBean getInjectedUserBean() {
		return injectedUserBean;
	}



	public void setInjectedUserBean(UserBean injectedUserBean) {
		this.injectedUserBean = injectedUserBean;
	}



	private User user;
	private boolean loggedln; 
	public boolean validToken=false;
	private String ip;
	private String country;
	// default is false
	// injection de dependences 
	@EJB
	UserService userService;
	@EJB
	AdminService adminService;
	@EJB
	User_logService user_logService;
	
	
	

	
	
	public String doLogin() throws IOException, NexmoClientException {
		String navigateTo = "null";
		System.out.println("Login de JSF dans bean="+login+" password="+password);
		user = userService.getUserByLoginAndPassword(login, password);
		
		injectedUserBean.setUser_details(user);//au debut user_details recoi le user comme ca si user navigue il se voit , si admin navigue il voit le compte du user sur le qu'elle il a appuiyé voir detail dans le tableau
		injectedUserBean.setUser(user);
		if (user!=null && user.getClass().getSimpleName().equals("Administrator"))
		injectedUserBean.setAdmin_details((Administrator)user);  
		
		System.out.println(user);
		//if (user != null && user.getClass().getName().equals("admin") ) 
		if (user != null && user.getStatus()==Status.active ) 
	
			{
//			URL whatismyip = new URL("http://checkip.amazonaws.com");
//			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
//	
//			String ip = in.readLine(); //you get the IP as a String
			System.out.println("ip="+ip);
			System.out.println("country="+country);
			User_log last_user_log=user_logService.getLastUser_Log(user.getId());
			System.out.println("last user_log in DB du user id="+user.getId()+" ="+last_user_log);
			
			if(last_user_log!=null && !last_user_log.getIp_address().equals(ip))
			{
				
				AuthMethod auth = new TokenAuthMethod(API_KEY, API_SECRET);
				NexmoClient client = new NexmoClient(auth);
				try{
				SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(
					new TextMessage(
				        "Neoxam",
				        "21625435089",
				        "Avertissement: quelqu'un s'est connecté à votre compte depuis cette ip:"+ip+"("+country+")"
				    )
				);
					
				for (SmsSubmissionResult response : responses) {
				    System.out.println(response);
				}
				}
				catch(Exception e)
				{
					System.out.println("Exception nexmo="+e.getMessage());
				}
			}
				
			
			//InetAddress ip=InetAddress.getByName(ip);
			User_log user_log=new User_log(new Date(),JSessionStaticID.pc_id,JSessionStaticID.id,ip,user,country);
			user_logService.addUser_log(user_log);
			System.out.println(user.getClass().getName());	
			loggedln = true;
			System.out.println("user.getClass().getSimpleName()="+user.getClass().getSimpleName());
			
			if (user.getClass().getSimpleName().equals("User"))
			navigateTo = "/pages/user/welcome?faces-redirect=true";
			else if (user.getClass().getSimpleName().equals("Administrator"))
			navigateTo = "/pages/admin/welcome?faces-redirect=true";
			else if (user.getClass().getSimpleName().equals("Candidate"))
			navigateTo = "/pages/candidate/welcome?faces-redirect=true";
			
			} 
		else if (user!=null && !(user.getStatus()==Status.active))
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Erreur","Veillez activer votre compte"));
		else 
			{
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Erreur","Veillez verifier vos données"));

	       
			}
		return navigateTo;
	}
	
	
	
	public String doLogout() 
		{
		User_log user_log =user_logService.getUser_LogForLogoutTimeSet(user.getId(), JSessionStaticID.id);
		user_log.setLogout_timestamp(new Date());
		user_logService.updateUser_log(user_log);
		
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession() ;
		return "/login?faces-redirect=true";
		}


	
	
	
	
	public void UserRegisterTest() throws MessagingException, IOException {

		if(adminService.addAdmin(new Administrator("ladneneX@yahoo.fr","ladneneX","123456Mm",new Date(),Status.active,"+21625435089", "Adnene", "LABIDI","123456789", new Address("35","Rue crepuscule","La ghazele,Ariana","Tunis","Tunisie","2083",36.8899779,10.181705999999963),"x.jpg")))
			{
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Admin successfully added"));
			}
		else 
			{
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Adding admin failed"));
			}
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



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
	
}
