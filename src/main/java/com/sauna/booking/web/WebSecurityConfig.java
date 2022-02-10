package com.sauna.booking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = WebSecurityConfig.class)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private UserService userDetailsService;
    
    @Autowired
    public WebSecurityConfig(UserService service) {
        this.userDetailsService = service;
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().ignoringAntMatchers("/nocsrf","/api/**")
        .and()
        .authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**").permitAll()
        .and()
        .authorizeRequests().antMatchers("/register", "/registeruser", "/calendar", "/reservations/search", "/api/", "/api/**").permitAll()
        .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/api/**").permitAll()
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
      .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/home")
          .permitAll()
          .and()
      .logout()
          .permitAll();
    }
}