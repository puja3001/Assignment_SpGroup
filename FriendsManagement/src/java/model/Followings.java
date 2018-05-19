/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Followings.findByFollowingId", query = "SELECT f FROM Followings f WHERE f.followingId = :followingId"),
    @NamedQuery(name = "Followings.findByDatecreated", query = "SELECT f FROM Followings f WHERE f.datecreated = :datecreated")})
public class Followings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "following_id")
    private Integer followingId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datecreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreated;
    @JoinColumn(name = "fusername", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private Users fusername;
    @JoinColumn(name = "username", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private Users username;

    public Followings() {
    }

    public Followings(Integer followingId) {
        this.followingId = followingId;
    }

    public Followings(Integer followingId, Date datecreated) {
        this.followingId = followingId;
        this.datecreated = datecreated;
    }

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Users getFusername() {
        return fusername;
    }

    public void setFusername(Users fusername) {
        this.fusername = fusername;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (followingId != null ? followingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Followings)) {
            return false;
        }
        Followings other = (Followings) object;
        if ((this.followingId == null && other.followingId != null) || (this.followingId != null && !this.followingId.equals(other.followingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Followings[ followingId=" + followingId + " ]";
    }
    
}
