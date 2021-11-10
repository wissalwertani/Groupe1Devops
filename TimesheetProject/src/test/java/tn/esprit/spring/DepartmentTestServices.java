package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentTestServices {
	
	private static final Logger L = LogManager.getLogger(DepartmentTestServices.class);
	
	@Autowired
	IEmployeService empl ;
	
	@Autowired
	IEntrepriseService ent ;
	
	@Autowired
	EntrepriseRepository entRep;
	
	@Autowired
	DepartementRepository depRep;
	
	Entreprise entreprise;
	
	@Test
	public void ajouterDepartementTest()
	{
		Departement departement = new Departement ("Devops1");
		int depId = ent.ajouterDepartement(departement);
		Assert.assertTrue(depRep.findById(depId).isPresent() != false);
		Departement d = depRep.findById(depId).orElse(null);
		if(d != null){
			Assert.assertTrue(d.getName().equals("Devops1"));
			L.info("Entreprise added successfully!");
			ent.deleteDepartementById(depId);
		}else{
			L.info("Data not Found");
		}
	}
	
	@Test
	public void affecterDepartementAEntrepriseTest()
	{
		Entreprise entrep = new Entreprise ("entrepriseTest","Test2");
		Departement department = new Departement("Devops2");
		int entreId = ent.ajouterEntreprise(entrep);
		int depId = ent.ajouterDepartement(department);
		L.info("Departement added successfully to Entreprise ");
		ent.affecterDepartementAEntreprise(depId, entreId);
        List<String> result1 = ent.getAllDepartementsNamesByEntreprise(entreId);
		assertThat(result1).containsExactly("Devops2");
		assertThat(result1).size().isEqualTo(1);
		ent.deleteDepartementById(depId);
		ent.deleteEntrepriseById(entreId);
	}
	
	@Test 
	public void deleteDepartementByIdTest()
	{
		Departement dep3 = new Departement ("Devops3");
		int idDepartement = ent.ajouterDepartement(dep3);
		Assert.assertTrue(depRep.findById(idDepartement).isPresent());
		ent.deleteDepartementById(idDepartement);
		Assert.assertFalse(depRep.findById(idDepartement).isPresent());
	}
	
	
	@Test
	public void getAllDepartementsNamesByEntrepriseTest()
	{
		this.entreprise = new Entreprise();
		this.entreprise.setName("entreprise à trouver");
		int idEnt = ent.ajouterEntreprise(this.entreprise);
		Departement dep1 = new Departement();
		dep1.setName("departmentTest 1");
		int depId1 = ent.ajouterDepartement(dep1);
		
		Departement dep2= new Departement();
		dep2.setName("departmentTest 2");
		int depId2 = ent.ajouterDepartement(dep2);
		
		ent.affecterDepartementAEntreprise(depId1, idEnt);
		ent.affecterDepartementAEntreprise(depId2, idEnt);
		
		List<String> result2 = ent.getAllDepartementsNamesByEntreprise(idEnt);
		
		assertThat(result2).containsExactly("departmentTest 1","departmentTest 2");

		assertThat(result2).size().isEqualTo(2);
		ent.deleteDepartementById(depId1);
		ent.deleteDepartementById(depId2);
		ent.deleteEntrepriseById(idEnt);
	}
	
}
