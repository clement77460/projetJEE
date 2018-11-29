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
import java.sql.PreparedStatement;
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
    
    public void updateEmploye(Employe employe){
        this.openConnection();
        String updateTableSQL = "update EMPLOYES"
		+ " set NOM=?,PRENOM=?,TELDOM=?,TELPORT=?,TELPRO=?,ADRESSE=?,CODEPOSTAL=?,VILLE=?,EMAIL=?"
		+ " where id=?";
        try {
            PreparedStatement preparedStatement = dbConn.prepareStatement(updateTableSQL);
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
                    
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
            this.closeConnection();
        }
        
        this.closeConnection();
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
    
    public Employe getSpecificEmploye(int id){
        this.openConnection();
        
        ResultSet rs;
        Employe emp=new Employe();
        
        try {
            rs= this.setQuery("select * from EMPLOYES where id="+id);
            
            rs.next();
            
            emp.setId(rs.getInt("id"));
            emp.setAdresse(rs.getString("ADRESSE"));
            emp.setCodePostal(rs.getString("CODEPOSTAL"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setNom(rs.getString("NOM"));
            emp.setPrenom(rs.getString("PRENOM"));
            emp.setTeldom(rs.getString("TELDOM"));
            emp.setTelport(rs.getString("TELPORT"));
            emp.setTelpro(rs.getString("TELPRO"));
            emp.setVille(rs.getString("VILLE"));
            
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.closeConnection();
        return emp;
        
    }
    
    public boolean deleteSpecificEmploye(int id){
        if(!this.openConnection())
            return false;

        boolean hasSucceed=this.executeQuery(id);
        this.closeConnection();
        return hasSucceed;
        
    }
    
    public boolean insertEmploye(Employe employe){
        if(!this.openConnection())
            return false;
        
        String insertTableSQL = "INSERT INTO EMPLOYES"
		+ "( NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES"
		+ "(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = dbConn.prepareStatement(insertTableSQL);
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
            System.out.println("lol");
            return false;
        }
        
        this.closeConnection();
        return true;
    }
    
    public List<Employe> getAllEmployes(){
        this.openConnection();
        
        ResultSet rs;
        List<Employe> ids;
        ids = new ArrayList<Employe>();
        
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
  
    
    private boolean executeQuery(int id){
        try {
            stmt.execute("delete from EMPLOYES where id="+id);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    private void closeConnection(){

        try {
            stmt.close();
            dbConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean openConnection(){
        try {
            this.dbConn= DriverManager.getConnection(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPwd"));
            this.stmt= dbConn.createStatement();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataSources.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
