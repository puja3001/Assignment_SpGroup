/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        
    public ArrayList<String> findSubscribers(Users sender, Set<Users> friends){
        ArrayList<String> subscribers = new ArrayList<>();
        friends.stream().forEach((friend) ->{
            Followings following1 = findFollowing(friend,sender);
            Followings following2 = findFollowing(sender,friend);
            if(following1 != null && !following1.getFstatus().equals("blocked")){
                if(following2 != null && !following2.getFstatus().equals("blocked")){
                    subscribers.add(friend.getEmail());  
                }else if(following2 == null){
                    subscribers.add(friend.getEmail());    
                }
            }else if(following1 == null){
                subscribers.add(friend.getEmail());   
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
        FollowingsPK followingPK = new FollowingsPK();
        followingPK.setUsername(userone.getEmail());
        followingPK.setFusername(usertwo.getEmail());
        Followings following = em.find(Followings.class, followingPK);
        return following;
    }
    
}
