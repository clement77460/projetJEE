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
    
    
    /**
     * Vérifie si l'identifiant correspond bien à un utilisateur
     * puis redirige vers la liste des employés si c'est correct
     * S'il n'y a pas d'utilisateurs en BD => problème de connexion au SGBD
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
     * Si hasSucced est à false, affichage indiquant une erreur SGBD
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
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
    
    
    /**
     * Permet d'afficher le détail d'un employé
     * @param request
     * @param response
     * @param message : 
     *      1.vide => on affiche l'employé et le message s'il y'en a un
     *      2.erreur formattage => on affiche l'employé à son état initial et un MSG erreur formattage
     *      3.erreur SGBD => on affiche un MSG erreur SGBD
     * @throws ServletException
     * @throws IOException 
     */
    private void displayEmployeDetail(HttpServletRequest request, HttpServletResponse response,String message)
            throws ServletException, IOException{
        
        HttpSession session=request.getSession();
        
        this.actionChoosed=1;
        
       
        session.setAttribute(ACTION_CHOOSED,this.actionChoosed);
        session.setAttribute("errorMessageEmploye",message);
        
        //no format error, we compute employe details with DataBase
        //format error, we display back employe details that were compute by DataBase
        if(message.equals(EMPTY_STRING)){
            
            if(this.computeEmployeWithDataBase(session,request)){
                request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
            }else{//DB error while computing
                session.setAttribute("errorMessageEmploye",ERROR_MESSAGE_BDD);
            }
             
        }else{
            session.setAttribute(EMPLOYE,(Employe)session.getAttribute(EMPLOYE));
            
        }
        request.getRequestDispatcher(EMPLOYE_VIEW).forward(request, response);
            
        
        
    }
    
    /**
     * Permet de créer un employé et de l'insérer en attribut de session
     * La fabrication se passe mal et l'indique en cas de mauvaise connexion au SGBD
     * @param session
     * @param response
     * @param request
     * @return false: erreur SGBD, true : on a bien recupéré les infos de l'employé
     * @throws ServletException
     * @throws IOException 
     */
    private boolean computeEmployeWithDataBase(HttpSession session,HttpServletRequest request)
        throws ServletException, IOException{
        
        Employe emp=ds.getSpecificEmploye(Integer.parseInt(request.getParameter(RADIOS_VALUE)));
        session.setAttribute(EMPLOYE, emp);
        
        return emp != null;
        
    }
    
    /**
     * Déclenche l'insertion d'un employé dans la BDD après deux controles:
     *      1.Contrôle de la présence de tout les champs et du bon formattage (erreur formattage)
     *      2.Connexion au SGBD effective (erreur_BDD)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
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
    
    /**
     * Déclenche la MaJ d'un employé dans la BDD après deux controles:
     *      1.Contrôle de la présence de tout les champs et du bon formattage (erreur formattage)
     *      2.Connexion au SGBD effective (erreur_BDD)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
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
    
    
    /**
     * Permet la création d'un objet de type Employes
     * @param request
     * @param id de l'employe à créer
     * @param withoutChecking permet de garder l'affichage d'un employé malgrès les erreurs de formattage
     * @return null si l'employé est mal formatté ou un Employe si le formattage est correcte
     */
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
        session.setAttribute(EMPLOYES, ds.getAllEmployes());
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
