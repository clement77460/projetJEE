package fr.efrei.constants;

/**
 * Stock toutes les constantes relatives à la persistance
 * @author Clément
 */
public final class SQLConstants {
   
    //TABLE IDENTIFIANTS
    public final static String TABLE_IDENTIFIANTS="IDENTIFIANTS";
    public final static String LOGIN="LOGIN";
    public final static String MDP="MDP";
    
    //TABLE EMPLOYES
    public final static String TABLE_EMPLOYES="EMPLOYES";
    public final static String ID="id";
    public final static String NOM="NOM";
    public final static String PRENOM="PRENOM";
    public final static String TELDOM="TELDOM";
    public final static String TELPORT="TELPORT";
    public final static String TELPRO="TELPRO";
    public final static String ADRESSE="ADRESSE";
    public final static String CODEPOSTAL="CODEPOSTAL";
    public final static String VILLE="VILLE";
    public final static String EMAIL="EMAIL";
    
    //QUERY
    public final static String NAME_QUERY_FIND_ALL_EMPLOYES="Employes.findAll";
    public final static String QUERY_FIND_ALL_EMPLOYES="SELECT i FROM Employes i";
   
    
}
