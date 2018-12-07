package fr.efrei.model.dao;


import static fr.efrei.constants.Constants.UNIT_NAME;
import fr.efrei.model.entities.Identifiants;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import fr.efrei.model.entities.IdentifiantsPK;


/**
 * Classe technique pour l'accès au SGBD qui implémente l'interface IdentifiantsDaoLocal
 * (cf interface pour la javadoc)
 * Se réfère à src/main/ressources/persistence.xml pour les données de connexion
 * @author Clément
 */
@Stateless
public class IdentifiantsDao implements IdentifiantsDaoLocal {
    @PersistenceContext(unitName=UNIT_NAME)
    private EntityManager em;
    
    @Override
    public Identifiants getIdentifiants(String id,String mdp) {
        return em.find(Identifiants.class, new IdentifiantsPK(id,mdp));
       
    }

}
