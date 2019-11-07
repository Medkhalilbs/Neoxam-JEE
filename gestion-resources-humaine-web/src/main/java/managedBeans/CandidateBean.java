package managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.http.ParseException;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

import com.pff.PSTAttachment;
import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.image.ImageParser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import entities.Candidate;
import entities.Cv;
import interfaces.CvServiceRemote;
import services.CandidateService;
import services.CvService;
import utils.Address;
import utils.Status;

//h: tout les composant graphique
//f: toute les balise qui seron converti en code java 
//si 2 bean different , on appel le 1managed bean dans le 2eme grace a @Managedproperty loginBean lb; lb.employe.....
@ManagedBean
@SessionScoped
public class CandidateBean {

	////////////////////////////////////////////
	////////// Variables //////////////////////
	////////////////////////////////////////////

	private String first_name;
	private String last_name;
	private int ProfileValide;
	private String SocialState;
	private String DriverLience;
	private int Age;
	private Candidate toBeDisplayedCandidate;
	private String Description;
	private Address address;
	private String Birthdate;
	private StreamedContent toBeDownloadedfile;

	@EJB
	CandidateService candidateservice;
	CvService cvservice;

	private String pstFilePath = "";
	UploadedFile file;

	////////////////////////////////////////////
	/// Getters & Setters //////////////////////
	////////////////////////////////////////////

	public String getBirthdate() {
		return Birthdate;
	}

	public void setBirthdate(String birthdate) {
		Birthdate = birthdate;
	}

	public String getPstFilePath() {
		return pstFilePath;
	}

	public void setPstFilePath(String pstFilePath) {
		this.pstFilePath = pstFilePath;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int isProfileValide() {
		return ProfileValide;
	}

	public void setProfileValide(int profileValide) {
		ProfileValide = profileValide;
	}

	public String getSocialState() {
		return SocialState;
	}

	public void setSocialState(String socialState) {
		SocialState = socialState;
	}

	public String getDriverLience() {
		return DriverLience;
	}

	public void setDriverLience(String driverLience) {
		DriverLience = driverLience;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CandidateService getCandidateservice() {
		return candidateservice;
	}

	public void setCandidateservice(CandidateService candidateservice) {
		this.candidateservice = candidateservice;
	}

	public Candidate getToBeDisplayedCandidate() {
		return toBeDisplayedCandidate;
	}

	public void setToBeDisplayedCandidate(Candidate toBeDisplayedCandidate) {
		this.toBeDisplayedCandidate = toBeDisplayedCandidate;
	}

	////////////////////////////////////////////
	/// View Candidate //////////////////////
	////////////////////////////////////////////

	public String viewCandidateDetails(Candidate candidate) {
		toBeDisplayedCandidate = candidate;
		return "/pages/candidate/candidateDetails?faces-redirect=true";

	}

	////////////////////////////////////////////
	/// update candidate //////////////////////
	////////////////////////////////////////////

	public void updateCandidate() {
		candidateservice.updateCandidate(toBeDisplayedCandidate);
	}

	////////////////////////////////////////////
	/// Confirm msg//////////////////////
	////////////////////////////////////////////
	public void handleClose(CloseEvent event) {
		addMessage(event.getComponent().getId() + " closed", "So you don't like nature?");
	}

	public void handleMove(MoveEvent event) {
		addMessage(event.getComponent().getId() + " moved", "Left: " + event.getLeft() + ", Top: " + event.getTop());
	}

	public void destroyWorld() {
		addMessage("System Error", "Please try again later.");
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	////////////////////////////////////////////
	/// Upload PST File //////////////////////
	////////////////////////////////////////////

	public void fileUploadListener(FileUploadEvent e) throws IOException, PSTException, ParseException, SAXException {

		// Get uploaded file from the FileUploadEvent

		this.file = e.getFile();

		// Print out the information of the file
		System.out.println(
				"Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());

		// Path folder = Paths.get("/uploads");
		// String filename = FilenameUtils.getBaseName(file.getFileName());
		// Path savedFile = Files.createTempFile(folder, filename + "-", "." +
		// "pst");
		// try (InputStream input = file.getInputstream()) {
		// Files.copy(input, savedFile, StandardCopyOption.REPLACE_EXISTING);
		// }
		//
		System.out.println("Uploaded file successfully saved in " + file);

		try {
			copyFile(e.getFile().getFileName(), e.getFile().getInputstream());
			FacesMessage msg = new FacesMessage("Success! ", e.getFile().getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (IOException a) {
			a.printStackTrace();
		}

		PSTFile pst;
		// pst = new
		// PSTFile("C:\\Users\\X\\devstudio\\wildfly-9.0.1.Final2\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\pstFiles\\"+file.getFileName());
		pst = new PSTFile(System.getProperty("jboss.home.dir")
				+ "\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\pstFiles\\"
				+ file.getFileName());
		parseFolder(pst.getRootFolder());

	}

	public void parseFolder(PSTFolder folder) throws IOException, PSTException, ParseException, SAXException {

		if (folder.hasSubfolders()) {
			Vector<PSTFolder> childfolders = folder.getSubFolders();
			for (PSTFolder child : childfolders) {
				parseFolder(child);
			}
		}

		if (folder.getContentCount() > 0) {
			PSTMessage mail = (PSTMessage) folder.getNextChild();
			while (mail != null) {
				if (subjectVerificator(mail.getSubject()) && mail.getNumberOfAttachments() > 0) {
					PSTAttachment attach = mail.getAttachment(0);
					String mail1 = mail.getSenderEmailAddress();
					String name = mail.getSenderName();
					String filename = attach.getFilename();
					InputStream i = attach.getFileInputStream();

					// System.out.println("mail.getAttachment(0)="+mail.getAttachment(0));

					// String pdfPath =
					// "C:\\Users\\X\\devstudio\\wildfly-9.0.1.Final2\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\cvFiles\\"
					// + name + ".pdf";
					BodyContentHandler handler = new BodyContentHandler();
					Metadata metadata = new Metadata();
					InputStream inputstream = null;

					inputstream = i;

					ParseContext pcontext = new ParseContext();

					// parsing pdf using a pdf parser
					PDFParser pdfparser = new PDFParser();

					try {
						pdfparser.parse(inputstream, handler, metadata, pcontext);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SAXException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TikaException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String contentPdf = handler.toString();
					String nomPrenom="";
					String email="";
					String birthDate ="";
					String phone="";
					String AgeParse="";
					
					// ////////////////////////////////////////////
					/// Parse date de naissance/////////////////
					////////////////////////////////////////////
					String Mois_Fr_Short = "[Jj]anv|[Ff]évr|[Mm]ars|[Aa]vr|[Mm]ai|[Jj]uin|[Jj]uil|[Aa]oût|[Ss]ept|[Oo]ct|[Nn]ov|[Dd]éc";
					String Mois_En_Short = "[Jj]an|[Ff]eb|[Mm]ar|[Aa]pr|[Mm]ay|[Jj]un|[Jj]ul|[Aa]ug|[Ss]ep|[Oo]ct|[Nn]ov|[Dd]ec";
					String Mois_Fr_Long = "[Jj]anvier|[Ff]évrier|[Mm]ars|[Aa]vril|[Mm]ai|[Jj]uin|[Jj]uillet|[Aa]oût|[Ss]eptembre|[Oo]ctobre|[Nn]ovembre|[Dd]écembre";
					String Mois_En_Long ="[Jj]anuary|[Ff]ebruary|[Mm]arch|[Aa]pril|[Mm]ay|[Jj]une|[Jj]uly|[Aa]ugust|[Ss]eptember|[Oo]ctober|[Nn]ovember|[Dd]ecember";

						try{
					birthDate = "";		
					int count = 0;
					String[] allMatches = new String[2];
					Matcher m = Pattern
							.compile("(0[1-9]|[12][0-9]|3[01])[ - /.](0[1-9]|1[012]|[Jj]anv|[Ff]évr|[Mm]ars|[Aa]vr|[Mm]ai|[Jj]uin|[Jj]uil|[Aa]oût|[Ss]ept|[Oo]ct|[Nn]ov|[Dd]éc|[Jj]an|[Ff]eb|[Mm]ar|[Aa]pr|[Mm]ay|[Jj]un|[Jj]ul|[Aa]ug|[Ss]ep|[Oo]ct|[Nn]ov|[Dd]ec|[Jj]anvier|[Ff]évrier|[Mm]ars|[Aa]vril|[Mm]ai|[Jj]uin|[Jj]uillet|[Aa]oût|[Ss]eptembre|[Oo]ctobre|[Nn]ovembre|[Dd]écembre|[Jj]anuary|[Ff]ebruary|[Mm]arch|[Aa]pril|[Mm]ay|[Jj]une|[Jj]uly|[Aa]ugust|[Ss]eptember|[Oo]ctober|[Nn]ovember|[Dd]ecember)[ - /.](19[0-9]{2}|20[0-9]{2})")
							.matcher(contentPdf);
					while (m.find()) {
						allMatches[count] = m.group();
						birthDate = m.group();

					}
					System.out.println("birth Date =" + birthDate);}
					catch(Exception e)
					{System.out.println(e.getMessage());}

					// ////////////////////////////////////////////
					/// Parse Num téléphone/////////////////
					////////////////////////////////////////////
						try {
					phone = "";
					Pattern patternPhone = Pattern.compile(
							"([+]216\\d{4} \\d{2} \\d{3} \\d{3})|[+]216\\d{4} \\d{2} \\d{2} \\d{2} \\d{2}|[+]216\\d{8}|\\d{2} \\d{3} \\d{3}|\\d{2} \\d{2} \\d{2} \\d{2}|\\d{8}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}|(?:\\d{2}-){2}\\d{4}|\\(\\d{2}\\)\\d{4}-?\\d{4}");
					Matcher matcherPhone = patternPhone.matcher(contentPdf);
					if (matcherPhone.find()) {
						phone = phone + matcherPhone.group(0);
					}
					System.out.println("Phone number = " + phone);}
					catch(Exception e)
					{System.out.println(e.getMessage());}
						
						// ////////////////////////////////////////////
						/// Age /////////////////
						////////////////////////////////////////////
							try {
						AgeParse = "";
						Pattern patternAge = Pattern.compile("[0-9]{2}");
						Matcher matcherAge = patternAge.matcher(contentPdf); 
						
						if (matcherAge.find()) {
							AgeParse = AgeParse + matcherAge.group(0);
						}
						System.out.println("Age after PARSE  = " + AgeParse);}
						catch(Exception e)
						{System.out.println(e.getMessage());}	

					// ////////////////////////////////////////////
					/// Parse Mail /////////////////////////////
					////////////////////////////////////////////
						try{
					 email = "";
					Pattern patternEmail = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
					Matcher matcherEmail = patternEmail.matcher(contentPdf);
					if (matcherEmail.find()) {
						email = email + matcherEmail.group(0);
					}
					System.out.println("Email = " + email);

					// ////////////////////////////////////////////
					/// Parse Nom & Prenom /////////////////////
					////////////////////////////////////////////

					nomPrenom = "";
					
					Pattern patternNomPrenomEmail = Pattern.compile("[a-zA-Z0-9_.+-]+@");
					Matcher matcherNomPrenomEmail = patternNomPrenomEmail.matcher(matcherEmail.group(0));
					if (matcherNomPrenomEmail.find()) {
						// System.out.println(matcherNomPrenomEmail.group(0));

					}

					
						Pattern patternNomPrenom = Pattern.compile("[a-zA-Z_.+-]+");

						Matcher matcherNomPrenom = patternNomPrenom.matcher(matcherNomPrenomEmail.group(0));

						if (matcherNomPrenom.find()) {
							// System.out.println(matcherNomPrenom.group(0));
							nomPrenom = nomPrenom + matcherNomPrenom.group(0);
						}
						try {
							Pattern patternNomAndPrenom = Pattern.compile("([a-zA-Z]+)");
							Matcher matcherNomAndPrenom = patternNomAndPrenom.matcher(matcherNomPrenom.group(0));
							if (matcherNomAndPrenom.find()) {
								// System.out.println(matcherNomAndPrenom.group(0));
							}

							Pattern patternNPrenom = Pattern.compile("([_.+- ][a-zA-Z]+)");

							Matcher matcherNPrenom = patternNPrenom.matcher(matcherNomPrenom.group(0));
							if (matcherNPrenom.find()) {
								// System.out.println(matcherNPrenom.group(0));

							}

							Pattern patternPrenom = Pattern.compile("([a-zA-Z]+)");

							Matcher matcherPrenom = patternPrenom.matcher(matcherNPrenom.group(0));

							if (matcherPrenom.find()) {
								// System.out.println(matcherPrenom.group(0));
							}

							Pattern patternFindName = Pattern.compile(matcherNomAndPrenom.group(0).toUpperCase() + "|"
									+ matcherNomAndPrenom.group(0).substring(0, 1).toUpperCase()
									+ matcherNomAndPrenom.group(0).substring(1).toLowerCase());
							Matcher matcherFindName = patternFindName.matcher(contentPdf);
							if (matcherFindName.find()) {
								// System.out.println(matcherFindName.group(0)+
								// "******"+matcherNomAndPrenom.group(0));
								nomPrenom = "";
								nomPrenom = nomPrenom + matcherFindName.group(0);
							}

							Pattern patternFind = Pattern
									.compile(matcherPrenom.group(0) + "|" + matcherPrenom.group(0).toUpperCase() + "|"
											+ matcherPrenom.group(0).substring(0, 1).toUpperCase()
											+ matcherPrenom.group(0).substring(1).toLowerCase());
							Matcher matcherFind = patternFind.matcher(contentPdf);
							if (matcherFind.find()) {
								// System.out.println(matcherFind.group(0)+
								// "******"+matcherPrenom.group(0));
								nomPrenom = nomPrenom + " " + matcherFind.group(0);

							}
						} catch (Exception e) {
							// TODO: handle exception

							System.out.println("no name and last name");

						}
					} catch (Exception e) {
						// TODO: handle exception

					}

					System.out.println("nomprenom=" + nomPrenom);

					InputStream i2 = attach.getFileInputStream();

					String pdfPath = System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\cvFiles\\"
							+ nomPrenom + ".pdf";
					File f = new File(pdfPath);
					FileOutputStream fos = new FileOutputStream(f);
					byte[] buf = new byte[4096];
					int bytesRead;
					while ((bytesRead = i2.read(buf)) != -1) {
						fos.write(buf, 0, bytesRead);
					}
					// fos.close();

					// candidateservice.parsePDFtoCandidate(new
					// File("C:\\Users\\X\\devstudio\\wildfly-9.0.1.Final2\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\cvFiles\\"
					// + name + ".pdf"));

					//////////////////////////////////////////////////////////////////////////
					/////////// Create a new candidate with parser
					////////////////////////////////////////////////////////////////////////// information/////////////////
					//////////////////////////////////////////////////////////////////////////

					
					
					
					Candidate candidate = new Candidate(email, email, "123456Mm" , new Date(), Status.active);
					candidate.setPhone_number(phone);
					
						String nom="";
						String prenom="";
						if (nomPrenom.equals(""))
						{
							nom=email;
							prenom=email;
						}
						
						else
					{
							try{
							String[] parts = nomPrenom.split(" ");
							nom = parts[0];
							prenom = parts[1];
							}
							catch(Exception e)
							{System.out.println(e.getMessage());}
					}
					
					
					candidate.setFirst_name(nom);
					candidate.setLast_name(prenom);
					candidate.setBirthdate(birthDate);
					
					
					try {
						birthDate.replace("-", "/").replace(" ", "/").replace(".", "/");
						Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date1);
						Period p = Period.between(LocalDate.of(calendar.get(Calendar.YEAR) + 1,
								calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH) + 1),
								LocalDate.now());
						candidate.setAge(p.getYears());
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
					System.out.println("candidat crée !!!!!!!!!!!!!!!!");
					System.out.println("candidate=" + candidate);
					
					
					Cv myCv = new Cv();
					myCv.setFilePath(f.getAbsolutePath());
					myCv.setFileSize(mail.getMessageSize());
					myCv.setFileType("pdf");
					myCv.setOriginalFileType("pdf");
					myCv.setCandidates(candidate);
					//cvservice.addCv(myCv);
					Set<Cv> cvs = new HashSet<Cv>();
					cvs.add(myCv);
					candidate.setCvs(cvs);
					
					candidateservice.addCandidate(candidate);
					
					
				
				}
				mail = (PSTMessage) folder.getNextChild();
		
			}
		}
	}

	////////////////////////////////////////////
	/// Verification Candidature ///////////////
	////////////////////////////////////////////
	public boolean subjectVerificator(String subject) {
		String[] parts = subject.split(" ");
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].equals("Candidature") || parts[i].equals("candidature") || parts[i].equals("cv")
					|| parts[i].equals("Cv") || parts[i].equals(" ") || parts[i].equals("condidature")
					|| parts[i].equals("Condidature")) {
				return true;
			}
		}
		return false;
	}

	////////////////////////////////////////////
	/// Afficher Liste Candidat/////////////////
	////////////////////////////////////////////

	public List<Candidate> affichercandidate() {
		return candidateservice.findAllCandidate();
	}

	////////////////////////////////////////////
	/// Supprimer Candidat /////////////////////
	////////////////////////////////////////////

	public void supprmier(int candidateId) {
		// String navigateTo = null;
		candidateservice.deleteCandidateWithId(candidateId);

	}

	////////////////////////////////////////////
	/// Download Cv PDF file////////////////////
	////////////////////////////////////////////

	public StreamedContent getToBeDownloadedfile(Candidate candidate) {
		String nomPrenom = candidate.getFirst_name() + " " + candidate.getLast_name() + ".pdf";
		File repertoire = new File(System.getProperty("jboss.home.dir")
				+ "\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\cvFiles\\");
		// File repertoire = new
		// File("C:\\Users\\X\\devstudio\\wildfly-9.0.1.Final2\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\cvFiles\\");
		File[] list = repertoire.listFiles();

		InputStream stream = null;

		if (list != null)
			for (File fil : list) {

				if (nomPrenom.equalsIgnoreCase(fil.getName())) {
					System.out.println("fil.getParentFile()=" + fil.getParentFile());
					System.out.println("fil.getName()=" + fil.getName());
					stream = FacesContext.getCurrentInstance().getExternalContext()
							.getResourceAsStream("/uploads/cvFiles/" + fil.getName());

				}
			}

		if (stream != null)
			toBeDownloadedfile = new DefaultStreamedContent(stream, "application/pdf", nomPrenom + ".pdf");

		return toBeDownloadedfile;
	}

	public void copyFile(String fileName, InputStream in) {
		try {
			// String destination =
			// "C:\\Users\\X\\devstudio\\wildfly-9.0.1.Final2\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\pstFiles\\";
			String destination = System.getProperty("jboss.home.dir")
					+ "\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\pstFiles\\";

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
