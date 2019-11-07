package managedBeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.DonutChartModel;

import interfaces.CritereServiceLocal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import entities.Critere;

@ManagedBean
public class StatBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private DonutChartModel donutModel2;

	@EJB
	private CritereServiceLocal cs;
	private List<Critere> criteres;

	@PostConstruct
	public void init() {
		criteres = cs.getCriteres();
		createDonutModels();
	}

	public DonutChartModel getDonutModel2() {
		return donutModel2;
	}

	private void createDonutModels() {

		donutModel2 = initDonutModel();
		donutModel2.setTitle("Poucentage critère");
		donutModel2.setLegendPosition("e");
		donutModel2.setSliceMargin(5);
		donutModel2.setShowDataLabels(true);
		donutModel2.setDataFormat("value");
		donutModel2.setShadow(false);
	}

	private DonutChartModel initDonutModel() {
		DonutChartModel model = new DonutChartModel();

		Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
		for (Critere critere : criteres) {
			circle1.put(critere.getDescrCritere(), critere.getPourcentage());
		}
		model.addCircle(circle1);

		return model;
	}

}
