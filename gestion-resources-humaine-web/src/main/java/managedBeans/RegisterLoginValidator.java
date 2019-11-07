package managedBeans;



import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import services.UserService;
@ManagedBean
@RequestScoped
@FacesValidator("registerLoginValidator")
public class RegisterLoginValidator implements Validator {
	@EJB
	UserService userService;
	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {

		if(userService.checkIfLoginExist( value.toString())==2)
			{System.out.println("***************************************************************************************************************************************");
			FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Validation failed", "Login Exist.");
			throw new ValidatorException(fmsg);}
		
		else if (userService.checkIfLoginExist( value.toString())==4)
				{
			FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Validation failed", "Login doit avoir plus que 4 characteres.");
			throw new ValidatorException(fmsg);}
				}
		
	
}
