package com.sauna.booking.web;

import com.sauna.booking.domain.User;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends User implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user;

    public CustomUserDetails(User user) {
        super(user);
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return AuthorityUtils.createAuthorityList(this.getGroup());
        //return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
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
    
    public Long getUserId() {
    	return this.user.getId();
    }
    
    public String getFullname() {
        return this.user.getFirstname() + " " + this.user.getLastname();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }
    
    @Override
    public String getApartment() {
        return this.user.getApartment();
    }
    
    @Override
    public String getPhonenumber() {
        return this.user.getPhonenumber();
    }

    public String getUserDatabase() {
        return "usertable" + Integer.toString((int) (1 + this.user.getId()));
    }

}