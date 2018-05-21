/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_request_models;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author agarwal.puja
 */

@XmlRootElement
public class JSONRequest_Email implements Serializable{
    
    @JsonProperty
    String email;

    public JSONRequest_Email(){
        
    }
    
    public JSONRequest_Email(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
