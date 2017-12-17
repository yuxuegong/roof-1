package org.roof.oauth2.controller;

import org.springframework.security.oauth2.common.util.OAuth2Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenglt on 2017/12/4.
 */
public class TokenDto {

    private String grant_type;
    private String scope;
    private String client_id;
    private String client_secret;
    private String username;
    private String password;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        if(client_id !=null){
            map.put(OAuth2Utils.CLIENT_ID ,this.client_id);
        }
        if(scope !=null){
            map.put(OAuth2Utils.SCOPE ,this.scope);
        }
        if(grant_type !=null){
            map.put(OAuth2Utils.GRANT_TYPE ,this.grant_type);
        }
        if(username !=null){
            map.put(OAuth2Utils.CLIENT_ID ,this.username);
            map.put("username" ,this.username);
        }
        if(password !=null){
            map.put("password" ,this.password);
        }
        if(client_secret != null){
            map.put("client_secret",this.client_secret);

        }

        return map;
    }
}
