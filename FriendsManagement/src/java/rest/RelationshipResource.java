/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import business.Friend;
import business.RelationshipBean;
import business.UserBean;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Relationships;
import model.Users;

/**
 *
 * @author agarwal.puja
 */

@RequestScoped
@Path("/socialapp")
public class RelationshipResource {
    
    @EJB private RelationshipBean relationshipBean;
    @EJB private UserBean userBean;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    private String get(){
        String resource = "<h1>Hello from HTML</h1>";
        return resource;
    }
    
    @GET
    @Path("friends")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    private Response getAllFriends(String email){
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
    
    @GET
    @Path("common")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    private Response getCommonFriends(ArrayList<String> friends){
        String email1 = friends.get(0);
        String email2 = friends.get(1);
        Users userone = userBean.findUser(email1);
        Users usertwo = userBean.findUser(email2);
        
        if(userone == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email1).build();
        }
        if(usertwo == null ){
            return Response.status(Response.Status.NOT_FOUND).entity("User not found for email:" + email2).build();
        }
        
        ArrayList<String> common_friends = relationshipBean.findCommonFriends(userone, usertwo);
        if(common_friends.isEmpty()){
           return Response.status(Response.Status.NO_CONTENT).entity("Given users have no common friends").build(); 
        }
        JsonArrayBuilder friendsBuilder = Json.createArrayBuilder();
        common_friends.stream().forEach((p) -> {
            friendsBuilder.add(p);
         });
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        response.add("friends",friendsBuilder);
        response.add("count",common_friends.size());
        return(Response.ok(response.build()).build());
        
    }
    
    @POST
    @Path("connect")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)    
    private Response addConnection(ArrayList<String> friends) {
        return modifyRelation(friends.get(0), friends.get(1), "friends");            
    }
    
    @POST
    @Path("subscibe")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    private Response subsribeToUpdates(String requestor, String target) {
        return modifyRelation(requestor, target, "subscribed");         
    }
    
    @POST
    @Path("block")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    private Response blockUsers(String requestor, String target) {
        return modifyRelation(requestor, target, "blocked");         
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
        Relationships relation = relationshipBean.findRelation(userone, usertwo);
        if(relation != null && relation.getStatus().equals(status)){
            return Response.status(Response.Status.CONFLICT).entity("User: "+ email1 +" and user: " + email2 +" already " + status).build();
        }
        relationshipBean.createRelation(userone, usertwo, status);
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        return(Response.ok(response.build()).build());
        
    }
    
}
