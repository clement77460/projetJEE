package fr.efrei.model.dao;

import static fr.efrei.constants.Constants.UNIT_NAME;
import static fr.efrei.constants.SQLConstants.NAME_QUERY_FIND_ALL_EMPLOYES;
import fr.efrei.model.entities.Employes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Clément
 */
@Stateless
public class EmployesDao implements EmployesDaoLocal {
    @PersistenceContext(unitName=UNIT_NAME)
    private EntityManager em;

    
    @Override
    public List<Employes> getAllEmployes() {
        
        Query findAllQuery= em.createNamedQuery(NAME_QUERY_FIND_ALL_EMPLOYES);
        return findAllQuery.getResultList();
    
    }
    
    
   
    @Override
    public void deleteSpecificEmploye(int id) {
        em.remove(em.find(Employes.class, id));

    }

    @Override
    public Employes getEmploye(int id) {
        
        return em.find(Employes.class, id);
    }

    @Override
    public void insertEmploye(Employes employe) {

        em.persist(employe);
    }

    @Override
    public void updateEmploye(Employes employe) { 
        em.merge(employe);
        
    }
    

    
}
