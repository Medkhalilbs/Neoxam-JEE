package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Action;

@Remote
public interface ActionServiceRemote {

	void createAction(Action action);
	void updateAction(Action action);
	void deleteAction(Action action);
	Action getAction(int id);
	List<Action> getActions();
}
