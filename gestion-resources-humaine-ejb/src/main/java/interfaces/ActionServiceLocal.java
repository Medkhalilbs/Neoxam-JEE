package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Action;

@Local
public interface ActionServiceLocal {

	void createAction(Action action);
	void updateAction(Action action);
	void deleteAction(Action action);
	Action getAction(int id);
	List<Action> getActions();
}
