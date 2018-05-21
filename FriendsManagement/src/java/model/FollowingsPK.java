/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author agarwal.puja
 */
@Embeddable
public class FollowingsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "fusername")
    private String fusername;

    public FollowingsPK() {
    }

    public FollowingsPK(String username, String fusername) {
        this.username = username;
        this.fusername = fusername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFusername() {
        return fusername;
    }

    public void setFusername(String fusername) {
        this.fusername = fusername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (fusername != null ? fusername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FollowingsPK)) {
            return false;
        }
        FollowingsPK other = (FollowingsPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.fusername == null && other.fusername != null) || (this.fusername != null && !this.fusername.equals(other.fusername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FollowingsPK[ username=" + username + ", fusername=" + fusername + " ]";
    }
    
}
