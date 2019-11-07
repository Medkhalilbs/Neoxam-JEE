package managedBeans;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

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
public class RegisterEmailValidator implements Validator {
	@EJB
	UserService userService;
	Pattern pattern = Pattern.compile("[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException 
	{

		
			if(userService.checkIfEmailExist( value.toString())==1)
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email Validation failed", "Email existe deja,veillez choisir un autre."));
		    	
			else if(value.toString().length()==0)
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email Validation failed", "Veuillez entrer une adresse email."));
			
			else if (userService.checkIfEmailExist( value.toString())==3)
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email Validation failed", "Format email invalide."));
	}
}
