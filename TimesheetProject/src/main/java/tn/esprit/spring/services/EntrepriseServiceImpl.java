package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	DepartementRepository deptRepository;
	
	private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);
	
	List<String> depNames = new ArrayList<>();

	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		try {
            l.info("Ajout du Departement");
			l.debug("je vais ajouter un departement : ");
			Departement departement = deptRepository.save(dep);
			l.debug("je viens  de finir l'ajout d'un departement :%s", departement);
			l.info("departement ajouté sans problèmes");
		} catch (Exception e) {
			l.error(String.format("Erreur lors de l'ajout d'un departement :%s", e.getMessage()));
		}
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		try {
			l.info("Affectation du departement a une entreprise :");
			l.debug("Selection de l'entreprise");
		    Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
		    l.debug(String.format("l'entreprise selectionnée :%s " , entrepriseManagedEntity));
			l.debug("Selection du departement");
			Departement depManagedEntity = deptRepository.findById(depId).orElse(null);
			if(depManagedEntity != null){
			l.debug("Departement selectionné :%s", depManagedEntity);
			l.debug("Affecter departement à l'entreprise : ");
			depManagedEntity.setEntreprise(entrepriseManagedEntity);
			deptRepository.save(depManagedEntity);
			l.info("Departement affecté à l'entreprise sans problèmes");
			}
			else{
				l.info("Departement seelctionné n'existe pas");
			}
		} catch (Exception e) {
			l.error("Erreur lors de l'affectation :%s", e.getMessage());
		}
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		try {
			l.debug("Selection de l'entreprise");
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
			l.debug(String.format("l'entreprise selectionnée :%s", entrepriseManagedEntity));
			if(entrepriseManagedEntity != null){
			for(Departement dep : entrepriseManagedEntity.getDepartements()){
				depNames.add(dep.getName());	
			}
			}
			else{
				l.info("Cette entreprise n'existe pas");
			}
		} catch (Exception e) {
			l.error("Erreur de chargement :%s", e.getMessage());
		}
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		Entreprise entreprise = entrepriseRepoistory.findById(entrepriseId).orElse(null);
		if(entreprise != null){
		entrepriseRepoistory.delete(entreprise);	
		}
		else{
			l.info("L'entreprise seelctionnée n'existe pas");
		}
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		try {
            l.info("Suppession Departement");
			l.debug("Selection du departement à supprimer");
			Departement departementManaged = deptRepository.findById(depId).orElse(null);
			if(departementManaged != null){
			l.debug("Supprimer le departement");
			deptRepository.delete(departementManaged);	
			l.info("Departement est supprimer sans problèmes");
			}
			else{
				l.info("Le departement selectionné n'existe pas");
			}
		} catch (Exception e) {
			l.error("Erreur lors du suppression :%s", e.getMessage());
		}
		
	}

	public Entreprise getEntrepriseById(int entrepriseId) {
		return entrepriseRepoistory.findById(entrepriseId).orElse(null);	
	}

}