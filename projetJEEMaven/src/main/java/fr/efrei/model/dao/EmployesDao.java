/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model.dao;

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
    @PersistenceContext(unitName="fr.efrei_projetJEEMaven_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<Employes> getAllEmployes() {
        Query findAllQuery= em.createNamedQuery("Employes.findAll");
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
