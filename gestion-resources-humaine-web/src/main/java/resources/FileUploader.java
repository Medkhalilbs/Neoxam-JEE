package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.ParseException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.pff.PSTAttachment;
import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;

import entities.Candidate;
import entities.Cv;
import managedBeans.CandidateBean;
import services.CandidateService;
import utils.Status;

/**
 * Servlet implementation class FileUploader
 */
@WebServlet("/UploadPstServlet")
public class FileUploader extends HttpServlet {
	
	@EJB
	CandidateService candidateService;
	
	private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		if(fileName == null || fileName.equals("")){
			throw new ServletException("File Name can't be null or empty");
		}
		File file = new File(System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\pstFiles_dotNet\\"+fileName);
		if(!file.exists()){
			throw new ServletException("File doesn't exists on server.");
		}
		System.out.println("File location on server::"+file.getAbsolutePath());
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null? mimeType:"application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.sendRedirect("http://localhost:4054/Candidat");

		ServletOutputStream os       = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read=0;
		while((read = fis.read(bufferData))!= -1){
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("File downloaded at client successfully");
	}
	
	public boolean subjectVerificator(String subject) {
		String[] parts = subject.split(" ");
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].equals("Candidature") || parts[i].equals("candidature") || parts[i].equals("cv")
					|| parts[i].equals("Cv")) {
				return true;
			}
		}
		return false;
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

					// ////////////////////////////////////////////
					/// Parse date de naissance/////////////////
					////////////////////////////////////////////
					String birthDate = "";
					int count = 0;
					String[] allMatches = new String[2];
					Matcher m = Pattern
							.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19[0-9]{2}|20[0-9]{2})")
							.matcher(contentPdf);
					while (m.find()) {
						allMatches[count] = m.group();
						birthDate = m.group();

					}
					System.out.println("birth Date =" + birthDate);

					// ////////////////////////////////////////////
					/// Parse Num téléphone/////////////////
					////////////////////////////////////////////

					String phone = "";
					Pattern patternPhone = Pattern.compile(
							"([+]216\\d{4} \\d{2} \\d{3} \\d{3})|[+]216\\d{4} \\d{2} \\d{2} \\d{2} \\d{2}|[+]216\\d{8}|\\d{2} \\d{3} \\d{3}|\\d{2} \\d{2} \\d{2} \\d{2}|\\d{8}");
					Matcher matcherPhone = patternPhone.matcher(contentPdf);
					if (matcherPhone.find()) {
						phone = phone + matcherPhone.group(0);
					}
					System.out.println("Phone number = " + phone);

					// ////////////////////////////////////////////
					/// Parse Mail /////////////////////////////
					////////////////////////////////////////////

					String email = "";
					Pattern patternEmail = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
					Matcher matcherEmail = patternEmail.matcher(contentPdf);
					if (matcherEmail.find()) {
						email = email + matcherEmail.group(0);
					}
					System.out.println("Email = " + email);

					// ////////////////////////////////////////////
					/// Parse Nom & Prenom /////////////////////
					////////////////////////////////////////////

					String nomPrenom = "";

					Pattern patternNomPrenomEmail = Pattern.compile("[a-zA-Z0-9_.+-]+@");
					Matcher matcherNomPrenomEmail = patternNomPrenomEmail.matcher(matcherEmail.group(0));
					if (matcherNomPrenomEmail.find()) {
						// System.out.println(matcherNomPrenomEmail.group(0));

					}

					try {
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

							Pattern patternNPrenom = Pattern.compile("([_.+-][a-zA-Z]+)");

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

					
					
					
					Candidate candidate = new Candidate(email, email, birthDate , new Date(), Status.active);
					candidate.setPhone_number(phone);
					String[] parts = nomPrenom.split(" ");
					String nom = parts[0];
					String prenom = parts[1];
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
					myCv.setFilePath(mail.getReturnPath());
					myCv.setFileSize(mail.getMessageSize());
					myCv.setFileType("pdf");
					myCv.setOriginalFileType("pdf");
					myCv.setCandidates(candidate);
					//cvservice.addCv(myCv);
					Set<Cv> cvs = new HashSet<Cv>();
					cvs.add(myCv);
					candidate.setCvs(cvs);
					
					candidateService.addCandidate(candidate);
					
					
				
				}
				mail = (PSTMessage) folder.getNextChild();
		
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}

		response.setContentType("text/html");
		response.sendRedirect("http://localhost:4054/Candidat");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			out.println(fileItemsList);
			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				System.out.println("FieldName="+fileItem.getFieldName());
				System.out.println("FileName="+fileItem.getName());
				System.out.println("ContentType="+fileItem.getContentType());
				System.out.println("Size in bytes="+fileItem.getSize());

				File file = new File(System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\pstFiles_dotNet\\"+fileItem.getName());
				System.out.println("Absolute Path at server="+file.getAbsolutePath());
				fileItem.write(file);
				
				PSTFile pst;
				pst = new PSTFile(
						System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\pstFiles_dotNet\\"
								+ file.getName());
				PSTFolder pstFolder = pst.getRootFolder();
				parseFolder(pstFolder);
				
						
				out.write("File "+fileItem.getName()+ " uploaded successfully.");
				out.write("<br>");
				out.write("<a href=\"UploadPstServlet?fileName="+fileItem.getName()+"\">Download "+fileItem.getName()+"</a>");
			}
		} catch (FileUploadException e) {
			out.write("Exception in uploading file : File upload exception.");			
			e.printStackTrace() ;
		} catch (Exception e) {
			out.write("Exception in uploading file.");
			e.printStackTrace();
		}
		out.write("</body></html>");
	}
}
