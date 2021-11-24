package com.sauna.booking.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@Configuration
@EntityScan(basePackageClasses = ReservationDAO.class)
@EnableJpaRepositories(basePackageClasses = ReservationDAO.class)

public interface ReservationDAO extends CrudRepository<Reservation, Long> {
	@RestResource(path="findById", rel="findById")
	
	Optional<Reservation> findById(Long id);
	
	Reservation findBySlot(ReservationSlot slot);
	
	public List<Reservation> findAllBySubscriber(User subscriber);
	
	public List<Reservation> findAllBySlot(ReservationSlot slot);
	
	public List<Reservation> findAll();
}
