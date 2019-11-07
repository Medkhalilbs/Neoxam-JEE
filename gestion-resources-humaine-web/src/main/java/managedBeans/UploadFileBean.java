package managedBeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import services.EvaluationTestService;




@ManagedBean
@SessionScoped
public class UploadFileBean {
	
	
	
	
	
	
	private String nom;

	private Part uploadedFile;
	//private String folder = "C:\\Users\\X\\workspace\\gestion-resources-humaine\\gestion-resources-humaine-web\\src\\main\\webapp\\uploads";
	private String folder = System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads";
	
	
	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void saveFile() {

		if(uploadedFile!=null)
		{
		try (InputStream input = uploadedFile.getInputStream()) {
			String fileName = uploadedFile.getSubmittedFileName();
			Files.copy(input, new File(folder, fileName).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Part file1;
	private String department;
	private String Deadline;
	@EJB
	EvaluationTestService evaluationTestService;
	public String getDepartment() {
		return department;
	}






	public void setDepartment(String department) {
		this.department = department;
	}






	public String getDeadline() {
		return Deadline;
	}






	public void setDeadline(String deadline) {
		Deadline = deadline;
	}






	public EvaluationTestService getEvaluationTestService() {
		return evaluationTestService;
	}






	public void setEvaluationTestService(EvaluationTestService evaluationTestService) {
		this.evaluationTestService = evaluationTestService;
	}






	public void upload() throws IOException {
		
		
		
		Scanner s2 = new Scanner(file1.getInputStream());
		String fileContent2 = s2.useDelimiter("\\A").next();
		s2.close();
		System.out.println(fileContent2);
		//Files.write(Paths.get("D:\\"+file1.getSubmittedFileName()), fileContent2.getBytes(), StandardOpenOption.CREATE);
		//evaluationTestService.ajouterEval(new EvalutaionTest(department, Deadline, fileContent2));
		
		
		

	}
	
	
	

	

	public void validate(FacesContext context, UIComponent component, Object value) {
		Part file = (Part) value;
		if (file.getSize() > 11) {
			throw new ValidatorException(new FacesMessage("File is too large"));
		}
		if (!file.getContentType().equals("text/plain")) 
			throw new ValidatorException(new FacesMessage("File is not a text file"));
	}


	public Part getFile1() {
		return file1;
	}


	public void setFile1(Part file1) {
		this.file1 = file1;
	}





}