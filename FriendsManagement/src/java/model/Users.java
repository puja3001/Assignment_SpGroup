/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author agarwal.puja
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT u FROM Users u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "Users.findByLastname", query = "SELECT u FROM Users u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "Users.findByGender", query = "SELECT u FROM Users u WHERE u.gender = :gender"),
    @NamedQuery(name = "Users.findByCity", query = "SELECT u FROM Users u WHERE u.city = :city"),
    @NamedQuery(name = "Users.findByCountry", query = "SELECT u FROM Users u WHERE u.country = :country")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 2)
    @Column(name = "gender")
    private String gender;
    @Size(max = 128)
    @Column(name = "city")
    private String city;
    @Size(max = 128)
    @Column(name = "country")
    private String country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userone")
    private Collection<Relationships> relationshipsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usertwo")
    private Collection<Relationships> relationshipsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fusername")
    private Collection<Followings> followingsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Followings> followingsCollection1;

    public Users() {
    }

    public Users(String email) {
        this.email = email;
    }

    public Users(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @XmlTransient
    public Collection<Relationships> getRelationshipsCollection() {
        return relationshipsCollection;
    }

    public void setRelationshipsCollection(Collection<Relationships> relationshipsCollection) {
        this.relationshipsCollection = relationshipsCollection;
    }

    @XmlTransient
    public Collection<Relationships> getRelationshipsCollection1() {
        return relationshipsCollection1;
    }

    public void setRelationshipsCollection1(Collection<Relationships> relationshipsCollection1) {
        this.relationshipsCollection1 = relationshipsCollection1;
    }

    @XmlTransient
    public Collection<Followings> getFollowingsCollection() {
        return followingsCollection;
    }

    public void setFollowingsCollection(Collection<Followings> followingsCollection) {
        this.followingsCollection = followingsCollection;
    }

    @XmlTransient
    public Collection<Followings> getFollowingsCollection1() {
        return followingsCollection1;
    }

    public void setFollowingsCollection1(Collection<Followings> followingsCollection1) {
        this.followingsCollection1 = followingsCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Users[ email=" + email + " ]";
    }
    
}
