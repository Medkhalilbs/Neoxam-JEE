package services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;

import entities.Employee;
import entities.EmployeeStatus;
import entities.EvalutaionTest;
/**
 * Session Bean implementation class StorageService
 */
@Stateless
@LocalBean
public class EvaluationTestService {
	@PersistenceContext(unitName = "gestion-resources-humaine-ejb")

	public EntityManager em;
	
	
	public EvaluationTestService() {
		super();
	}


	public void ajouterEval(EvalutaionTest et) {
			em.persist(et);
		
}
}
