/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Relationships;
import model.RelationshipsPK;
import model.Users;

/**
 *
 * @author agarwal.puja
 */

@Stateless
public class RelationshipBean {
    
    @PersistenceContext private EntityManager em;
    
    public Relationships createRelation(Users userone, Users usertwo, String status){
        RelationshipsPK relationPK = new RelationshipsPK();
        relationPK.setUserone(userone.getEmail());
        relationPK.setUsertwo(usertwo.getEmail());
        Relationships relation = new Relationships();
        
        relation.setRelationshipsPK(relationPK);
        relation.setUsers(userone);
        relation.setUsers1(usertwo);
        relation.setStatus(status);
        em.persist(relation);
        return em.merge(relation);
    }
    
    public void updateRelation(Relationships relation){
        em.merge(relation);
    }
    
    public Relationships findRelation(Users userone, Users usertwo){
        RelationshipsPK relationPK = new RelationshipsPK();
        relationPK.setUserone(userone.getEmail());
        relationPK.setUsertwo(usertwo.getEmail());
        Relationships relation = em.find(Relationships.class, relationPK);
        if(relation == null){
            relationPK.setUsertwo(userone.getEmail());
            relationPK.setUserone(usertwo.getEmail());
            relation = em.find(Relationships.class, relationPK); 
        }

        return relation;
    }
    
}
