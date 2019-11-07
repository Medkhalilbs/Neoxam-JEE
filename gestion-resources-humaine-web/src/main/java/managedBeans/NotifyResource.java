//package managedBeans;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//
//import org.primefaces.push.annotation.OnMessage;
//import org.primefaces.push.annotation.PushEndpoint;
//import org.primefaces.push.impl.JSONEncoder;
//
//@PushEndpoint("/notify")
//public class NotifyResource {
//
//  @OnMessage(encoders={JSONEncoder.class})
//  public FacesMessage onMessage(FacesMessage message)
//  {return message;}
//
//}