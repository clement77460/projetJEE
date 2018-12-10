package fr.efrei.model;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fr.efrei.constants.SQLConstants.*;
import static fr.efrei.constants.SqlFieldsConstants.*;
import static fr.efrei.constants.Constants.*;

/**
 * Centralise les accès à la BDD
 * @author Clément
 */
public class DataSources {
    
    Statement stmt;
    InputStream input;
    Properties prop;
    Connection dbConn;
    
    public DataSources(){
        this.initClassLoader();
        this.initProperties(); 
        
    }
    
    /**
     * Permet de charger les properties de notre BDD
     */
    private void initProperties(){
        
        prop=new Properties();
        
        
        ClassLoader cl=Thread.currentThread().getContextClassLoader();
        input=cl.getResourceAsStream("fr/efrei/db.properties");
        
        try {
            prop.load(input);
        } catch (IOException ex) {
        }
    }
    
    /**
     * MaJ d'un employé dans ntore BDD
     * @param employe à MaJ
     * @return false si la connexion à la BDD est impossible
     */
    public boolean updateEmploye(Employe employe){
        if(!this.openConnection()){
            return false;
        }
        
        try {
            PreparedStatement preparedStatement = dbConn.prepareStatement(UPDATE_TABLE_SQL);
            preparedStatement.setString(1,employe.getNom());
            preparedStatement.setString(2,employe.getPrenom());
            preparedStatement.setString(3,employe.getTeldom());
            preparedStatement.setString(4,employe.getTelport());
            preparedStatement.setString(5,employe.getTelpro());
            preparedStatement.setString(6,employe.getAdresse());
            preparedStatement.setString(7,employe.getCodePostal());
            preparedStatement.setString(8,employe.getVille());
            preparedStatement.setString(9,employe.getEmail());
            preparedStatement.setInt(10,employe.getId());
            preparedStatement .executeUpdate();
            
            this.closeConnection();
            
            return true;
            
        } catch (SQLException ex) {
         
            this.closeConnection();
            return false;
        }

    }
    
    /**
     * 
     * @return une liste de tout les IDENTIFIANTS de la BDD
     */
    public List<User> getAllUsers(){
        this.openConnection();
        ResultSet rs;
        List<User> ids=new ArrayList<>();
        
        try {
            rs= this.setQuery(SELECT_ALL_USERS);
            if(rs !=null){
                User id;
                while(rs.next()){
                    id= new User();
                    id.setLogin(rs.getString(IDENTIFIANTS_USER));
                    id.setPwd(rs.getString(IDENTIFIANTS_MDP));
                    ids.add(id);
                }
            }
        } catch (SQLException ex) {
            this.closeConnection();
            return ids;
        }
        
        this.closeConnection();
        return ids;
        
    }
    
    /**
     * Obtenir les infos d'un employé en particulié
     * @param id
     * @return null si la connexion à la BDD est impossible,sinon l'employe en question
     */
    public Employe getSpecificEmploye(int id){
        this.openConnection();
        
        ResultSet rs;
        Employe emp=new Employe();
        
        try {
            rs= this.setPreparedSelectQuery(SELECT_SPECIFIC_EMPLOYE,id);
            if(rs!=null){
                rs.next();

                emp.setId(rs.getInt(EMPLOYES_ID));
                emp.setAdresse(rs.getString(EMPLOYES_ADR));
                emp.setCodePostal(rs.getString(EMPLOYES_CP));
                emp.setEmail(rs.getString(EMPLOYES_EMAIL));
                emp.setNom(rs.getString(EMPLOYES_NOM));
                emp.setPrenom(rs.getString(EMPLOYES_PRENOM));
                emp.setTeldom(rs.getString(EMPLOYES_TELDOM));
                emp.setTelport(rs.getString(EMPLOYES_TELPORT));
                emp.setTelpro(rs.getString(EMPLOYES_TELPRO));
                emp.setVille(rs.getString(EMPLOYES_VILLE));
            }
            else{
                this.closeConnection();
                return null;
            }
            this.closeConnection();
            return emp;
            
        } catch (SQLException ex) {
            this.closeConnection();
            return null;
        }
        
        
        
    }
    
    /**
     * supprimer un employé de la BDD
     * @param id Primary Key dans la BDD
     * @return false si la connexion à la BDD est impossible
     */
    public boolean deleteSpecificEmploye(int id){
        if(!this.openConnection())
            return false;

        boolean hasSucceed=this.executePreparedDeleteQuery(DELETE_QUERY_EMPLOYE,id);
        this.closeConnection();
        return hasSucceed;
        
    }
    
    /**
     * insérer un employé dans la BDD
     * @param employe à insérer
     * @return false si la connexion à la BDD est impossible
     */
    public boolean insertEmploye(Employe employe){
        if(!this.openConnection())
            return false;
        
        
        try {
            PreparedStatement preparedStatement = dbConn.prepareStatement(INSERT_REQUEST);
            preparedStatement.setString(1,employe.getNom());
            preparedStatement.setString(2,employe.getPrenom());
            preparedStatement.setString(3,employe.getTeldom());
            preparedStatement.setString(4,employe.getTelport());
            preparedStatement.setString(5,employe.getTelpro());
            preparedStatement.setString(6,employe.getAdresse());
            preparedStatement.setString(7,employe.getCodePostal());
            preparedStatement.setString(8,employe.getVille());
            preparedStatement.setString(9,employe.getEmail());
            preparedStatement .executeUpdate();
                    
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
            this.closeConnection();
            
            return false;
        }
        
        this.closeConnection();
        return true;
    }
    
    /**
     * 
     * @return une liste de tout les employés
     */
    public List<Employe> getAllEmployes(){
        this.openConnection();
        
        ResultSet rs;
        List<Employe> ids = new ArrayList<>();
        
        try {
            rs= this.setQuery(SELECT_ALL_EMPLOYES);
            
            if(rs!=null){
                Employe id;
                while(rs.next()){
                    id= new Employe();
                    id.setId(rs.getInt(EMPLOYES_ID));
                    id.setAdresse(rs.getString(EMPLOYES_ADR));
                    id.setCodePostal(rs.getString(EMPLOYES_CP));
                    id.setEmail(rs.getString(EMPLOYES_EMAIL));
                    id.setNom(rs.getString(EMPLOYES_NOM));
                    id.setPrenom(rs.getString(EMPLOYES_PRENOM));
                    id.setTeldom(rs.getString(EMPLOYES_TELDOM));
                    id.setTelport(rs.getString(EMPLOYES_TELPORT));
                    id.setTelpro(rs.getString(EMPLOYES_TELPRO));
                    id.setVille(rs.getString(EMPLOYES_VILLE));
                    ids.add(id);
                }
            }
        } catch (SQLException ex) {
                this.closeConnection();
                return ids;

        }
        
        
        this.closeConnection();
        return ids;
    }
    
    /**
     * méthode générique pour l'execution d'une requete
     * @param query
     * @return null si la connexion à la BDD est impossible, sinon le ResultSet
     */
    private ResultSet setQuery(String query){
        
        try {
            if(stmt==null){
                return null;
            }
            
            return stmt.executeQuery(query);
        } catch (SQLException ex) {

        }
        
        return null;
    }
  
    /**
     * méthode générique pour l'execution d'une prepared query sur un ID
     * @param query
     * @param id
     * @return null si la connexion à la BDD est impossible, sinon le ResultSet
     */
    private ResultSet setPreparedSelectQuery(String query,int id){
        try {
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1,id);
            
            return preparedStatement.executeQuery();
            
        } catch (SQLException ex) {
            
            
            return null;
        }  
    }
    
    /**
     * méthode générique pour l'execution d'une prepared query sur un ID
     * @param request
     * @param id
     * @return boolean, true si réussite, sinon false
     */
    private boolean executePreparedDeleteQuery(String request,int id){
        try {
            PreparedStatement preparedStatement = dbConn.prepareStatement(request);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            
            return false;
        }
        
    }
    
    /**
     * Fermeture d'une connexion vers la BDD
     * 
     */
    private void closeConnection(){

        try {
            if(stmt!=null && dbConn!=null){
                stmt.close();
                dbConn.close();
            }
        } catch (SQLException ex) {
            
        }
    }
    
    /**
     * Ouverture d'une connexion vers la BDD
     * @return false si échec, true sinon
     */
    private boolean openConnection(){
        try {
            this.dbConn= DriverManager.getConnection(prop.getProperty(DB_URL_PROPERTIES),prop.getProperty(DB_USER_PROPERTIES),prop.getProperty(DB_PASS_PROPERTIES));
            this.stmt= dbConn.createStatement();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Force l'utilisattion du driver MYSQL
     */
    private void initClassLoader(){
        try {
            Class.forName(DRIVER_MYSQL).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            
        }
        
    }
}
