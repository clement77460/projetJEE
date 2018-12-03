/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model.dao;


import fr.efrei.model.Identifiants;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import fr.efrei.model.IdentifiantsPK;
/**
 *
 * @author Clément
 */
@Stateless
public class IdentifiantsDao implements IdentifiantsDaoLocal {
    @PersistenceContext(unitName="fr.efrei_projetJEEMaven_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public Identifiants getIdentifiants(String id,String mdp) {
        return em.find(Identifiants.class, new IdentifiantsPK(id,mdp));
       
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
