/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model.dao;

import fr.efrei.model.Identifiants;
import javax.ejb.Local;

/**
 *
 * @author Clément
 */
@Local
public interface IdentifiantsDaoLocal {

    Identifiants getIdentifiants(String id);
    
}
