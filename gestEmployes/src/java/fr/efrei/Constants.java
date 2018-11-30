/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

/**
 *
 * @author Clément
 */
public final class Constants {
    public final static String insertRequest="INSERT INTO EMPLOYES" 
		+ "( NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES " 
		+ "(?,?,?,?,?,?,?,?,?)";
}
