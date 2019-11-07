package managedBeans;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import java.util.List;

import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.PieChartModel;

import entities.Topic;
import services.TopicService;
@ManagedBean

public class ChartView  {
	private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    @EJB
	TopicService topicService;
 
    @PostConstruct
    public void init() {
        createPieModels();
    }
 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
     
    public PieChartModel getPieModel2() {
        return pieModel2;
    }
     
    private void createPieModels() {
        createPieModel1();
        createPieModel2();
    }
 
    private void createPieModel1() {
    	List<Topic>topics=topicService.getlist();
        pieModel1 = new PieChartModel();
         for(int i=0;i<topics.size();i++)
         { pieModel1.set(topics.get(i).getDomain().getName(), topicService.nbtopicpardomain(topics.get(i).getDomain().getName()));
        }
         
        pieModel1.setTitle("Simple");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }
     
    private void createPieModel2() {
    	List<Topic>topics=topicService.getlist();
        pieModel2 = new PieChartModel();
         
        for(int i=0;i<topics.size();i++)
        { pieModel2.set(topics.get(i).getDomain().getName(), topicService.nbtopicpardomain(topics.get(i).getDomain().getName()));
       }
        pieModel2.setTitle("Personnalisé");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
        pieModel2.setShadow(false);
    }
}