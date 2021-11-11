package com.sauna.booking.domain;

import java.util.List;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@Configuration
@EntityScan(basePackageClasses = SaunaDAO.class)
@EnableJpaRepositories(basePackageClasses = SaunaDAO.class)

public interface SaunaDAO extends CrudRepository<Sauna, Integer> {
	@RestResource(path="findById", rel="findById")
		
	Sauna findById(Long id);
		
	public List<Sauna> findAll();
}
