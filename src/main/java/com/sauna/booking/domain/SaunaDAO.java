package com.sauna.booking.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@Configuration
@EntityScan(basePackageClasses = SaunaDAO.class)
@EnableJpaRepositories(basePackageClasses = SaunaDAO.class)

public interface SaunaDAO extends CrudRepository<Sauna, Long> {
	@RestResource(path="findById", rel="findById")
		
	Optional<Sauna> findById(Long id);
	
	Sauna findByName(String name);
		
	public List<Sauna> findAll();
}
