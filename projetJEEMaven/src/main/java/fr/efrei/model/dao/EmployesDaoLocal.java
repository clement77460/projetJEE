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

    List<Employes> getAllEmployes();

    void deleteSpecificEmploye(int id);

    Employes getEmploye(int id);

    void insertEmploye(Employes employe);

    void updateEmploye(Employes employe);
    
}
