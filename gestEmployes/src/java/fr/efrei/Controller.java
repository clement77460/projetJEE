/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static fr.efrei.Constants.*; //import des constantes de type action
import static fr.efrei.PathConstants.*; //import des constantes de type chemins

/**
 *
 * @author Clément
 */
public class Controller extends HttpServlet {
    
    private final DataSources ds=new DataSources();
    
    private int actionChoosed; //0 -> ajouter ... 1->update
    
    //0 -> error MSG en rouge pour bienvenue.jsp et employeView.jsp
    //1 -> Information msg en bleu pour bienvenue.jsp
    //2 -> On affiche plus rien
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String actionToProceed=EMPTY_STRING;
        if(request.getParameter(ACTION)!=null){
            actionToProceed=request.getParameter(ACTION);//reference vers name="xxx" de l'HTML
        }
        
        switch(actionToProceed){ 
            case ACTION_LOGIN:
                this.checkLogin(request, response);
                break;
            case ACTION_SUPPRIMER:
                this.toDelete(request, response);
                break;

            case ACTION_AJOUTER:
                this.redirectToInsertEmployeView(request, response,2);
                break;

            case ACTION_DETAILS:
                this.displayEmployeDetail(request, response);
                break;

            case ACTION_VALIDER:
                this.whichActionWasChoosed(request, response);
                break;

            case ACTION_GET_LIST:
                this.redirectToEmployesView(request, response,2);
                break;
                
            default:
                request.getRequestDispatcher(INDEX_PATH).forward(request, response);
                break;

        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return EMPTY_STRING;
    }

    private void checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession session=request.getSession();

        if(request.getParameter(USER).equals(EMPTY_STRING) || request.getParameter(PASSWORD).equals(EMPTY_STRING)){
            
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_FILL_ALL);
            request.getRequestDispatcher(INDEX_PATH).forward(request, response);
            
        }else{
            User input = new User();
            
            input.setLogin(request.getParameter(USER));
            input.setPwd(request.getParameter(PASSWORD));
            
            session.setAttribute(USER, input);//peut servir si on affiche son nom au dessus du bouton deconnection
            session.setAttribute(EMPLOYES, ds.getAllEmployes());

            if(input.isCorrect(ds.getAllUsers())){
                request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
            }
            else{
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_FAILURE);
                request.getRequestDispatcher(INDEX_PATH).forward(request, response);
            }
        }
        
    }
    
    private void whichActionWasChoosed(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        switch(this.actionChoosed){
            case 0:
                this.toInsert(request, response);
                break;
            
            case 1:
                this.toUpdate(request, response);
                break;
        }
    }
    
    private void toDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        boolean hasSucceed=ds.deleteSpecificEmploye(Integer.parseInt(request.getParameter(RADIOS_VALUE)));

        if(hasSucceed){
            this.redirectToEmployesView(request, response,1);
            
        }
        else{
            
            request.getSession().setAttribute(TYPE_MESSAGE,0);
            request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
        }
    }
    
    private void displayEmployeDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        
        this.actionChoosed=1;
        session.setAttribute(EMPLOYE, ds.getSpecificEmploye(Integer.parseInt(request.getParameter("radiosSelected"))));
        session.setAttribute(ACTION_CHOOSED,this.actionChoosed);
        request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
        
    }
    
    private void toInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        
        boolean hasSucceed=ds.insertEmploye(this.buildEmploye(request,0));
        
        if(hasSucceed){
            
            this.redirectToEmployesView(request, response,2);
            
        }
        else{
            this.redirectToInsertEmployeView(request, response, 0);
        }
    }
    
    private void toUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        Employe emp=(Employe)session.getAttribute(EMPLOYE); //get id from emp
        
        emp=this.buildEmploye(request, emp.getId()); //changing with input informations
        ds.updateEmploye(emp);
       
        this.redirectToEmployesView(request, response,2);
    }
    
    private Employe buildEmploye(HttpServletRequest request,int id){
        
        return new Employe(id,request.getParameter(EMPLOYE_NOM),request.getParameter(EMPLOYE_PRENOM),
                                    request.getParameter(EMPLOYE_TEL_DOM),request.getParameter(EMPLOYE_TEL_MOB),
                                    request.getParameter(EMPLOYE_TEL_PRO),request.getParameter(EMPLOYE_ADR),
                                    request.getParameter(EMPLOYE_CP),request.getParameter(EMPLOYE_VILLE),
                                    request.getParameter(EMPLOYE_MAIL));
    }
    

    private void redirectToEmployesView(HttpServletRequest request, HttpServletResponse response,int typeMessage)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        session.setAttribute(EMPLOYES, ds.getAllEmployes());
        request.getSession().setAttribute(TYPE_MESSAGE,typeMessage);
        request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
        
    }
    
    private void redirectToInsertEmployeView(HttpServletRequest request, HttpServletResponse response,int typeMessage)
            throws ServletException, IOException{
        
        
        this.actionChoosed=0;
        
        HttpSession session=request.getSession();
        session.setAttribute(EMPLOYE,null);
        session.setAttribute(ACTION_CHOOSED,actionChoosed);
        session.setAttribute(TYPE_MESSAGE, typeMessage);
        
        request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
    }

        
    
}
