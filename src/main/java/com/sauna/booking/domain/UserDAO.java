package com.sauna.booking.domain;

import java.util.List;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@Configuration
@EntityScan(basePackageClasses = UserDAO.class)
@EnableJpaRepositories(basePackageClasses = UserDAO.class)
public interface UserDAO extends CrudRepository<User, Integer> {
	
	@RestResource(path="findByEmail", rel="findByEmail")
	User findByEmail(String name);
	
	User findById(Long id);
	
	public List<User> findAll();
}
