package fr.efrei.model;

import java.util.List;

import static fr.efrei.constants.Constants.EMPTY_STRING;

/**
 * Représente notre entité Utilisateur (IDENTIFIANTS dans la BDD)
 * @author Clément
 */
public class User {
    private String login=EMPTY_STRING;
    private String pwd=EMPTY_STRING;

    public User(){
        
    }
    
    /**
     * get Login
     * @return 
     */
    public String getLogin(){
        return login;
    }
    
    /**
     * Set Login
     * @param login 
     */
    public void setLogin(String login){
        this.login=login;
    }
    
    /**
     * get password
     * @return 
     */
    public String getPwd(){
        return pwd;
    }
    /**
     * set password
     * @param password 
     */
    public void setPwd(String password){
        this.pwd=password;
    }
    
    /**
     * Compare un Utilisateur avec une liste d'utilisateurs
     * @param ids
     * @return true si deux utilisateurs sont similaires, false sinon
     */
    public boolean isCorrect(List<User> ids){

        return ids.stream().anyMatch((user) -> (user.login.equals(this.login) && user.pwd.equals(this.pwd)));
        
    }
}
