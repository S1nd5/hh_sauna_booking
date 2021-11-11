package com.sauna.booking.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sauna.booking.domain.Reservation;
import com.sauna.booking.domain.ReservationDAO;

@RestController
public class RestAPIController {
	 @Autowired
	 private ReservationDAO repository;
	 
	 public RestAPIController(ReservationDAO repository) {
		 this.repository = repository;
	 }
	 
	// REST API Requests start
	
	@RequestMapping(value="/api/books", method = RequestMethod.GET)
	public @ResponseBody List<Reservation> bookList() {
		return (List<Reservation>) repository.findAll();
	}
	
	@RequestMapping(value="/api/books/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Reservation> findBookRest(@PathVariable("id") Long reservationId) {
		return this.repository.findById(reservationId);
	}
	
	@RequestMapping(value="/api/books/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void deleteBookRest(@PathVariable("id") Long reservationId) {
		this.repository.deleteById(reservationId);
	}
	
	@RequestMapping(value="/api/books", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void addBookRest(@RequestBody Reservation reservation) {
		this.repository.save(reservation);
	}
	
	 /*@RequestMapping(value="/api/books/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public Reservation updateBookRest(@RequestBody Reservation nReservation, @PathVariable Long id) {
	    return repository.findById(id)
	   .map(book -> {
	    	 book.setTitle(nBook.getTitle());
	    	 book.setPrice(nBook.getPrice());
	    	 return repository.save(book);
	    })
	    .orElseGet(() -> {
		    nBook.setId(id);
		    return repository.save(nBook);
	    });
	}*/
}
