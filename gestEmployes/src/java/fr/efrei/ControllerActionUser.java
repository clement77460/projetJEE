/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

import java.io.IOException;
import java.io.PrintWriter;
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        switch(request.getParameter("action")){
            case "Supprimer":
                    this.toDelete(request, response);
                    break;
                    
                case "Ajouter":
                    System.out.println("on ajoute");
                    break;
                    
                case "Details":
                    System.out.println("on detail");
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
    
    private void toDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        
        boolean hasSucceed=ds.deleteSpecificEmploye(Integer.parseInt(request.getParameter("radiosSelected")));
        
        if(hasSucceed){

            session.setAttribute("employes", ds.getAllEmployes());
            request.getRequestDispatcher("WEB-INF/bienvenue.jsp").forward(request, response);
            
        }
    }

}
