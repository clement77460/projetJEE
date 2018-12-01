/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

import java.util.List;

import static fr.efrei.Constants.EMPTY_STRING;
/**
 *
 * @author Clément
 */
public class User {
    private String login=EMPTY_STRING;
    private String pwd=EMPTY_STRING;

    public User(){
        
    }
    
    public String getLogin(){
        return login;
    }
    
    public void setLogin(String login){
        this.login=login;
    }
    
    public String getPwd(){
        return pwd;
    }
    
    public void setPwd(String password){
        this.pwd=password;
    }
    
    public boolean isCorrect(List<User> ids){
        
        for(User user : ids){
            if(user.login.equals(this.login) && user.pwd.equals(this.pwd)){
                return true;
            }
        }
        return false;
        
        //return ids.stream().anyMatch((i) -> (i.getLogin().equals(this.login) && i.getPwd().equals(this.pwd) ));
    }
}
