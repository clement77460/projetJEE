package fr.efrei.constants;

/**
 * Stock toutes les constantes de notre programme
 * @author Clément
 */
public final class Constants {
    
    
    public final static String EMPTY_STRING="";
    
    public final static String ACTION="action";
    public final static String ACTION_CHOOSED="actionChoosed";
    
    public final static String ACTION_LOGIN="Login";
    public final static String ACTION_SUPPRIMER="Supprimer";
    public final static String ACTION_AJOUTER="Ajouter";
    public final static String ACTION_DETAILS="Details";
    public final static String ACTION_VALIDER="Valider";
    public final static String ACTION_GET_LIST="Voir liste";
    public final static String ACTION_DISCONNECT="disconnect";
    
    
    public final static String EMPLOYES="employes";
    public final static String EMPLOYE="employe";
    public final static String EMPLOYE_NOM="nom";
    public final static String EMPLOYE_PRENOM="prenom";
    public final static String EMPLOYE_TEL_DOM="telDom";
    public final static String EMPLOYE_TEL_MOB="telMob";
    public final static String EMPLOYE_TEL_PRO="telPro";
    public final static String EMPLOYE_ADR="adresse";
    public final static String EMPLOYE_CP="codePostal";
    public final static String EMPLOYE_VILLE="ville";
    public final static String EMPLOYE_MAIL="mail";
    
    
    public final static String USER="user";
    public final static String PASSWORD="password";
    
    public final static String ERROR_MESSAGE="errorMessage";
    public final static String ERROR_MESSAGE_EMPLOYE="errorMessageEmploye";
    public final static String ERROR_MESSAGE_FILL_ALL="Vous devez renseigner les deux champs";
    public final static String ERROR_MESSAGE_FAILURE="Echec de la connexion! Vérifiez votre login et/ou mot de passe et essayez à nouveau";
    public final static String ERROR_MESSAGE_FORMAT="Certains champs sont mal formatés ou non remplies: \n "+
                                                    "(+)le numéro de téléphonne doit être composé de 10 chiffres \n"+
                                                    "(+)l'Email de la forme xxx@xxx.xxx";
    public final static String ERROR_MESSAGE_BDD="la connexion avec la base de donnée a échoué";
    
    public final static String TYPE_MESSAGE="typeMessage";
    
    public final static String RADIOS_VALUE="radiosSelected";
    
    public final static String UNIT_NAME="fr.efrei_projetJEEMaven_war_1.0-SNAPSHOTPU";
    
    public final static String REGEX_TEL="^0[0-9]([ .-]?[0-9]{2}){4}";
    public final static String REGEX_CODE_POSTAL="^[0-9]{5}$";
    public final static String REGEX_MAIL="^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]{2,}\\.[a-z]{2,4}$";
    
    
}
