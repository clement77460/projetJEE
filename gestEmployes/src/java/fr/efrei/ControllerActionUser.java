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

/**
 *
 * @author Clément
 */
public class ControllerActionUser extends HttpServlet {

    private final DataSources ds=new DataSources();
    private int actionChoosed; //0 -> ajouter ... 1->update
    
    
    //0 -> error MSG en rouge pour bienvenue.jsp et employeView.jsp
    //1 -> Information msg en bleu pour bienvenue.jsp
    //2 -> On affiche plus rien
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        switch(request.getParameter("action")){
            case "Supprimer":
                    this.toDelete(request, response);
                    break;
                    
                case "Ajouter":
                    this.redirectToInsertEmployeView(request, response,2);
                    break;
                    
                case "Details":
                    this.displayEmployeDetail(request, response);
                    break;
                    
                case "Valider":
                    this.whichActionWasChoosed(request, response);
                    break;
                
                case "Voir liste":
                    this.redirectToEmployesView(request, response,2);
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
        return "Short description";
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
        
        
        boolean hasSucceed=ds.deleteSpecificEmploye(Integer.parseInt(request.getParameter("radiosSelected")));
        
        if(hasSucceed){
            this.redirectToEmployesView(request, response,1);
            
        }
        else{
            request.getSession().setAttribute("informationMessage",0);
        }
    }
    
    private void displayEmployeDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        
        this.actionChoosed=1;
        session.setAttribute("employe", ds.getSpecificEmploye(Integer.parseInt(request.getParameter("radiosSelected"))));
        session.setAttribute("actionChoosed",this.actionChoosed);
        request.getRequestDispatcher("WEB-INF/employeView.jsp").forward(request, response);
        
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
        Employe emp=(Employe)session.getAttribute("employe"); //get id from emp
        
        emp=this.buildEmploye(request, emp.getId()); //changing with input informations
        ds.updateEmploye(emp);
       
        this.redirectToEmployesView(request, response,2);
    }
    
    private Employe buildEmploye(HttpServletRequest request,int id){
        
        return new Employe(id,request.getParameter("nom"),request.getParameter("prenom"),
                                    request.getParameter("telDom"),request.getParameter("telMob"),
                                    request.getParameter("telPro"),request.getParameter("adresse"),
                                    request.getParameter("codePostal"),request.getParameter("ville"),
                                    request.getParameter("mail"));
    }
    
    /**
    //0 -> error MSG en rouge pour bienvenue.jsp et employeView.jsp
    //1 -> Information msg en bleu pour bienvenue.jsp
    //2 -> On affiche plus rien
    **/
    private void redirectToEmployesView(HttpServletRequest request, HttpServletResponse response,int typeMessage)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        session.setAttribute("employes", ds.getAllEmployes());
        request.getSession().setAttribute("typeMessage",typeMessage);
        request.getRequestDispatcher("WEB-INF/bienvenue.jsp").forward(request, response);
        
    }
    
    private void redirectToInsertEmployeView(HttpServletRequest request, HttpServletResponse response,int typeMessage)
            throws ServletException, IOException{
        
        
        this.actionChoosed=0;
        
        HttpSession session=request.getSession();
        session.setAttribute("employe",null);
        session.setAttribute("actionChoosed",actionChoosed);
        session.setAttribute("typeMessage", typeMessage);
        
        request.getRequestDispatcher("WEB-INF/employeView.jsp").forward(request, response);
    }
}