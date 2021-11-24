package com.sauna.booking.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sauna.booking.domain.User;
import com.sauna.booking.domain.UserDAO;
import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;
import com.sauna.booking.domain.RegisterForm;
import com.sauna.booking.domain.Reservation;
import com.sauna.booking.domain.ReservationDAO;
import com.sauna.booking.domain.ReservationSlot;
import com.sauna.booking.domain.ReservationSlotDAO;

@Controller
@EnableAutoConfiguration
@ComponentScan("com.sauna.web")
public class UserController {
	
    @Autowired
    private UserDAO userDAO;
    private SaunaDAO saunaDAO;
    private ReservationSlotDAO reservationSlotDAO;
    private ReservationDAO reservationDAO;
    
	@Autowired
	public UserController(UserDAO userRepository, SaunaDAO saunaDAO, ReservationSlotDAO reservationSlotDAO, ReservationDAO reservationDAO) {
		this.userDAO = userRepository;
		this.saunaDAO = saunaDAO;
		this.reservationSlotDAO = reservationSlotDAO;
		this.reservationDAO = reservationDAO;
	}
    
    @RequestMapping("/") 
    public String index() {
    	return "redirect:home";
    }
	
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
    }
    
    @RequestMapping(value="/calendar")
    public String calendar() {	
        return "calendar";
    }	
	
    @RequestMapping(value = "/register")
    public String addStudent(Model model){
    	model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }	
    
    @RequestMapping(value="/home")
    public String Home(@AuthenticationPrincipal User principal, Model model, RedirectAttributes redirAttrs) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = userDAO.findById(userDetails.getUserId());
		if ( user.isPresent() ) {
			LocalDateTime timeNow = LocalDateTime.now();
	        List<Sauna> saunas = saunaDAO.findAll();
	        List<ReservationSlot> reservationSlots = reservationSlotDAO.findAllByStatusAndDatetimeGreaterThanEqual(0, timeNow);
	        List<Reservation> reservations = reservationDAO.findAllBySubscriber(user.get());
	        String fullname = user.get().getFirstname() + " " + user.get().getLastname();
	        model.addAttribute("saunas", saunas);
	        model.addAttribute("slots", reservationSlots);
	        model.addAttribute("reservations", reservations);
	        model.addAttribute("fullname", fullname);
	        return "home";
		} else {
			redirAttrs.addFlashAttribute("error", "Käyttäjätietojen haku epäonnistui.");
			return "redirect:/home";
		}
    }
    
    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // errors during validation
    		if (registerForm.getPassword().equals(registerForm.getPasswordCheck())) { // check that passwords match	
	    		String pwd = registerForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	User newUser = new User();
		    	newUser.setPassword(hashPwd);
		    	newUser.setFirstname(registerForm.getFirstname());
		    	newUser.setLastname(registerForm.getLastname());
		    	newUser.setPhonenumber(registerForm.getPhonenumber());
		    	newUser.setApartment(registerForm.getApartment());
		    	newUser.setEmail(registerForm.getUsername());
		    	newUser.setGroup("USER");
		    	if (userDAO.findByEmail(registerForm.getUsername()) == null) { // Check if user exists
		    		userDAO.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Käyttäjätunnus on jo rekisteröity");    	
	    			return "register";		    		
		    	}
		    	return "redirect:/login";   
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Salasanat eivät täsmää");    	
    			return "register";
    		}
    	}
    	else {
    		return "register";
    	}  	
    }      
}
