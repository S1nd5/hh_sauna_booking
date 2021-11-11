package com.sauna.booking.web;

import java.util.*;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sauna.booking.domain.User;
 
public class SaunaUserDetails implements UserDetails {
    private User user;
     
    public SaunaUserDetails(User user) {
        this.user = user;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Set<Role> roles = user.getGroup();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        /*for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }*/
        
        authorities.add(new SimpleGrantedAuthority(user.getGroup()));
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getEmail();
    }
    
    public Long getId() {
    	return user.getId();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
}