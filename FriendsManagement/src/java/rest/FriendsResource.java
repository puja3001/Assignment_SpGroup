/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import json_request_models.JSONRequest_FriendList;
import json_request_models.JSONRequest_request;
import json_request_models.JSONRequest_subs;
import json_request_models.JSONRequest_Email;
import business.FollowingBean;
import business.RelationshipBean;
import business.UserBean;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Followings;
import model.Relationships;
import model.Users;

/**
 *
 * @author agarwal.puja
 */

@RequestScoped
@Path("/socialapp")
public class FriendsResource {
    
    @EJB private RelationshipBean relationshipBean;
    @EJB private UserBean userBean;
    @EJB private FollowingBean followingBean;

    @Resource(mappedName = "concurrent/schedThreadPool") 
    private ManagedScheduledExecutorService executorService;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String get(){            
        String resource = "<h1>Hello from HTML</h1>";
        return resource;
    }
    
    /**
     * To get all friends of the user if user with the email exists
     * @param asyncResponse
     * @param email 
     */
    @POST
    @Path("friends")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void getAllFriends(@Suspended
    final AsyncResponse asyncResponse, JSONRequest_Email email){
        executorService.submit(() -> {
            asyncResponse.resume(doGetAllFriends(email.getEmail()));
        });
    }
    
    private Response doGetAllFriends(String email) {
        Users user = userBean.findUser(email);
        if(user == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email).build();
        }
        ArrayList<String> friends = userBean.getAllUsers(email);
        if(friends.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).entity("User with email: " + email + "has no friends").build();
        }
        JsonArrayBuilder friendsBuilder = Json.createArrayBuilder();
        friends.stream().forEach((p) -> {
            friendsBuilder.add(p);
        });
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        response.add("friends",friendsBuilder);
        response.add("count",friends.size());
        return(Response.ok(response.build()).build());
    }

    /**
     * Gets common friends between given two users
     * @param asyncResponse
     * @param friendList 
     */
    @POST
    @Path("common")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void getCommonFriends(@Suspended final AsyncResponse asyncResponse, JSONRequest_FriendList friendList) {
        executorService.submit(() -> {
            asyncResponse.resume(doGetCommonFriends(friendList.getFriends()));
        });
    }

    private Response doGetCommonFriends(String[] friends) {
        String email1 = friends[0];
        String email2 = friends[1];
        Users userone = userBean.findUser(email1);
        Users usertwo = userBean.findUser(email2);
        
        if(userone == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email1).build();
        }
        if(usertwo == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email2).build();
        }
        
        ArrayList<String> userone_friends = userBean.getAllUsers(email1);
        ArrayList<String> usertwo_friends = userBean.getAllUsers(email2);
        userone_friends.retainAll(usertwo_friends);
        
        if(userone_friends.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).entity("Given users have no common friends").build();
        }
        JsonArrayBuilder friendsBuilder = Json.createArrayBuilder();
        userone_friends.stream().forEach((p) -> {
            friendsBuilder.add(p);
        });
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        response.add("friends",friendsBuilder);
        response.add("count",userone_friends.size());
        return(Response.ok(response.build()).build());
    }

    /**
     * Retrieves all the subscribers of the sender of the topic
     * @param asyncResponse
     * @param request 
     */
    @POST
    @Path("retrieve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void retrieveUsers(@Suspended final AsyncResponse asyncResponse, JSONRequest_subs request) {
        executorService.submit(() -> {
            asyncResponse.resume(doRetrieveUsers(request.getSender(), request.getText()));
        });
    }

    private Response doRetrieveUsers(String sender, String text) {
        
        JsonArrayBuilder subscribersBuilder = Json.createArrayBuilder();
        
        Set<Users> subscribers_to_verify = new HashSet<>();
        Users user = userBean.findUser(sender);
	Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
	while (m.find()) {
            String email = m.group();
	    Users user_sub = userBean.findUser(email);
            if(user_sub != null){
                subscribers_to_verify.add(user_sub);
            }
	}
        
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + sender).build();
        }
        ArrayList<String> relations = userBean.getAllUsers(user.getEmail());
        relations.stream().forEach((p) -> {
            subscribers_to_verify.add(userBean.findUser(p));
        }); 
        
        ArrayList<String> subscribers = followingBean.findSubscribers(user, subscribers_to_verify);
        
        if(subscribers.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).entity("Given user has no valid subsribers").build();
        }
        
        subscribers.stream().forEach((p) -> {
            subscribersBuilder.add(p);
        });
        
        int count = subscribers.size();
        
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        response.add("recipients",subscribersBuilder);
        response.add("count",count);
        return(Response.ok(response.build()).build());
    }

    /**
     * Adds given two users as friends
     * @param asyncResponse
     * @param friends 
     */
    @POST
    @Path("connect")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addConnection(@Suspended final AsyncResponse asyncResponse, JSONRequest_FriendList friends) {
        executorService.submit(() -> {
            asyncResponse.resume(doAddConnection(friends.getFriends()));
        });
    }

    private Response doAddConnection(String[] friends) {
        return connectPeople(friends[0], friends[1], "friends");
    }

    /**
     * Blocks the target user for the requesting user
     * @param asyncResponse
     * @param request 
     */
    @POST
    @Path("block")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void blockUsers(@Suspended final AsyncResponse asyncResponse, JSONRequest_request request) {
        executorService.submit(() -> {
            asyncResponse.resume(doBlockUsers(request.getRequestor(), request.getTarget()));
        });
    }

    private Response doBlockUsers(String requestor, String target) {
        return modifyRelation(requestor, target, "blocked");
    }

    /**
     * Makes the requesting user as the subscriber of the target user
     * @param asyncResponse
     * @param request 
     */
    @POST
    @Path("subscribe")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void subsribeToUpdates(@Suspended final AsyncResponse asyncResponse, JSONRequest_request request) {
        executorService.submit(() -> {
            asyncResponse.resume(doSubsribeToUpdates(request.getRequestor(), request.getTarget()));
        });
    }

    private Response doSubsribeToUpdates(String requestor, String target) {
        return modifyRelation(requestor, target, "subscribed");
    }
    
        private Response modifyRelation(String email1, String email2, String status){
        Users userone = userBean.findUser(email1);
        Users usertwo = userBean.findUser(email2);
        
        if(userone == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email1).build();
        }
        if(usertwo == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email2).build();
        }
        
        Followings following = followingBean.findFollowing(userone, usertwo);
        if(following != null){
            if(following.getFstatus().equals(status)){
             return Response.status(Response.Status.CONFLICT).entity("User: "+ email1 +" and user: " + email2 +" already " + status).build();   
            }else{
                following.setFstatus(status);
                followingBean.updateFollowing(following);
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("success","true");
                return(Response.ok(response.build()).build());
            }      
        }
        followingBean.createFollowing(userone, usertwo, status);
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        return(Response.ok(response.build()).build());
    }
    
    
    private Response connectPeople(String email1, String email2, String status){
        Users userone = userBean.findUser(email1);
        Users usertwo = userBean.findUser(email2);
        
        if(userone == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email1).build();
        }
        if(usertwo == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email2).build();
        }
        Relationships relation = relationshipBean.findRelation(userone, usertwo);
        if(relation != null){
            if(relation.getStatus().equals(status)){
             return Response.status(Response.Status.CONFLICT).entity("User: "+ email1 +" and user: " + email2 +" already exists as - " + status).build();   
            }else{
                relation.setStatus(status);
                relationshipBean.updateRelation(relation);
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("success","true");
                return(Response.ok(response.build()).build());
            }      
        }
        relationshipBean.createRelation(userone, usertwo, status);
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        return(Response.ok(response.build()).build());
        
    }
    
}
