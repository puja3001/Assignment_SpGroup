/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
    @NamedQuery(name = "Relationships.findByRelationshipId", query = "SELECT r FROM Relationships r WHERE r.relationshipId = :relationshipId"),
    @NamedQuery(name = "Relationships.findByStatus", query = "SELECT r FROM Relationships r WHERE r.status = :status"),
    @NamedQuery(name = "Relationships.findByActionuser", query = "SELECT r FROM Relationships r WHERE r.actionuser = :actionuser")})
public class Relationships implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "relationship_id")
    private Integer relationshipId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "actionuser")
    private String actionuser;
    @JoinColumn(name = "userone", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private Users userone;
    @JoinColumn(name = "usertwo", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private Users usertwo;

    public Relationships() {
    }

    public Relationships(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Relationships(Integer relationshipId, String status, String actionuser) {
        this.relationshipId = relationshipId;
        this.status = status;
        this.actionuser = actionuser;
    }

    public Integer getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionuser() {
        return actionuser;
    }

    public void setActionuser(String actionuser) {
        this.actionuser = actionuser;
    }

    public Users getUserone() {
        return userone;
    }

    public void setUserone(Users userone) {
        this.userone = userone;
    }

    public Users getUsertwo() {
        return usertwo;
    }

    public void setUsertwo(Users usertwo) {
        this.usertwo = usertwo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relationshipId != null ? relationshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relationships)) {
            return false;
        }
        Relationships other = (Relationships) object;
        if ((this.relationshipId == null && other.relationshipId != null) || (this.relationshipId != null && !this.relationshipId.equals(other.relationshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Relationships[ relationshipId=" + relationshipId + " ]";
    }
    
}
