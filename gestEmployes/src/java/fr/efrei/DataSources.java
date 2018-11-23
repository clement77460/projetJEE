/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
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

    private void initProperties(){
        
        prop=new Properties();
        
        
        ClassLoader cl=Thread.currentThread().getContextClassLoader();
        input=cl.getResourceAsStream("fr/efrei/db.properties");
        
        try {
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<User> getAllUsers(){
        this.openConnection();
        ResultSet rs;
        List<User> ids=new ArrayList<User>();
        
        try {
            rs= this.setQuery("select * from IDENTIFIANTS");
            
            User id;
            while(rs.next()){
                id= new User();
                id.setLogin(rs.getString("LOGIN"));
                id.setPwd(rs.getString("MDP"));
                ids.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.closeConnection();
        return ids;
        
    }
    
    public boolean deleteSpecificEmploye(int id){
        this.openConnection();

        boolean hasSucceed=this.executeQuery(id);
        this.closeConnection();
        System.out.println(hasSucceed);
        return hasSucceed;
        
    }
    
    public List<Employe> getAllEmployes(){
        this.openConnection();
        
        ResultSet rs;
        List<Employe> ids=new ArrayList<Employe>();
        
        try {
            rs= this.setQuery("select * from EMPLOYES");
            
            Employe id;
            while(rs.next()){
                id= new Employe();
                id.setId(rs.getInt("id"));
                id.setAdresse(rs.getString("ADRESSE"));
                id.setCodePostal(rs.getString("CODEPOSTAL"));
                id.setEmail(rs.getString("EMAIL"));
                id.setNom(rs.getString("NOM"));
                id.setPrenom(rs.getString("PRENOM"));
                id.setTeldom(rs.getString("TELDOM"));
                id.setTelport(rs.getString("TELPORT"));
                id.setTelpro(rs.getString("TELPRO"));
                id.setVille(rs.getString("VILLE"));
                ids.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.closeConnection();
        return ids;
    }
    
    private ResultSet setQuery(String query){
        
        try {
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private Boolean executeQuery(int id){
        try {
            stmt.execute("delete from EMPLOYES where id="+id);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void closeConnection(){

        try {
            stmt.close();
            dbConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void openConnection(){
        try {
            this.dbConn= DriverManager.getConnection(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPwd"));
            this.stmt= dbConn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initClassLoader(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
