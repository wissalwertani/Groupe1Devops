package tn.esprit.spring;



import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratTests {
	

	@Autowired
	IEmployeService empl ;

	
	Contrat contrat = new Contrat();
	Employe e = new Employe("wissal", "wissal", "wissalwissal@esprit.tn",true,Role.INGENIEUR);
		
	
	@Test
	public void testAjouterContrat()
	{
		//ajout contrat
		
		Date d = new Date();
		Contrat contrat = new Contrat(1,d,"CDI",2000);
		//test type contrat not null
				Assert.assertNotNull("Type contrat mustn't be null", contrat.getTypeContrat());
				//test date 
				Assert.assertEquals(contrat.getDateDebut(), d);
		  empl.ajouterContrat(contrat);
		
	}

	@Test
	public void testAffecterContratAEmploye()
	{
		int contratId = 1;
		int employeId=1;
		empl.affecterContratAEmploye(contratId, employeId);
	}
	
	@Test
	public void testDeleteContratById()
	{
		int contratId = 1;
		empl.deleteContratById(contratId);
		
	}
	
	@Test
	public void testdeleteAllContratJPQL()
	{
		empl.deleteAllContratJPQL();
		
	}

}
