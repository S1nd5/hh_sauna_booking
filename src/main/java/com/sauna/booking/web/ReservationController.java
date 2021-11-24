package com.sauna.booking.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sauna.booking.domain.Reservation;
import com.sauna.booking.domain.ReservationDAO;
import com.sauna.booking.domain.ReservationSlot;
import com.sauna.booking.domain.ReservationSlotDAO;
import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;
import com.sauna.booking.domain.User;
import com.sauna.booking.domain.UserDAO;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  

@SuppressWarnings("unused")
@Controller
public class ReservationController {
	
	@Autowired
	private ReservationSlotDAO slotRepository;
	private ReservationDAO reservationRepository;
	private SaunaDAO saunaRepository;
	private UserDAO userRepository;
	
	@Autowired
	public ReservationController(ReservationSlotDAO slotRepository, ReservationDAO reservationRepository, UserDAO userRepository, SaunaDAO saunaRepository) {
		this.slotRepository = slotRepository;
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
		this.saunaRepository = saunaRepository;
	}
	
	/* Admin Functions only for (admin-group) */
	
	@RequestMapping(value="/addslot", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addReservation(Model model, RedirectAttributes redirAttrs) {
		ReservationSlot slot = new ReservationSlot();
		List<Sauna> saunas = saunaRepository.findAll();
		model.addAttribute("slot", slot);
		model.addAttribute("saunas", saunas);
		return "addslot";
	}
	
	@RequestMapping(value="/reservations/modify/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editReservation (@PathVariable(value="id") Long id, Model model, RedirectAttributes redirAttrs) throws ParseException {
		Optional<ReservationSlot> givenSlot = slotRepository.findById(id);
		if ( givenSlot.isPresent()) {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ReservationSlot thisReservation = givenSlot.get();
	        List<Sauna> saunas = saunaRepository.findAll();
	        model.addAttribute("saunas", saunas);
	        model.addAttribute("slot", thisReservation);
	        model.addAttribute("fullname", userDetails.getFirstname());
	        return "editslot";
		} else {
			redirAttrs.addFlashAttribute("error", "Vuoron hakeminen epäonnistui tuntemattomasta syystä.");
			return "redirect:/home";
		}
	}
	
	@RequestMapping(value="/reservations/remove/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String searchReservation (@PathVariable(value="id") Long id, Model model, RedirectAttributes redirAttrs) throws ParseException {
		Optional<ReservationSlot> slot = slotRepository.findById(id);
		if ( slot.isPresent()) {
			Reservation res = reservationRepository.findBySlot(slot.get());
			reservationRepository.delete(res);
			slotRepository.deleteById(id);
			redirAttrs.addFlashAttribute("success", "Vuoropaikka " + id +  " poistettu järjestelmästä.");
			return "redirect:/home";
		} else {
			redirAttrs.addFlashAttribute("error", "Vuoropaikaa ei löytynyt järjestelmästä.");
			return "redirect:/home";
		}
	}
	
	@PostMapping(value="/saveslot")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveReservationSlot(@ModelAttribute ReservationSlot slot, RedirectAttributes redirAttrs) throws ParseException {
		LocalDateTime date = slot.getDatetime().withMinute(0).withSecond(0).withNano(0);
		List<ReservationSlot> slots = slotRepository.findAllByDatetimeAndSauna(date, slot.getSauna());
		if ( slots.size() > 0 ) {
			Long id = slots.get(0).getId();
			if ( slot.getId() == id) {
				slot.setDatetime(date);
				slotRepository.save(slot);
				redirAttrs.addFlashAttribute("success", "Vuoro tallennettu onnistuneesti järjestelmään.");
				return "redirect:/home"; 
			} else {
				redirAttrs.addFlashAttribute("error", "Samalle ajankohdalle on jo olemassa rekisteröity vuoro.");
				return "redirect:/home"; 
			}
		} else {
			slot.setDatetime(date);
			slotRepository.save(slot);
			redirAttrs.addFlashAttribute("success", "Vuoro tallennettu onnistuneesti järjestelmään.");
			return "redirect:/home"; 
		}
	}
	
	@RequestMapping(value="/management", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String allReservations(Model model, RedirectAttributes redirAttrs) {
        List<Sauna> saunas = saunaRepository.findAll();
        List<Reservation> reservations = reservationRepository.findAll();
		List<ReservationSlot> slots = slotRepository.findAll();
        model.addAttribute("saunas", saunas);
        model.addAttribute("slots", slots);
        model.addAttribute("reservations", reservations);
		return "reservations";
	}
	
	/* General functions for handling reservations (user/admin): booking, cancelling, searching, extend) */
	
	@RequestMapping(value="/reservations/search", method = RequestMethod.GET)
	public String searchReservation (@PathVariable(value="date") @RequestParam(value="date") String date, @PathVariable(value="time") @RequestParam(value="time") String time, @PathVariable(value="space") @RequestParam(value="space") String space, Model model, Principal principal, RedirectAttributes redirAttrs) throws ParseException {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String[] arr = time.split(":");
		int hours = Integer.valueOf(arr[0]);
		LocalDate startDate = LocalDate.parse(date, dateFormatter);
		LocalDateTime startDatetime = startDate.atStartOfDay().withHour(hours).minusMinutes(1);
		LocalDateTime endDatetime = startDate.atStartOfDay().withHour(0).minusMinutes(1).plusDays(1);
		System.out.println("Start time: " + startDatetime.toString() + " -> " + endDatetime.toString());
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = userRepository.findById(userDetails.getUserId());
		if ( user.isPresent()) {
	        List<Sauna> saunas = saunaRepository.findAll();
	        List<Reservation> reservations = reservationRepository.findAllBySubscriber(user.get());
			List<ReservationSlot> freeSlots = slotRepository.findAllByDatetimeGreaterThanAndDatetimeLessThanEqualAndStatusAndSaunaSizeEquals(startDatetime, endDatetime, 0, space);
			System.out.println("Slots found: ");
			System.out.println(freeSlots.toString());
	        model.addAttribute("saunas", saunas);
	        model.addAttribute("slots", freeSlots);
	        model.addAttribute("reservations", reservations);
	        model.addAttribute("givenDate", date);
	        model.addAttribute("givenTime", time);
			return "home"; 
		} else {
			redirAttrs.addFlashAttribute("error", "Hakutoimintoa suorittaessa tapahtui virhe.");
			return "redirect:/home";
		}
	}
	
	@RequestMapping(value="/reservations/book/{id}", method = RequestMethod.GET)
	public String bookReservation(@PathVariable Long id, Model model, RedirectAttributes redirAttrs) {
		Optional<ReservationSlot> reservationSlot = slotRepository.findById(id);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = userRepository.findById(userDetails.getUserId());
		if ( reservationSlot.isPresent()) {
			ReservationSlot targetSlot = reservationSlot.get();
			LocalDateTime timeNow = LocalDateTime.now();
			if ( timeNow.isAfter(targetSlot.getDatetime())) {
				redirAttrs.addFlashAttribute("error", "Vuoron ajankohta on jo mennyt, menneisyydessä olevaa vuoroa ei voi varata.");
				return "redirect:/home";
			}
			User targetUser = user.get();
			Reservation reservation = new Reservation(targetSlot, targetUser);
			reservationRepository.save(reservation);
			targetSlot.setStatus(1);
			slotRepository.save(targetSlot);
			redirAttrs.addFlashAttribute("success", "Vuoro varattu onnistuneesti.");
		} else {
			redirAttrs.addFlashAttribute("error", "Vuoron varaus ei onnistunut.");
		}
		return "redirect:/home";
	}
	
	@RequestMapping(value="/reservations/extend/{id}", method = RequestMethod.GET)
	public String extendReservation (@PathVariable(value="id") Long id, Model model, RedirectAttributes redirAttrs) throws ParseException {
		Optional<Reservation> givenReservation = reservationRepository.findById(id);
		if ( givenReservation.isPresent()) {
			Reservation thisReservation = givenReservation.get();
			ReservationSlot currentSlot = thisReservation.getSlot();
			String space = currentSlot.getSauna().getSize();
			LocalDateTime searchTime = currentSlot.getDatetime().plusHours(1).minusMinutes(1);
			LocalDateTime endTime = currentSlot.getDatetime().withHour(0).plusDays(1);
			List<ReservationSlot> slotsAvailableForExtend = slotRepository.findAllByDatetimeGreaterThanAndDatetimeLessThanEqualAndStatusAndSaunaSizeEquals(searchTime, endTime, 0, space);
	        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("uid", userDetails.getId());
			model.addAttribute("slots", slotsAvailableForExtend);
	        model.addAttribute("reservedSlot", currentSlot);
	        model.addAttribute("currentSubscription", thisReservation);
	        redirAttrs.addFlashAttribute("success", "Vapaita ajankohtia löytyi nykyisen vuorosi jatkamiseksi.");
			return "extend"; 
		} else {
			redirAttrs.addFlashAttribute("error", "Vuorovarauksen hakeminen epäonnistui tuntemattomasta syystä.");
			return "redirect:/home";
		}
	}
	
	@RequestMapping(value="/reservations/cancel/{id}", method = RequestMethod.GET)
	public String cancelReservation(@PathVariable Long id, Model model, RedirectAttributes redirAttrs) {
		Optional<Reservation> reservation = reservationRepository.findById(id);
		Reservation res = reservation.get();
		Optional<ReservationSlot> slot = slotRepository.findById(res.getSlot().getId());
		if ( slot.isPresent() ) {
			ReservationSlot sl = slot.get();
			sl.setStatus(0);
			this.slotRepository.save(sl);
		}
		this.reservationRepository.deleteById(id);
		redirAttrs.addFlashAttribute("success", "Vuoro peruttu onnistuneesti.");
		return "redirect:/home"; 
	}
}
