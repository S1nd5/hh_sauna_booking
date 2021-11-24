package com.sauna.booking.web;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;

@Controller
public class SaunaController {
	
	@Autowired
	private SaunaDAO saunaRepository;
	
	@Autowired
	public SaunaController(SaunaDAO saunaRepository) {
		this.saunaRepository = saunaRepository;
	}
	
	@RequestMapping(value="/addsauna", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addReservation(Model model, RedirectAttributes redirAttrs) {
		Sauna sauna = new Sauna();
		model.addAttribute("sauna", sauna);
		return "addsauna";
	}
	
	@PostMapping(value="/savesauna")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveReservationSlot(@ModelAttribute Sauna sauna, RedirectAttributes redirAttrs) throws ParseException {
		saunaRepository.save(sauna);
		redirAttrs.addFlashAttribute("success", "Sauna tallennettu onnistuneesti järjestelmään.");
		return "redirect:/home"; 
	}
}
