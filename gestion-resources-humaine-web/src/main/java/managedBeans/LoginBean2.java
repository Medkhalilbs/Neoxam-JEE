//package managedBeans;
//
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.Date;
//
//import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ManagedBean;
//import entities.User;
//import entities.User_log;
//import interfaces.User_logServiceLocal;
//import services.UserService;
//import services.User_logService;
//
//
//@ManagedBean
//@SessionScoped
//public class LoginBean2 {
//	   
//	private String login; 
//	private String password; 
//	private User user;
//	private boolean loggedln; 
//	public boolean validToken=false;
//	// default is false
//	// injection de dependences 
//	@EJB
//	UserService userService;
//	@EJB
//	User_logServiceLocal user_logService;
//	
//	public String doLogin() throws IOException {
//		String navigateTo = "null";
//		user = userService.getUserByEmailAndPassword(login, password);
//		System.out.println(user);
//		//if (user != null && user.getClass().getName().equals("admin") ) 
//		if (user != null  ) 
//	
//			{
//			URL whatismyip = new URL("http://checkip.amazonaws.com");
//			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
//	
//			String ip = in.readLine(); //you get the IP as a String
//			System.out.println(ip);
//			
//			
//			//InetAddress ip=InetAddress.getByName(ip);
//			User_log user_log=new User_log(new Date(),"pc_id=adnene","session_id=azeqzdq",ip,user);
//			user_logService.addUser_log(user_log);
//			System.out.println(user.getClass().getName());	
//			navigateTo = "/pages/admin/welcome?faces-redirect=true";
//			loggedln = true;
//			} 
//		else 
//			{
//			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("bad credentials"));
//			}
//		return navigateTo;
//	}
//	
//	
//	
//	public String doLogout() 
//		{
//		FacesContext.getCurrentInstance().getExternalContext().invalidateSession() ;
//		return "/login?faces-redirect=true";
//		}
//
//
//
//	public String getLogin() {
//		return login;
//	}
//
//
//
//	public void setLogin(String login) {
//		this.login = login;
//	}
//
//
//
//	public String getPassword() {
//		return password;
//	}
//
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//
//	public User getUser() {
//		return user;
//	}
//
//
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//
//
//	public boolean isLoggedln() {
//		return loggedln;
//	}
//
//
//
//	public void setLoggedln(boolean loggedln) {
//		this.loggedln = loggedln;
//	}
//	
//	
//	
//	
//	
//}
