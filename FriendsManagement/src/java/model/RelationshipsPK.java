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
public class RelationshipsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "userone")
    private String userone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "usertwo")
    private String usertwo;

    public RelationshipsPK() {
    }

    public RelationshipsPK(String userone, String usertwo) {
        this.userone = userone;
        this.usertwo = usertwo;
    }

    public String getUserone() {
        return userone;
    }

    public void setUserone(String userone) {
        this.userone = userone;
    }

    public String getUsertwo() {
        return usertwo;
    }

    public void setUsertwo(String usertwo) {
        this.usertwo = usertwo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userone != null ? userone.hashCode() : 0);
        hash += (usertwo != null ? usertwo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelationshipsPK)) {
            return false;
        }
        RelationshipsPK other = (RelationshipsPK) object;
        if ((this.userone == null && other.userone != null) || (this.userone != null && !this.userone.equals(other.userone))) {
            return false;
        }
        if ((this.usertwo == null && other.usertwo != null) || (this.usertwo != null && !this.usertwo.equals(other.usertwo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RelationshipsPK[ userone=" + userone + ", usertwo=" + usertwo + " ]";
    }
    
}
