package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentTest {
	
	@Autowired
	IEmployeService empl ;
	
	@Autowired
	IEntrepriseService ent ;
	
	@Test
	public void ajouterDepartementTest()
	{
		//ajout departement
		Departement departement = new Departement("Dev");
		//test nom departement non null
		Assert.assertNotNull("Le nom du departement ne peut pas etre null", departement.getName());
		//Ajouter dep
		int depId = ent.ajouterDepartement(departement);
		//Verifier l'ajout de departement
		assertThat(depId).isGreaterThan(0);
	}
	
	@Test 
	public void deleteDepartementByIdTest()
	{
		int departementId = 10;
		ent.deleteDepartementById(departementId);
	}
	
	@Test
	public void affecterDepartementAEntrepriseTest()
	{
		ent.affecterDepartementAEntreprise(12,1);
	}
	
	@Test
	public void getAllDepartementsNamesByEntrepriseTest()
	{
		int entrepriseId=1;
		List<String> aux = ent.getAllDepartementsNamesByEntreprise(entrepriseId);
		assertThat(aux).size().isGreaterThan(0);
	}

}
