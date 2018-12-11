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
            actionToProceed=request.getParameter(ACTION);//reference vers name="xxx" de l'HTML
        }else{//un utilisateur arrive sur index, on verifie s'il a une session
            if(request.getSession().getAttribute(USER)!=null){//il a une session car il ne s'est pas déco
                actionToProceed=ACTION_GET_LIST;
            }
        }

        if(request.getSession().getAttribute(USER)==null && 
                !(actionToProceed.equals(EMPTY_STRING)||actionToProceed.equals(ACTION_LOGIN)))
        {
            //l'utilisateur s'est deconnecté d'une autre session. 
            //il perd donc automatiquement tte ces sessions !
            
            actionToProceed=EMPTY_STRING;
            
        }
        
        switch(actionToProceed){ 
            case ACTION_LOGIN:
                this.checkLogin(request, response);
                break;
            case ACTION_SUPPRIMER:
                this.toDelete(request, response);
                break;

            case ACTION_AJOUTER:
                this.redirectToInsertEmployeView(request, response,EMPTY_STRING);
                break;

            case ACTION_DETAILS:
                this.displayEmployeDetail(request, response,EMPTY_STRING);
                break;

            case ACTION_VALIDER:
                this.whichActionWasChoosed(request, response);
                break;

            case ACTION_GET_LIST:
                this.redirectToEmployesView(request, response,2);
                break;
            
            case ACTION_DISCONNECT:
                request.getSession().removeAttribute(USER);
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
            
            request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_FILL_ALL);
            request.getRequestDispatcher(INDEX_PATH).forward(request, response);
            
        }else{
            
            Identifiants identifiant=identifiantsDao.getIdentifiants(request.getParameter(USER),request.getParameter(PASSWORD));
            

            if(identifiant!=null){
                session.setAttribute(USER, identifiant);
                request.setAttribute(EMPLOYES,employesDao.getAllEmployes() );
                request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
            }
            else{
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_FAILURE);
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
            
            request.setAttribute(TYPE_MESSAGE,0);
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
    private void displayEmployeDetail(HttpServletRequest request, HttpServletResponse response,String message)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        
        this.actionChoosed=1;
        
        request.setAttribute(ACTION_CHOOSED,this.actionChoosed);
        request.setAttribute(ERROR_MESSAGE_EMPLOYE,message);
        
        if(message.equals(EMPTY_STRING)){
            session.setAttribute(EMPLOYE,employesDao.getEmploye(Integer.parseInt(request.getParameter(RADIOS_VALUE))));
        }
        else{
            session.setAttribute(EMPLOYE,(Employes)session.getAttribute(EMPLOYE));
        }
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
       
        Employes emp=this.buildEmploye(request,0,false);
        
        if(emp==null){
            this.redirectToInsertEmployeView(request, response, ERROR_MESSAGE_FORMAT);
        }else{
            try{
                employesDao.insertEmploye(emp);
                this.redirectToEmployesView(request, response,2);
            }catch(javax.ejb.EJBException e){
                this.redirectToInsertEmployeView(request, response, ERROR_CREATION_EJB);
            }
            
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
        
        Employes newEmp=this.buildEmploye(request, emp.getId(),false);
        try{
            if(newEmp==null){//erreur de formattage
                session.setAttribute(EMPLOYE, this.buildEmploye(request, emp.getId(),true));
                this.displayEmployeDetail(request, response, ERROR_MESSAGE_FORMAT);

            }else{

                    employesDao.updateEmploye(newEmp);
                    this.redirectToEmployesView(request, response,2);

                    
                }
            }catch(javax.ejb.EJBException e){
                session.setAttribute(EMPLOYE, this.buildEmploye(request, emp.getId(),true));
                this.displayEmployeDetail(request, response,ERROR_CREATION_EJB );
            }
        }
       
        
    
    
    /**
     * Permet la création d'un objet de type Employes en verifiant bien que tout les champs correspondent
     * @param request
     * @param id de l'employe à créer
     * @return null si le formattage ne correspond pas
     */
    private Employes buildEmploye(HttpServletRequest request,int id,boolean withoutChecking){
        if(!withoutChecking){
            if(!checkFormat(request))
            {
                return null;
            }
        }
        return new Employes(id,request.getParameter(EMPLOYE_NOM),request.getParameter(EMPLOYE_PRENOM),
                                    request.getParameter(EMPLOYE_TEL_DOM),request.getParameter(EMPLOYE_TEL_MOB),
                                    request.getParameter(EMPLOYE_TEL_PRO),request.getParameter(EMPLOYE_ADR),
                                    request.getParameter(EMPLOYE_CP),request.getParameter(EMPLOYE_VILLE),
                                    request.getParameter(EMPLOYE_MAIL));
    }
    
    
    
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
        
        request.getSession().removeAttribute(EMPLOYE);
        request.setAttribute(EMPLOYES, employesDao.getAllEmployes());
        request.setAttribute(TYPE_MESSAGE,typeMessage);
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
    private void redirectToInsertEmployeView(HttpServletRequest request, HttpServletResponse response,String message)
            throws ServletException, IOException{
        
        this.actionChoosed=0;
        
        HttpSession session=request.getSession();
        
        request.setAttribute(ACTION_CHOOSED,actionChoosed);
        request.setAttribute(ERROR_MESSAGE_EMPLOYE, message);
        
        if(!message.equals(EMPTY_STRING))//cela signifie qu'il s'est trompé donc on sauvegarde ce qu'il a déja ecris
            session.setAttribute(EMPLOYE,this.buildEmploye(request, actionChoosed, true));
        else{
            session.setAttribute(EMPLOYE,null);
        }
        
        request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
    }

    /**
     * Permet de tester le bon formattage des champs lors de l'insertion / MaJ d'un employé
     * @param request
     * @return true si les tests de formattage sont ok sinon false
     */
    private boolean checkFormat(HttpServletRequest request){    
        String nom=request.getParameter(EMPLOYE_NOM);
        String prenom=request.getParameter(EMPLOYE_PRENOM);
        String telDom=request.getParameter(EMPLOYE_TEL_DOM);
        String telMob=request.getParameter(EMPLOYE_TEL_MOB);
        String telPro=request.getParameter(EMPLOYE_TEL_PRO);
        String adr=request.getParameter(EMPLOYE_ADR);
        String cp=request.getParameter(EMPLOYE_CP);
        String ville=request.getParameter(EMPLOYE_VILLE);
        String mail=request.getParameter(EMPLOYE_MAIL);
        
        if(nom.equals(EMPTY_STRING)||prenom.equals(EMPTY_STRING)||telDom.equals(EMPTY_STRING)||
            telMob.equals(EMPTY_STRING)||telPro.equals(EMPTY_STRING)||adr.equals(EMPTY_STRING)||
            cp.equals(EMPTY_STRING)||ville.equals(EMPTY_STRING)||mail.equals(EMPTY_STRING))
        {
            return false;
        }
        
        if(telDom.matches(REGEX_TEL)&&telPro.matches(REGEX_TEL)&&telMob.matches(REGEX_TEL)) {
        } else {
            return false;
        }
        
        if(mail.matches(REGEX_MAIL)) {
        } else {
            return false;
        }
        
        if(cp.matches(REGEX_CODE_POSTAL)){
        }else{
            return false;
        }
        
        return true;
    }
        
    
}
