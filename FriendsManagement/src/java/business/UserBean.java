/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
