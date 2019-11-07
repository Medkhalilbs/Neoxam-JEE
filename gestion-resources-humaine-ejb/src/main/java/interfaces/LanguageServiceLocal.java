package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.language;

@Local
public interface LanguageServiceLocal {
	
	public Boolean addlanguage(language Language) ;
	public Boolean updatelanguage(language Language);
	public Boolean deletelanguage(language Language) ;
	public List<language> findAllLanguage() ;
	public List<language> listeLanguagebyname();
	public language findLanguageById(Integer id) ;

}
