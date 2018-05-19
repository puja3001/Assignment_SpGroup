/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Relationships;
import model.Users;

/**
 *
 * @author agarwal.puja
 */

@Stateless
public class UserBean {
    
    @PersistenceContext private EntityManager em;
    
    public Users findUser(String email){
        Users user = em.find(Users.class, email);
        return user;
    }
    
    public ArrayList<String> getAllUsers(String email){
        Users user = em.find(Users.class, email);
        Collection<Relationships> relationships1 = user.getRelationshipsCollection();
        Collection<Relationships> relationships2 = user.getRelationshipsCollection1();
        ArrayList<String> friends = new ArrayList<>();
        relationships1.stream().forEach((relation) -> {
            if(relation.getStatus().equals("friends")){
                friends.add(relation.getUsers1().getEmail());
            }
         });
        relationships2.stream().forEach((relation) -> {
            if(relation.getStatus().equals("friends")){
                friends.add(relation.getUsers().getEmail());
            }
         });
        return friends;
    }
    
}
