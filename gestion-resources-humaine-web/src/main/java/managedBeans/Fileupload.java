package managedBeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
@ManagedBean
@ViewScoped
public class Fileupload {
 
 private Part uploadedFile;
 private String folder = "c:\\files";
 
 public Part getUploadedFile() {
 return uploadedFile;
 }
 
 public void setUploadedFile(Part uploadedFile) {
 this.uploadedFile = uploadedFile;
 }
 
 
 public String saveFile(){
	 String  navigateTo = null ;
 
 try (InputStream input = uploadedFile.getInputStream()) {
	
 String fileName = uploadedFile.getSubmittedFileName();
         Files.copy(input, new File(folder, fileName).toPath());
        // navigateTo = "/pages/chahnez/ajouterTopic?faces-redirect=true"; 
     }
     catch (IOException e) {
         e.printStackTrace();
     }
 return navigateTo;
 }
 
}