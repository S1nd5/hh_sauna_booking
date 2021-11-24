package com.sauna.booking.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sauna.booking.domain.Reservation;
import com.sauna.booking.domain.ReservationDAO;
import com.sauna.booking.domain.ReservationSlot;
import com.sauna.booking.domain.ReservationSlotDAO;
import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;

@RestController
public class RestAPIController {
	
	 @Autowired
	 private final ReservationDAO repository;
	 private final ReservationSlotDAO slotRepository;
	 private final SaunaDAO saunaRepository;
	 
	 public RestAPIController(ReservationDAO repository, ReservationSlotDAO slotRepository, SaunaDAO saunaRepository) {
		 this.repository = repository;
		 this.slotRepository = slotRepository;
		 this.saunaRepository = saunaRepository;
	 }
	 
	// REST API Requests start
	 
	 /* Reservations  that exists */
	 
	 @SuppressWarnings("rawtypes")
	 @RequestMapping(value = "/api/reservations", method = RequestMethod.GET, produces="application/json")
	 @ResponseBody
	 public ResponseEntity<List> getReservations()
	 {
	     List<Reservation> reservations = repository.findAll();
	     HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     if (reservations.size() > 0)
	     {
	         return new ResponseEntity<List>(reservations, headers, HttpStatus.OK);
	     }
	     return new ResponseEntity<List>(null, headers, HttpStatus.OK);
	 }
	 
	@RequestMapping(value="/api/reservations/{id}", method = RequestMethod.GET)
		public @ResponseBody Optional<Reservation> findReservation(@PathVariable("id") Long reservationId) {
		return this.repository.findById(reservationId);
	}
	
	/* Reservation Slots */
	
	 @SuppressWarnings("rawtypes")
	 @RequestMapping(value = "/api/slots", method = RequestMethod.GET, produces="application/json")
	 @ResponseBody
	 public ResponseEntity<List> getReservatioSlots()
	 {
	     List<ReservationSlot> reservations = slotRepository.findAll();
	     HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     if (reservations.size() > 0)
	     {
	         return new ResponseEntity<List>(reservations, headers, HttpStatus.OK);
	     }
	     return new ResponseEntity<List>(null, headers, HttpStatus.OK);
	 }
	 
	@RequestMapping(value="/api/slots/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<ReservationSlot> findReservationSlot(@PathVariable("id") Long slotId) {
		return this.slotRepository.findById(slotId);
	}
	
	/* Saunas */
	
	 @SuppressWarnings("rawtypes")
	 @RequestMapping(value = "/api/saunas", method = RequestMethod.GET, produces="application/json")
	 @ResponseBody
	 public ResponseEntity<List> getReservatioSaunas()
	 {
	     List<Sauna> saunas = saunaRepository.findAll();
	     HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     if (saunas.size() > 0)
	     {
	         return new ResponseEntity<List>(saunas, headers, HttpStatus.OK);
	     }
	     return new ResponseEntity<List>(null, headers, HttpStatus.OK);
	 }
	 
	@RequestMapping(value="/api/saunas/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Sauna> findSauna(@PathVariable("id") Long saunaId) {
		return this.saunaRepository.findById(saunaId);
	}
	
	@RequestMapping(value="/api/saunas", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void addBookRest(@RequestBody Sauna sauna) {
		this.saunaRepository.save(sauna);
	}
	
	@RequestMapping(value="/api/saunas/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void deleteBookRest(@PathVariable("id") Long saunaId) {
		this.saunaRepository.deleteById(saunaId);
	}
}
