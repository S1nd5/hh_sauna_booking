package com.sauna.booking.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sauna.booking.domain.Reservation;
import com.sauna.booking.domain.ReservationDAO;
import com.sauna.booking.domain.ReservationSlot;
import com.sauna.booking.domain.ReservationSlotDAO;

@SuppressWarnings("unused")
@Controller
public class ReservationController {
	@Autowired
	private ReservationSlotDAO slotRepository;
	private ReservationDAO reservationRepository;
	
	@Autowired
	public ReservationController(ReservationSlotDAO slotRepository, ReservationDAO reservationRepository) {
		this.slotRepository = slotRepository;
		this.reservationRepository = reservationRepository;
	}
	
	@RequestMapping(value="/api/reservations/cancel/{id}", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public void removeReservation(@PathVariable("id") Long reservationId, ReservationSlot slot) {
		this.reservationRepository.deleteById(reservationId);
		slot.setStatus(0);
		this.slotRepository.save(slot);
	}
	
	@PostMapping("/reservations/search")
	public void bookReservation (@RequestBody String searchForm, Model model) {
		//this.repository.save(reservation);
		System.out.println(searchForm);
		//return "ok";
	}
}
