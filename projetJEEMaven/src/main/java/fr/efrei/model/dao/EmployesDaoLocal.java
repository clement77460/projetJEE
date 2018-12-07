package fr.efrei.model.dao;

import fr.efrei.model.entities.Employes;
import java.util.List;
import javax.ejb.Local;

/**
 * 
 * @author Clément
 */
@Local
public interface EmployesDaoLocal {
    
    /**
     * Accéder à tout les employés
     * @return une liste de tout les employés
     */
    List<Employes> getAllEmployes();
    
    /**
     * Suppresion d'un employé
     * @param id PK de l'employé à supprimer
     */
    void deleteSpecificEmploye(int id);
    
    /**
     * Selectionne un employé spécifique
     * @param id PK de l'employé à selectionner
     * @return 
     */
    Employes getEmploye(int id);
    
    /**
     * On ajoute dans notre BDD un nouvel employé
     * @param employe classe possédant toutes les infos de notre employé à ajouter
     */
    void insertEmploye(Employes employe);

    /**
     * On modifie les infos d'un employé dans notre BDD
     * @param employe classe possédant toutes les infos de notre employé à mettre à jour
     */
    void updateEmploye(Employes employe);
    
}
