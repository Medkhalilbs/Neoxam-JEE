package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.language;

@Remote
public interface LanguageServiceRemote {
	
	public Boolean addlanguage(language Language) ;
	public Boolean updatelanguage(language Language);
	public Boolean deletelanguage(language Language) ;
	public List<language> findAllLanguage() ;
	public List<language> listeLanguagebyname();
	public language findLanguageById(Integer id) ;

}
