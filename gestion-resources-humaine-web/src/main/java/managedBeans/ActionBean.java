package managedBeans;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entities.Action;
import entities.TypeAction;
import interfaces.ActionServiceLocal;

@ManagedBean
@ViewScoped
public class ActionBean {

	@EJB
	private ActionServiceLocal as;
	private List<Action> actions;
	private Action action, actionToupdate;
	private String typeAction;
	private int idAction = 0;

	@PostConstruct
	public void init() {
		idAction = 0;
		action = new Action();
		actionToupdate = new Action();
		actions = as.getActions();
	}

	public void doCreate() {
		if (typeAction.equals("consecutif"))
			action.setType(TypeAction.consecutif);
		else
			action.setType(TypeAction.preventif);
		as.createAction(action);
		action = new Action();
		actions = as.getActions();
	}

	public void doDelete() {
		System.out.println(idAction);
		as.deleteAction(as.getAction(idAction));
		actions = as.getActions();
	}

	public void doSelect() {
		actionToupdate = as.getAction(idAction);
	}

	public void doUpdate() {
		as.updateAction(actionToupdate);
		actionToupdate = new Action();
		actions = as.getActions();
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Action getActionToupdate() {
		return actionToupdate;
	}

	public void setActionToupdate(Action actionToupdate) {
		this.actionToupdate = actionToupdate;
	}

	public int getIdAction() {
		return idAction;
	}

	public void setIdAction(int idAction) {
		this.idAction = idAction;
	}
}
