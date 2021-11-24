package com.sauna.booking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sauna.booking.domain.User;
import com.sauna.booking.domain.UserDAO;

// User authentication

@Service
public class UserService implements UserDetailsService {
	
	private final UserDAO repository;
	
	@Autowired
	public UserService(UserDAO userRepository) {
		this.repository = userRepository;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   
    	User curruser = this.repository.findByEmail(username);
        /*UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(), 
        		AuthorityUtils.createAuthorityList(curruser.getGroup()));*/
    	if ( null == curruser || !curruser.getEmail().equals(username) ) {
    		throw new UsernameNotFoundException("No user found with given username.");
    	} else {
    		return new CustomUserDetails(curruser);
    	}
    } 
}
