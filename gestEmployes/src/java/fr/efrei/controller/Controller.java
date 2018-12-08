package fr.efrei.controller;

import fr.efrei.model.DataSources;
import fr.efrei.model.Employe;
import fr.efrei.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static fr.efrei.constants.Constants.*; //import des constantes de type action
import static fr.efrei.constants.PathConstants.*; //import des constantes de type chemins

/**
 * Controller principal de notre aplication
 * @author Clément
 */
public class Controller extends HttpServlet {
    
    private final DataSources ds=new DataSources();
    
    private int actionChoosed; //Permet de faire la difference entre la view d'insert et d'update
        
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
            
            case "disconnect":
                request.getRequestDispatcher("WEB-INF/aurevoir.jsp").forward(request, response);
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
            
            session.setAttribute(USER, input);
            

            if(input.isCorrect(ds.getAllUsers())){
                session.setAttribute(EMPLOYES, ds.getAllEmployes());
                request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
            }
            else{
                if(ds.getAllUsers().isEmpty()){
                    session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_BDD);
                }
                else{
                    session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_FAILURE);
                }
                
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
    
    private void displayEmployeDetail(HttpServletRequest request, HttpServletResponse response,String message)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        
        this.actionChoosed=1;
        
       
        session.setAttribute(ACTION_CHOOSED,this.actionChoosed);
        session.setAttribute("errorMessageEmploye",message);
        
        //no format error, we compute employe details with DataBase
        //format error, we display back employe details that were compute by DataBase
        if(message.equals(EMPTY_STRING)){
            
            if(this.computeEmployeWithDataBase(session,response,request)){
                request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
            }else{//DB error while computing
                session.setAttribute("errorMessageEmploye",ERROR_MESSAGE_BDD);
            }
             
        }else{
            session.setAttribute(EMPLOYE,(Employe)session.getAttribute(EMPLOYE));
            
        }
        request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
            
        
        
    }
    
    private boolean computeEmployeWithDataBase(HttpSession session,HttpServletResponse response,HttpServletRequest request)
        throws ServletException, IOException{
        
        Employe emp=ds.getSpecificEmploye(Integer.parseInt(request.getParameter(RADIOS_VALUE)));
        session.setAttribute(EMPLOYE, emp);
        
        return emp != null;
        
    }
    
    private void toInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
     
        Employe emp=this.buildEmploye(request,0,false);
        if(emp==null){//erreur de format
            this.redirectToInsertEmployeView(request, response, ERROR_MESSAGE_FORMAT);
        }
        else{
            boolean hasSucceed=ds.insertEmploye(emp);


            if(hasSucceed){

                this.redirectToEmployesView(request, response,2);

            }
            else{//erreur de BD
                this.redirectToInsertEmployeView(request, response, ERROR_MESSAGE_BDD);
            }
        }
    }
    
    private void toUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        
        Employe emp=(Employe)session.getAttribute(EMPLOYE); 
        emp=this.buildEmploye(request, emp.getId(),false); 
        
        if(emp==null){//erreur de formattage
            this.displayEmployeDetail(request, response, ERROR_MESSAGE_FORMAT);
        }
        
        if(ds.updateEmploye(emp)){
            this.redirectToEmployesView(request, response,2);
        }else{
            this.displayEmployeDetail(request, response, ERROR_MESSAGE_BDD);
        }
       
        
    }
    
    private Employe buildEmploye(HttpServletRequest request,int id,boolean withoutChecking){
        
        if(!withoutChecking){
            if(!checkFormat(request))
            {
                return null;
            }
        }
        
        return new Employe(id,request.getParameter(EMPLOYE_NOM),request.getParameter(EMPLOYE_PRENOM),
                                    request.getParameter(EMPLOYE_TEL_DOM),request.getParameter(EMPLOYE_TEL_MOB),
                                    request.getParameter(EMPLOYE_TEL_PRO),request.getParameter(EMPLOYE_ADR),
                                    request.getParameter(EMPLOYE_CP),request.getParameter(EMPLOYE_VILLE),
                                    request.getParameter(EMPLOYE_MAIL));
    }
    
    
    private boolean checkFormat(HttpServletRequest request){
        String regexTel="^0[0-9]([ .-]?[0-9]{2}){4}";
        String regexMail="^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]{2,}\\.[a-z]{2,4}$";
        String regexCP="^[0-9]{5}$" ;
        
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
        
        if(telDom.matches(regexTel)&&telPro.matches(regexTel)&&telMob.matches(regexTel)) {
        } else {
            return false;
        }
        
        if(mail.matches(regexMail)) {
        } else {
            return false;
        }
        
        if(cp.matches(regexCP)){
        }else{
            return false;
        }
        
        return true;
    }
    
    private void redirectToEmployesView(HttpServletRequest request, HttpServletResponse response,int typeMessage)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        session.setAttribute(EMPLOYES, ds.getAllEmployes());
        request.getSession().setAttribute(TYPE_MESSAGE,typeMessage);
        request.getRequestDispatcher(WELCOME_PATH).forward(request, response);
        
    }
    
    private void redirectToInsertEmployeView(HttpServletRequest request, HttpServletResponse response,String message)
            throws ServletException, IOException{
        
        
        this.actionChoosed=0;
        
        HttpSession session=request.getSession();
        
        session.setAttribute(ACTION_CHOOSED,actionChoosed);
        session.setAttribute("errorMessageEmploye", message);
        
        if(!message.equals(EMPTY_STRING))//cela signifie qu'il s'est trompé donc on sauvegarde ce qu'il a déja ecris
            session.setAttribute(EMPLOYE,this.buildEmploye(request, actionChoosed, true));
        else{
            session.setAttribute(EMPLOYE,null);
        }
        
        request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
    }

        
    
}
