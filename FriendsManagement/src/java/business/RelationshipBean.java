/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    
    public Relationships create(Users userone, Users usertwo, String status){
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
    
    public Relationships findRelation(Users userone, Users usertwo){
        RelationshipsPK relationPK = new RelationshipsPK();
        relationPK.setUserone(userone.getEmail());
        relationPK.setUsertwo(usertwo.getEmail());
        Relationships relation = em.find(Relationships.class, relationPK);
        return relation;
    }
    
    public ArrayList<String> findCommonFriends(Users userone, Users usertwo){
       String query_string = "SELECT rel1 from (SELECT r1 from Relationships r1 where r1.users = :userone ) rel1 INNER JOIN (select r2 from Relationships r2 where r2.users = :usertwo ) rel2 ON rel1.user1 = rel2.users1";
       TypedQuery<Relationships> query = em.createQuery(query_string, Relationships.class);
       query.setParameter("userone", userone);
       query.setParameter("usertwo", usertwo);
       List<Relationships> result = query.getResultList();
       ArrayList<String> common_friends = new ArrayList<String>();
       result.stream().forEach((relation) -> {
          common_friends.add(relation.getUsers1().getEmail());
       });
    return common_friends;
    }
}
