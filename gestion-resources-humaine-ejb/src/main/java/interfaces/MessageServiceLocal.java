package interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import entities.Message;
@Local
public interface MessageServiceLocal {
	public Boolean addMessage(Message message) ;
	public Boolean updateMessage(Message message);
	public Boolean deleteMessage(Message message) ;
	public Message findMessageById(Integer id) ;
	public List<Message> findAllMessages() ;
	public List<Message> listeMessagesByname();
	public Long CountMessages() ;
	public Message findMessageById(String id);
	public Boolean deleteMessage(String id);
	public Boolean deleteMessage(int id) ;
}