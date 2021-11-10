package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		Optional<Entreprise> entrepriseOpt = entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise = null;
		if (entrepriseOpt.isPresent())
			entreprise = entrepriseOpt.get();
		Optional<Departement> departementOpt = deptRepoistory.findById(depId);
		Departement departement = null;
		if (departementOpt.isPresent())
			departement = departementOpt.get();
		if (departement != null){		
		    departement.setEntreprise(entreprise);
	        deptRepoistory.save(departement);
	        }
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Optional<Entreprise> entrepriseOpt = entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise = null;
		if (entrepriseOpt.isPresent())
			entreprise = entrepriseOpt.get();
		
		List<String> depNames = new ArrayList<>();
		if (entreprise != null)
		    for(Departement dep : entreprise.getDepartements()){
		    	depNames.add(dep.getName());
		    }
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		Optional<Entreprise> entrepriseOpt = entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise = null;
		if (entrepriseOpt.isPresent())
			entreprise = entrepriseOpt.get();
		if (entreprise != null)
		    entrepriseRepoistory.delete(entreprise);	
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		Optional<Departement> departementOpt = deptRepoistory.findById(depId);
		Departement departement = null;
		if (departementOpt.isPresent())
			departement = departementOpt.get();
		if (departement != null)
			deptRepoistory.delete(departement);		
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		Optional<Entreprise> entrepriseOpt = entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise = null;
		if (entrepriseOpt.isPresent())
			entreprise = entrepriseOpt.get();
		return entreprise;
	}

}