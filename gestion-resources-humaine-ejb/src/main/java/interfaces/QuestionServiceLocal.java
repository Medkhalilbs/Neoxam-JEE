package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Answer;
import entities.Domain;
import entities.Question;

@Local
public interface QuestionServiceLocal {


	public int ajout_question(Question q);

	void affecterquestiontopic(int topicid, int questionid);
	boolean updateQuestion(Question question);
	boolean deleteQuestion(int id);
	Question getQuestionById(int id);
	Question getQuestionById(int id, String complexity, String enonce);
	public List<Question> getlist();
	int ajouterDomain(Domain domain);
	int ajout_reponse(Answer a);
}

