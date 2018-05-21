/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Followings;
import model.FollowingsPK;
import model.Users;

/**
 *
 * @author agarwal.puja
 */
@Stateless
public class FollowingBean {
    
    @PersistenceContext private EntityManager em;
    
    public ArrayList<String> findFollowersOfUser(Users user){
        TypedQuery<Followings> query = em.createNamedQuery("Followings.findByFusername", Followings.class);
        query.setParameter("fusername", user);
        List<Followings> followers = query.getResultList();
        ArrayList<String> subscribers = new ArrayList<>();
        followers.stream().forEach((follower) -> {
            if(follower.getFstatus().equals("subscribed")){
                subscribers.add(follower.getUsers().getEmail());  
            }
       });
        return subscribers;
    }
    
    public void updateFollowing(Followings following){
        em.merge(following);
    }
    
    public Followings createFollowing(Users userone, Users usertwo, String status){
        FollowingsPK followingPK = new FollowingsPK();
        followingPK.setUsername(userone.getEmail());
        followingPK.setFusername(usertwo.getEmail());
        
        Followings following = new Followings();
        following.setFollowingsPK(followingPK);
        following.setUsers(userone);
        following.setUsers1(usertwo);
        following.setFstatus(status);
        em.persist(following);
        return em.merge(following);
    }
    
    public Followings findFollowing(Users userone, Users usertwo){
        TypedQuery<Followings> query = em.createNamedQuery("Followings.findByUsers", Followings.class);
        query.setParameter("username", userone);
        query.setParameter("fusername", usertwo);
        Followings following = query.getSingleResult();
        return following;
    }
    
}
