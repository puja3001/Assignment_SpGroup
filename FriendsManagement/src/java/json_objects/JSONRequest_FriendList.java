/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_objects;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author agarwal.puja
 */

@XmlRootElement
public class JSONRequest_FriendList implements Serializable{
    
    @JsonProperty
    String[] friends = new String[2];
    
    public JSONRequest_FriendList(){
        
    }
    
    public JSONRequest_FriendList(String[] friends){
        this.friends = friends;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }
    
    public String[] getFriends(){
        return friends;
    }
    
}
