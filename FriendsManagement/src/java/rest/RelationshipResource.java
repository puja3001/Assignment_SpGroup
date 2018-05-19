/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import business.Friend;
import business.RelationshipBean;
import business.UserBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
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
@Path("/friends")
public class RelationshipResource {
    
    @EJB private RelationshipBean relationshipBean;
    @EJB private UserBean userBean;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    private String get(){
        String resource = "<h1>Hello from HTML</h1>";
        return resource;
    }
    
    @POST
    @Path("connect")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)    
    private Response addConnection(Friend friends) {
        
        String email1 = friends.getFriends().get(0);
        String email2 = friends.getFriends().get(1);
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
            return Response.status(Response.Status.CONFLICT).entity("Relationship exists").build();
        }
        relationshipBean.create(userone, usertwo,"friends");
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("success","true");
        return(Response.ok(response.build()).build());
            
    }
}
