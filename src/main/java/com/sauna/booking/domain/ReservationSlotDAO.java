package com.sauna.booking.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@Configuration
@EntityScan(basePackageClasses = ReservationSlotDAO.class)
@EnableJpaRepositories(basePackageClasses = ReservationSlotDAO.class)

public interface ReservationSlotDAO extends CrudRepository<ReservationSlot, Long> {
	@RestResource(path="findById", rel="findById")
		
	Optional<ReservationSlot> findById(Long id);
	
	public List<ReservationSlot> findAllByStatus(int status);
		
	public List<ReservationSlot> findAll();
}
