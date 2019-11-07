package services;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Answer;
import entities.Domain;
import entities.Question;
import entities.Topic;
import interfaces.QuestionServiceRemote;

/**
 * Session Bean implementation class QuestionService
 */
@Stateless
@LocalBean
public class QuestionService implements QuestionServiceRemote {
	@PersistenceContext(unitName="gestion-resources-humaine-ejb")
	public EntityManager em;
	private Integer integer;

    /**
     * Default constructor. 
     */
    public QuestionService() {
       
    }

    
    
    @Override
    public int ajouterDomain(Domain domain) 
    { em.persist(domain); 
    return domain.getId();
    
    }
    
    
    
    @Override
    public int ajout_question(Question q) 
    { 
    	try {
    		System.out.println("affichage="+q);
    	em.persist(q); 
    return q.getId();
    	}
    	 catch (Exception e) {
  			System.err.println("Cant find Event");
  			integer = (Integer) null;
			return integer;
    	 }
    }
    
    
    @Override
    public int ajout_reponse(Answer a) 
    { 
    	try {
    		System.out.println("affichage="+a);
    	em.persist(a); 
    return a.getId();
    	}
    	 catch (Exception e) {
  			System.err.println("Cant find Event");
  			integer = (Integer) null;
			return integer;
    	 }
    }
   
    
    @Override
    public void affecterquestiontopic(int topicid, int questionid) {
    	Question questionManagedEntity = em.find(Question.class, questionid); 
    	Topic topicManagedEntity = em.find(Topic.class, topicid);
    	questionManagedEntity.setTopic(topicManagedEntity);
    }

    
    
    ////////////////
 

	@Override
	public boolean updateQuestion(Question question) {
		if(getQuestionById(question.getId())!=null){
			em.merge(question);
			return true;
		}
		else
			return false;
		
		
	}

	@Override
	public boolean deleteQuestion(int id) {
		if(getQuestionById(id)!=null){
			em.remove(id);
			return true;
		}
		else
			return false;
		
	}

	@Override
	public Question getQuestionById(int id,String complexity, String enonce) {
    	TypedQuery<Question> query = em.createQuery("Select q from Question q "  +"where q.id=:id" , Question.class); 
         query.setParameter("id", id); 
          
         Question q = null;
          return q; 
	}
	
	public long nombredequestionbyId(int id)
	{
		TypedQuery<Long> query = em.createQuery("Select count(q) from Question q "  +"where q.topic.id=:id" , Long.class); 
        query.setParameter("id", id); 
        return query.getSingleResult();
         
	}
	@Override
	public List<Question> getlist() {
    	TypedQuery<Question> query = em.createQuery("Select q from Question q "  , Question.class); 
         return query.getResultList();
          
	}
//
//	@Override
//	public List<Attribut> getMyAttributs(int id) {
//		 Query query = entityManager.createQuery("SELECT new Attribut(a.id,a.attributValue,a.duplicated) FROM Question q JOIN q.attributs a WHERE q.id=:param");
//	    return (List<Attribut>) query.setParameter("param", id).getResultList();
//
//	}


	@Override
	public Question getQuestionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
