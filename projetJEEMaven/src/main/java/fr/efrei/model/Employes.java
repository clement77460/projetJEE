/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Clément
 */
@Entity
@Table(name = "EMPLOYES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employes.findAll", query = "SELECT e FROM Employes e")
    , @NamedQuery(name = "Employes.findById", query = "SELECT e FROM Employes e WHERE e.id = :id")
    , @NamedQuery(name = "Employes.findByNom", query = "SELECT e FROM Employes e WHERE e.nom = :nom")
    , @NamedQuery(name = "Employes.findByPrenom", query = "SELECT e FROM Employes e WHERE e.prenom = :prenom")
    , @NamedQuery(name = "Employes.findByTeldom", query = "SELECT e FROM Employes e WHERE e.teldom = :teldom")
    , @NamedQuery(name = "Employes.findByTelport", query = "SELECT e FROM Employes e WHERE e.telport = :telport")
    , @NamedQuery(name = "Employes.findByTelpro", query = "SELECT e FROM Employes e WHERE e.telpro = :telpro")
    , @NamedQuery(name = "Employes.findByAdresse", query = "SELECT e FROM Employes e WHERE e.adresse = :adresse")
    , @NamedQuery(name = "Employes.findByCodepostal", query = "SELECT e FROM Employes e WHERE e.codepostal = :codepostal")
    , @NamedQuery(name = "Employes.findByVille", query = "SELECT e FROM Employes e WHERE e.ville = :ville")
    , @NamedQuery(name = "Employes.findByEmail", query = "SELECT e FROM Employes e WHERE e.email = :email")})
public class Employes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "NOM")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "PRENOM")
    private String prenom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TELDOM")
    private String teldom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TELPORT")
    private String telport;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TELPRO")
    private String telpro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ADRESSE")
    private String adresse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CODEPOSTAL")
    private String codepostal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "VILLE")
    private String ville;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
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
