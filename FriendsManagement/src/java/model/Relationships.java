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
@Table(name = "relationships")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relationships.findAll", query = "SELECT r FROM Relationships r"),
    @NamedQuery(name = "Relationships.findByUserone", query = "SELECT r FROM Relationships r WHERE r.relationshipsPK.userone = :userone"),
    @NamedQuery(name = "Relationships.findByUsertwo", query = "SELECT r FROM Relationships r WHERE r.relationshipsPK.usertwo = :usertwo"),
    @NamedQuery(name = "Relationships.findByStatus", query = "SELECT r FROM Relationships r WHERE r.status = :status")})
public class Relationships implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RelationshipsPK relationshipsPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "userone", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "usertwo", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users1;

    public Relationships() {
    }

    public Relationships(RelationshipsPK relationshipsPK) {
        this.relationshipsPK = relationshipsPK;
    }

    public Relationships(RelationshipsPK relationshipsPK, String status) {
        this.relationshipsPK = relationshipsPK;
        this.status = status;
    }

    public Relationships(String userone, String usertwo) {
        this.relationshipsPK = new RelationshipsPK(userone, usertwo);
    }

    public RelationshipsPK getRelationshipsPK() {
        return relationshipsPK;
    }

    public void setRelationshipsPK(RelationshipsPK relationshipsPK) {
        this.relationshipsPK = relationshipsPK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash += (relationshipsPK != null ? relationshipsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relationships)) {
            return false;
        }
        Relationships other = (Relationships) object;
        if ((this.relationshipsPK == null && other.relationshipsPK != null) || (this.relationshipsPK != null && !this.relationshipsPK.equals(other.relationshipsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Relationships[ relationshipsPK=" + relationshipsPK + " ]";
    }
    
}
