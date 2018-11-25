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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        switch(request.getParameter("action")){
            case "Supprimer":
                    this.toDelete(request, response);
                    break;
                    
                case "Ajouter":
                    this.actionChoosed=0;
                    request.getSession().setAttribute("employe",null);
                    request.getSession().setAttribute("actionChoosed",actionChoosed);
                    request.getRequestDispatcher("WEB-INF/employeView.jsp").forward(request, response);
                    break;
                    
                case "Details":
                    this.displayEmployeDetail(request, response);
                    break;
                    
                case "Valider":
                    this.whichActionWasChoosed(request, response);
                    break;
                
                case "Voir liste":
                    this.redirectToEmployesView(request, response);
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

            this.redirectToEmployesView(request, response);
            
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

            this.redirectToEmployesView(request, response);
            
        }
        else{
            //display error page
        }
    }
    
    private void toUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        Employe emp=(Employe)session.getAttribute("employe"); //get id from emp
        
        emp=this.buildEmploye(request, emp.getId()); //changing with input informations
        ds.updateEmploye(emp);
       
        this.redirectToEmployesView(request, response);
    }
    
    private Employe buildEmploye(HttpServletRequest request,int id){
        
        return new Employe(id,request.getParameter("nom"),request.getParameter("prenom"),
                                    request.getParameter("telDom"),request.getParameter("telMob"),
                                    request.getParameter("telPro"),request.getParameter("adresse"),
                                    request.getParameter("codePostal"),request.getParameter("ville"),
                                    request.getParameter("mail"));
    }
    
    private void redirectToEmployesView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        session.setAttribute("employes", ds.getAllEmployes());
        request.getRequestDispatcher("WEB-INF/bienvenue.jsp").forward(request, response);
        
    }
}