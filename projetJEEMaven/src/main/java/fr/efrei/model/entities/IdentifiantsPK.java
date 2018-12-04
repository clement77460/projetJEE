/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Clément
 */
@Embeddable
public class IdentifiantsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "MDP")
    private String mdp;

    public IdentifiantsPK() {
    }

    public IdentifiantsPK(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        hash += (mdp != null ? mdp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdentifiantsPK)) {
            return false;
        }
        IdentifiantsPK other = (IdentifiantsPK) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        if ((this.mdp == null && other.mdp != null) || (this.mdp != null && !this.mdp.equals(other.mdp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.efrei.model.IdentifiantsPK[ login=" + login + ", mdp=" + mdp + " ]";
    }
    
}
