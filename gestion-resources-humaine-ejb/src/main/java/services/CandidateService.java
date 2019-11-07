package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Candidate;
import interfaces.CandidateServiceRemote;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDResources;
//import org.apache.tika.exception.TikaException;
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.parser.ParseContext;
//import org.apache.tika.parser.image.ImageParser;
//import org.apache.tika.parser.microsoft.OfficeParser;
//import org.apache.tika.parser.pdf.PDFParser;
//import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
public class CandidateService implements CandidateServiceRemote {
	
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")	
    private EntityManager entityManager;

	@Override
	public Boolean addCandidate(Candidate Candidate) {
		try {
			entityManager.persist(Candidate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean updateCandidate(Candidate Candidate) {
		try {
			entityManager.merge(Candidate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean deleteCandidate(Candidate Candidate) {
		try {
			entityManager.remove(entityManager.merge(Candidate));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Boolean deleteCandidateWithId(int condidatId) {
	
		try {
			Candidate con = entityManager.find(Candidate.class, condidatId);
			entityManager.remove(con);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	@Override
	public List<Candidate> findAllCandidate() {
		Query query=entityManager.createQuery("select u from Candidate u");
		return query.getResultList();
	}
	
	@Override
	public Candidate findCandidateById(Integer id) {
		Candidate Candid=null;
		try {
			Candid=entityManager.find(Candidate.class, id);
			
		} catch (Exception e) {
			
		}
		return Candid;
	}

	@Override
	public List<Candidate> listeCandidatebyname() {
		return null;
	}

	@Override
	public Candidate parsePDFtoCandidate(File file) {
//		BodyContentHandler handler =  new BodyContentHandler();
//		Metadata metadata= new Metadata();
//		FileInputStream inputstream=null;
//		try {
//			inputstream = new  FileInputStream(file);
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		ParseContext pcontext = new ParseContext();
//		//parsing pdf using a pdf parser
//		PDFParser pdfparser = new PDFParser();
//		try {
//			pdfparser.parse(inputstream, handler, metadata,pcontext);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SAXException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (TikaException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		String contentPdf = handler.toString();
//		
////		***Date De Naissence ****
//		String birthDate="";
//		int count=0;
//		    String[] allMatches = new String[2];
//		    Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19[0-9]{2}|20[0-9]{2})").matcher(contentPdf);
//		    while (m.find()) {
//		        allMatches[count] = m.group();
//		        birthDate =m.group();
//		        
//		    }
//	        System.out.println("birth Date ="+birthDate); 
//
//		
////		***** Phone Number*****
//		
//	    String phone="";
//		Pattern patternPhone = Pattern.compile("([+]216\\d{2} \\d{3} \\d{3})|[+]216\\d{2} \\d{2} \\d{2} \\d{2}|[+]216\\d{8}|\\d{2} \\d{3} \\d{3}|\\d{2} \\d{2} \\d{2} \\d{2}|\\d{8}");
//		Matcher matcherPhone = patternPhone.matcher(contentPdf);
//		if (matcherPhone.find()) {
//		    phone=phone+matcherPhone.group(0);
//		}
//	    System.out.println("Phone number = " +phone);
//
//		
////		******Email******
//	    String email= "";
//		Pattern patternEmail = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
//		Matcher matcherEmail = patternEmail.matcher(contentPdf);
//		if (matcherEmail.find()) {
//		    email=email+matcherEmail.group(0);
//		}
//	    System.out.println("Email = "+email);
//
//		
////		*****Nom Et Prenom*****
//		
//	    
//
//		String nomPrenom = "";
//
//	    
//		Pattern patternNomPrenomEmail = Pattern.compile("[a-zA-Z0-9_.+-]+@");
//		Matcher matcherNomPrenomEmail = patternNomPrenomEmail.matcher(matcherEmail.group(0));
//		if (matcherNomPrenomEmail.find()) {
//		    System.out.println(matcherNomPrenomEmail.group(0));
//		
//		}
//		
//		try{
//		Pattern patternNomPrenom = Pattern.compile("[a-zA-Z_.+-]+");
//		
//		Matcher matcherNomPrenom = patternNomPrenom.matcher(matcherNomPrenomEmail.group(0));
//		if (matcherNomPrenom.find()) {
//		    System.out.println(matcherNomPrenom.group(0));
//		    nomPrenom= nomPrenom+matcherNomPrenom.group(0);
//		}
//		try{
//		Pattern patternNomAndPrenom = Pattern.compile("([a-zA-Z]+)");
//		Matcher matcherNomAndPrenom = patternNomAndPrenom.matcher(matcherNomPrenom.group(0));
//		if (matcherNomAndPrenom.find()) {
//		    System.out.println(matcherNomAndPrenom.group(0));
//		}
//		
//		Pattern patternNPrenom = Pattern.compile("([_.+-][a-zA-Z]+)");
//		
//		Matcher matcherNPrenom = patternNPrenom.matcher(matcherNomPrenom.group(0));
//		if (matcherNPrenom.find()) {
//		    System.out.println(matcherNPrenom.group(0));
//		    
//		    
//		}
//		
//		
//		Pattern patternPrenom = Pattern.compile("([a-zA-Z]+)");
//		
//		Matcher matcherPrenom = patternPrenom.matcher(matcherNPrenom.group(0));
//		
//		if (matcherPrenom.find()) {
//		    System.out.println(matcherPrenom.group(0));
//		}
//		
//		Pattern patternFindName = Pattern.compile(matcherNomAndPrenom.group(0).toUpperCase()+"|"
//			+matcherNomAndPrenom.group(0).substring(0,1).toUpperCase() + matcherNomAndPrenom.group(0).substring(1).toLowerCase());
//		Matcher matcherFindName = patternFindName.matcher(contentPdf);
//		if (matcherFindName.find()) {
//		    System.out.println(matcherFindName.group(0)+ "******"+matcherNomAndPrenom.group(0));
//		    nomPrenom="";
//		    nomPrenom=nomPrenom+matcherFindName.group(0);
//		}
//		
//		Pattern patternFind = Pattern.compile(matcherPrenom.group(0)+"|"+matcherPrenom.group(0).toUpperCase()+"|"
//			+matcherPrenom.group(0).substring(0,1).toUpperCase() + matcherPrenom.group(0).substring(1).toLowerCase());
//		Matcher matcherFind = patternFind.matcher(contentPdf);
//		if (matcherFind.find()) {
//		    System.out.println(matcherFind.group(0)+ "******"+matcherPrenom.group(0));
//		    nomPrenom=nomPrenom+" "+matcherFind.group(0);
//
//		}
//		}catch (Exception e) {
//			// TODO: handle exception
//			
//			System.out.println("no name and last name");
//			
//		}}catch (Exception e) {
//			// TODO: handle exception
//			
//		}
//		
//		System.out.println("nomprenom="+nomPrenom);
//
//		
		return new Candidate();

	}
	



}
