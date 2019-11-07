package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Answer;
import entities.Domain;

import entities.Topic;
import entities.User;
import interfaces.TopicServiceRemote;
import entities.Question;

/**
 * Session Bean implementation class TopicService
 */
@Stateless
@LocalBean
public class TopicService implements TopicServiceRemote {

	/**
	 * Default constructor.
	 */
	public TopicService() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "gestion-resources-humaine-ejb")
	public EntityManager em;

	@Override
	public int ajouterTopic(Topic topic) {
		em.persist(topic);
		return topic.getId();
	}

	@Override
	public void affecterdomainatopic(int topicid, int domainid) {
		Domain domainManagedEntity = em.find(Domain.class, domainid);
		Topic topicManagedEntity = em.find(Topic.class, topicid);
		topicManagedEntity.setDomain(domainManagedEntity);
	}

	@Override
	public void deleteTopicById(int topicId) {
		em.remove(em.find(Topic.class, topicId));
	}

	public List<Topic> rechechertopic(String dom) {
		TypedQuery<Topic> query = em.createQuery("Select t from Topic t where t.nom=:dom", Topic.class);
		query.setParameter("dom", dom);
		return query.getResultList();

	}

	public Long nbtopicpardomain(String dom) {
		TypedQuery<Long> query = em.createQuery("Select count(q) from Topic q " + "where q.domain.name=:dom",
				Long.class);
		query.setParameter("dom", dom);
		return query.getSingleResult();
	}

	@Override
	public Topic getTopicById(int topicId) {
		return em.find(Topic.class, topicId);
	}
	public void updatequestion(Question Q)
	{
		em.merge(Q);
	}
	// @Override
	// public void mettreAjourTopic(Domain domain,Double dureeTopic,Double
	// nombre_question,int topicId)
	// {
	// Topic topic = em.find(Topic.class, topicId);
	// topic.setDomain(domain);
	// topic.setDureeTopic(dureeTopic);
	// //topic.setListequestion(listequestion);
	// topic.setNombre_question(nombre_question);
	//
	// }

	@Override
	public List<Topic> getlist() {
		TypedQuery<Topic> query = em.createQuery("Select t from Topic t ", Topic.class);
		return query.getResultList();

	}
	public Question reponseparidquest(int id)
	{

		TypedQuery<Question> query = em.createQuery("Select q from Question q where q.id=:x ", Question.class);
		query.setParameter("x", id);
		return query.getSingleResult();
	}

	
	public List<Answer> getlistereponsequestions(int id) {

		TypedQuery<Answer> query = em.createQuery("Select q from Answer q where q.question.id=:x ", Answer.class);
		query.setParameter("x", id);
		return query.getResultList();

	}
	public List<Question> getquestions(int id) {

		TypedQuery<Question> query = em.createQuery("Select q from Question q where q.topic.id=:x ", Question.class);
		query.setParameter("x", id);
		return query.getResultList();

	}
	
	public List<Topic> gettopics(int id) {

		TypedQuery<Topic> query = em.createQuery("Select q from Topic q where q.domain.id=:x ", Topic.class);
		query.setParameter("x", id);
		System.out.println(query);
		System.out.println("hhhh");
		return query.getResultList();

	}

	public int ajouterreponse(Answer r,int id)
	{
		Question q=reponseparidquest(id);
		
		em.merge(q);
		em.persist(r);
		return r.getId();
	}

	public void deletetopicById(int topicId) {
		Topic topic = em.find(Topic.class, topicId);
		em.remove(topic);
	}
	


	@Override
	public void mettreAjourTopic(Domain domain, int dureeTopic, int nombre_question, int topicId) {
		 Topic topic = em.find(Topic.class, topicId);
			 topic.setDomain(domain);
			 topic.setDureeTopic(dureeTopic);
			 //topic.setListequestion(listequestion);
			 topic.setNombre_question(nombre_question);
	}
	
	
	@Override
	public Boolean updateTopic(Topic topic) {
		try {
			em.merge(topic);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public List<Topic> findByNamee(String name)
	{
		//List<Produit> list =  em.createQuery("SELECT * FROM produit ").getResultList();
		List<Topic> results = em.createQuery("select e from Topic e WHERE e.nom LiKE  :name  ", Topic.class).setParameter("name", "%"+name+"%").getResultList();
		return results;
	}
	
	
	
	public List<Object> chartTopic()
	{
		List<Object> results = em.createQuery("SELECT type,count(*) as y FROM Domain  GROUP BY type").getResultList();
		//List<Object> results = em.createQuery("SELECT d.name ,count(*) as y FROM Domain d Topic t"+ "where t.domain.id=:d.id").getResultList();
		return results;
	}

}
