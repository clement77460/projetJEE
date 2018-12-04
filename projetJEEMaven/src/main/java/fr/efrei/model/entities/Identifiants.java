/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Clément
 */
@Entity
@Table(name = "IDENTIFIANTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Identifiants.findAll", query = "SELECT i FROM Identifiants i")
    , @NamedQuery(name = "Identifiants.findByLogin", query = "SELECT i FROM Identifiants i WHERE i.identifiantsPK.login = :login")
    , @NamedQuery(name = "Identifiants.findByMdp", query = "SELECT i FROM Identifiants i WHERE i.identifiantsPK.mdp = :mdp")})
public class Identifiants implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IdentifiantsPK identifiantsPK;

    public Identifiants() {
    }

    public Identifiants(IdentifiantsPK identifiantsPK) {
        this.identifiantsPK = identifiantsPK;
    }

    public Identifiants(String login, String mdp) {
        this.identifiantsPK = new IdentifiantsPK(login, mdp);
    }

    public IdentifiantsPK getIdentifiantsPK() {
        return identifiantsPK;
    }

    public void setIdentifiantsPK(IdentifiantsPK identifiantsPK) {
        this.identifiantsPK = identifiantsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identifiantsPK != null ? identifiantsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Identifiants)) {
            return false;
        }
        Identifiants other = (Identifiants) object;
        if ((this.identifiantsPK == null && other.identifiantsPK != null) || (this.identifiantsPK != null && !this.identifiantsPK.equals(other.identifiantsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.efrei.model.Identifiants[ identifiantsPK=" + identifiantsPK + " ]";
    }
    
}
