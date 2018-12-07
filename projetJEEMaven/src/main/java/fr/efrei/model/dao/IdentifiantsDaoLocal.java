package fr.efrei.model.dao;

import fr.efrei.model.entities.Identifiants;
import javax.ejb.Local;

/**
 *
 * @author Clément
 */
@Local
public interface IdentifiantsDaoLocal {

    Identifiants getIdentifiants(String id,String mpd);
    
}
