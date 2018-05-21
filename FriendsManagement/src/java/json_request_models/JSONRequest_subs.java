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
public class JSONRequest_subs implements Serializable{
    
    @JsonProperty
    String sender;
    
    @JsonProperty
    String text;
    
    public JSONRequest_subs(){
        
    }
    
    public JSONRequest_subs(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
