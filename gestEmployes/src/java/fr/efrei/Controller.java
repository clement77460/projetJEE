/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Clément
 */
public class Controller extends HttpServlet {
    
    private final DataSources ds=new DataSources();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("process Request");
        
        
        if(request.getParameter("user")==null){
            request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
        }
        
        else{
            switch(request.getParameter("submit")){ //reference vers name="xxx" de l'HTML
                case "Login":
                    this.checkLogin(request, response);
                    break;

            }
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

    private void checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession session=request.getSession();

        if(request.getParameter("user").equals("") || request.getParameter("password").equals("")){
            
            session.setAttribute("errorMessage", "Vous devez renseigner les deux champs");
            request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
            
        }else{
            User input = new User();
            input.setLogin(request.getParameter("user"));
            input.setPwd(request.getParameter("password"));

            session.setAttribute("user", input);
            session.setAttribute("employes", ds.getAllEmployes());

            if(input.isCorrect(ds.getAllUsers())){
                request.getRequestDispatcher("WEB-INF/bienvenue.jsp").forward(request, response);
            }
            else{
                session.setAttribute("errorMessage", "Echec de la connexion! Vérifiez votre login et/ou mot de passe et essayez à nouveau");
                request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
            }
        }
        
    }
}
