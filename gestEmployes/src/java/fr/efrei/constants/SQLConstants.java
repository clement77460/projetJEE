/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.constants;

/**
 *
 * @author Clément
 */
public final class SQLConstants {
    
    public final static String INSERT_REQUEST="INSERT INTO EMPLOYES" 
		+ "( NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES " 
		+ "(?,?,?,?,?,?,?,?,?)";
    
    public final static String UPDATE_TABLE_SQL = "update EMPLOYES"
		+ " set NOM=?,PRENOM=?,TELDOM=?,TELPORT=?,TELPRO=?,ADRESSE=?,CODEPOSTAL=?,VILLE=?,EMAIL=?"
		+ " where id=?";
    
    public final static String DELETE_QUERY_EMPLOYE="delete from EMPLOYES where id=?";
    
    public final static String SELECT_ALL_USERS="select * from IDENTIFIANTS";
    public final static String SELECT_ALL_EMPLOYES="select * from EMPLOYES";
    public final static String SELECT_SPECIFIC_EMPLOYE="select * from EMPLOYES where id=?";
    
}
