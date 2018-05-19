/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Followings;
import model.Users;

/**
 *
 * @author agarwal.puja
 */
@Stateless
public class FollowingBean {
    
    @PersistenceContext private EntityManager em;
    
    public ArrayList<String> findFollowersOfUser(Users user){
        TypedQuery<Followings> query = em.createNamedQuery("Followings.findByFUsername", Followings.class);
        query.setParameter("username", user);
        List<Followings> followers = query.getResultList();
        ArrayList<String> subscribers = new ArrayList<>();
        followers.stream().forEach((follower) -> {
            if(follower.getFStatus().equals("subscribed")){
                subscribers.add(follower.getUsername().getEmail());  
            }
       });
        return subscribers;
    }
    
    public void updateFollowing(Followings following){
        em.merge(following);
    }
    
    public void createFollowing(Users userone, Users usertwo, String status){
        Followings following = new Followings();
        following.setUsername(userone);
        following.setFusername(usertwo);
        following.setFStatus(status);
        following.setDatecreated(new Date());
        em.persist(following);
    }
    
    public Followings findFollowing(Users userone, Users usertwo){
        TypedQuery<Followings> query = em.createNamedQuery("Followings.findByUsers", Followings.class);
        query.setParameter("username", userone);
        query.setParameter("fusername", usertwo);
        Followings following = query.getSingleResult();
        return following;
    }
    
}
