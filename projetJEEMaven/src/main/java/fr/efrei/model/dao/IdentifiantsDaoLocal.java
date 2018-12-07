package fr.efrei.model.dao;

import fr.efrei.model.entities.Identifiants;
import javax.ejb.Local;

/**
 *
 * @author Clément
 */
@Local
public interface IdentifiantsDaoLocal {
    
    /**
     * Renvoit un utilisateur selon son id(login) et mdp
     * @param id 
     * @param mpd
     * @return Identifiants si existant ou null 
     */
    Identifiants getIdentifiants(String id,String mpd);
    
}
