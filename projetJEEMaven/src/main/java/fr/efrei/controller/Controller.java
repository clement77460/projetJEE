package fr.efrei.controller;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static fr.efrei.constants.Constants.*; //import des constantes de type action
import static fr.efrei.constants.PathConstants.*; //import des constantes de type chemins
import fr.efrei.model.dao.EmployesDaoLocal;
import fr.efrei.model.dao.IdentifiantsDaoLocal;
import fr.efrei.model.entities.Employes;
import fr.efrei.model.entities.Identifiants;
import javax.ejb.EJB;

/**
 * Controller principal de notre application
 * @author Clément
 */
public class Controller extends HttpServlet {
    @EJB
    private IdentifiantsDaoLocal identifiantsDao;
    @EJB
    private EmployesDaoLocal employesDao;
    
    private int actionChoosed; //0 -> ajouter employe ... 1->update employe
    
    
    /**
     * redirige chaque appel GET et SET vers les action dédiées
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */ 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
        String actionToProceed=EMPTY_STRING;
        if(request.getParameter(ACTION)!=null){
            actionToProceed=request.getParameter(ACTION);//reference vers name=".." de l'HTML
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
            
            case ACTION_DISCONNECT:
                request.getRequestDispatcher(DISCONNECT_VIEW).forward(request, response);
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
    
    
    /**
     * Vérifie si l'identifiant correspond bien à un utilisateur
     * puis redirige vers la liste des employés si c'est correct
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession session=request.getSession();
        
        
        if(request.getParameter(USER).equals(EMPTY_STRING) || request.getParameter(PASSWORD).equals(EMPTY_STRING)){
            
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_FILL_ALL);
            request.getRequestDispatcher(INDEX_PATH).forward(request, response);
            
        }else{
            
            Identifiants identifiant=identifiantsDao.getIdentifiants(request.getParameter(USER),request.getParameter(PASSWORD));
            

            if(identifiant!=null){
                session.setAttribute(USER, identifiant);
                session.setAttribute(EMPLOYES,employesDao.getAllEmployes() );
                request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
            }
            else{
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_FAILURE);
                request.getRequestDispatcher(INDEX_PATH).forward(request, response);
            }
        }
        
    }
    
    /**
     * Permet de savoir si on insert ou update un employé
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
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
    
    /**
     * Permet de déclencher la suppression d'un employe
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void toDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        boolean hasSucceed=true;
        
        //Error Manager for JPA 
        //Can't reconnect after connection lost
        try{
            employesDao.deleteSpecificEmploye(Integer.parseInt(request.getParameter(RADIOS_VALUE)));
        }catch(javax.ejb.EJBException e){
                hasSucceed=false;
        }    
        if(hasSucceed){
            this.redirectToEmployesView(request, response,1);
            
        }
        else{
            
            request.getSession().setAttribute(TYPE_MESSAGE,0);
            request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
        }
    }
    
    /**
     * Déclenche l'affichage d'un employé
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void displayEmployeDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        
        this.actionChoosed=1;
        session.setAttribute(EMPLOYE, employesDao.getEmploye(Integer.parseInt(request.getParameter("radiosSelected"))));
        session.setAttribute(ACTION_CHOOSED,this.actionChoosed);
        request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
        
    }
    /**
     * Déclenche l'insertion d'un employé
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void toInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        boolean hasSucceed=true;
        employesDao.insertEmploye(this.buildEmploye(request,0));
        
        if(hasSucceed){
            
            this.redirectToEmployesView(request, response,2);
            
        }
        else{
            this.redirectToInsertEmployeView(request, response, 0);
        }
    }
    
    /**
     * déclenche la MaJ d'un employé
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void toUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        Employes emp=(Employes)session.getAttribute(EMPLOYE);
        
        
        employesDao.updateEmploye(this.buildEmploye(request, emp.getId()));

       
        this.redirectToEmployesView(request, response,2);
    }
    
    /**
     * Permet la création d'un objet de type Employes
     * @param request
     * @param id de l'employe à créer
     * @return 
     */
    private Employes buildEmploye(HttpServletRequest request,int id){
        
        return new Employes(id,request.getParameter(EMPLOYE_NOM),request.getParameter(EMPLOYE_PRENOM),
                                    request.getParameter(EMPLOYE_TEL_DOM),request.getParameter(EMPLOYE_TEL_MOB),
                                    request.getParameter(EMPLOYE_TEL_PRO),request.getParameter(EMPLOYE_ADR),
                                    request.getParameter(EMPLOYE_CP),request.getParameter(EMPLOYE_VILLE),
                                    request.getParameter(EMPLOYE_MAIL));
    }
    
    
    //typeMessage
    
    /**
     * Redirige vers la liste des employés et affiche ou non un msg selon le scénario dans lequel on se situe
     * @param request
     * @param response
     * @param typeMessage
     *        Values possible:         //0 -> error MSG en rouge pour bienvenue.jsp et employeView.jsp
     *                                 //1 -> Information msg en bleu pour bienvenue.jsp
     *                                 //2 -> On affiche plus rien
     * @throws ServletException
     * @throws IOException 
     */
    private void redirectToEmployesView(HttpServletRequest request, HttpServletResponse response,int typeMessage)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        session.setAttribute(EMPLOYES, employesDao.getAllEmployes());
        request.getSession().setAttribute(TYPE_MESSAGE,typeMessage);
        request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
        
    }
    
    /**
     * Redirige vers l'insertion d'un employé et affiche un msg d'erreur si échec
     * @param request
     * @param response
     * @param typeMessage
     *        Values possible:         //0 -> error MSG en rouge pour bienvenue.jsp et employeView.jsp
     *                                 //1 -> Information msg en bleu pour bienvenue.jsp
     *                                 //2 -> On affiche plus rien
     * @throws ServletException
     * @throws IOException 
     */
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
