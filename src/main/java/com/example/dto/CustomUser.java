package com.example.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomUser extends User {
    
    String username;
    String password;
    Collection<GrantedAuthority> authorities;
    String phone;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(
        String username, 
        String password, 
        Collection<GrantedAuthority> authorities,
        String phone
        ) {
            super(username, password, authorities);
            System.out.println("C");
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.phone = phone;
        
    }

}
