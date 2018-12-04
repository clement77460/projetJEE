/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Clément
 */
@Entity
@Table(name = "EMPLOYES")
@NamedQueries({
    @NamedQuery(name = "Employes.findAll", query = "SELECT i FROM Employes i")})
public class Employes implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @Column(name = "NOM")
    private String nom;
    
    @NotNull
    @Column(name = "PRENOM")
    private String prenom;
    
    @NotNull
    @Column(name = "TELDOM")
    private String teldom;
    
    @NotNull
    @Column(name = "TELPORT")
    private String telport;
    
    @NotNull
    @Column(name = "TELPRO")
    private String telpro;
    
    @NotNull
    @Column(name = "ADRESSE")
    private String adresse;
    
    @NotNull
    @Column(name = "CODEPOSTAL")
    private String codepostal;
    
    @NotNull
    @Column(name = "VILLE")
    private String ville;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    public Employes() {
    }

    public Employes(Integer id) {
        this.id = id;
    }

    public Employes(Integer id, String nom, String prenom, String teldom, String telport, String telpro, String adresse, String codepostal, String ville, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.teldom = teldom;
        this.telport = telport;
        this.telpro = telpro;
        this.adresse = adresse;
        this.codepostal = codepostal;
        this.ville = ville;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTeldom() {
        return teldom;
    }

    public void setTeldom(String teldom) {
        this.teldom = teldom;
    }

    public String getTelport() {
        return telport;
    }

    public void setTelport(String telport) {
        this.telport = telport;
    }

    public String getTelpro() {
        return telpro;
    }

    public void setTelpro(String telpro) {
        this.telpro = telpro;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employes)) {
            return false;
        }
        Employes other = (Employes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.efrei.model.Employes[ id=" + id + " ]";
    }
    
}
