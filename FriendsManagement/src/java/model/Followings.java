/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author agarwal.puja
 */
@Entity
@Table(name = "followings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Followings.findAll", query = "SELECT f FROM Followings f"),
    @NamedQuery(name = "Followings.findByUsername", query = "SELECT f FROM Followings f WHERE f.followingsPK.username = :username"),
    @NamedQuery(name = "Followings.findByFusername", query = "SELECT f FROM Followings f WHERE f.followingsPK.fusername = :fusername"),
    @NamedQuery(name = "Followings.findByFstatus", query = "SELECT f FROM Followings f WHERE f.fstatus = :fstatus")})
public class Followings implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FollowingsPK followingsPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "fstatus")
    private String fstatus;
    @JoinColumn(name = "fusername", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "username", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users1;

    public Followings() {
    }

    public Followings(FollowingsPK followingsPK) {
        this.followingsPK = followingsPK;
    }

    public Followings(FollowingsPK followingsPK, String fstatus) {
        this.followingsPK = followingsPK;
        this.fstatus = fstatus;
    }

    public Followings(String username, String fusername) {
        this.followingsPK = new FollowingsPK(username, fusername);
    }

    public FollowingsPK getFollowingsPK() {
        return followingsPK;
    }

    public void setFollowingsPK(FollowingsPK followingsPK) {
        this.followingsPK = followingsPK;
    }

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getUsers1() {
        return users1;
    }

    public void setUsers1(Users users1) {
        this.users1 = users1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (followingsPK != null ? followingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Followings)) {
            return false;
        }
        Followings other = (Followings) object;
        if ((this.followingsPK == null && other.followingsPK != null) || (this.followingsPK != null && !this.followingsPK.equals(other.followingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Followings[ followingsPK=" + followingsPK + " ]";
    }
    
}
