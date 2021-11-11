package com.sauna.booking.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sauna.booking.domain.User;
import com.sauna.booking.domain.UserDAO;
import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;
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
	
    @RequestMapping(value = "/signup")
    public String addStudent(Model model){
    	//model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }	
    
    @RequestMapping(value="/home")
    public String Home(@AuthenticationPrincipal User principal, Model model) {
    	Long uid = (long) 1;
    	User user = userDAO.findById(uid);
    	System.out.println("UID: " + uid);
    	int status = 0;
        List<Sauna> saunas = saunaDAO.findAll();
        List<ReservationSlot> reservationSlots = reservationSlotDAO.findAllByStatus(status);
        List<Reservation> reservations = reservationDAO.findAllBySubscriber(user);
        model.addAttribute("saunas", saunas);
        model.addAttribute("slots", reservationSlots);
        model.addAttribute("reservations", reservations);
        return "home";
    }
    
    @RequestMapping(value="/users")
    public String CustomerList(Model model) {	
        List<User> users = userDAO.findAll();
        model.addAttribute("customers", users);
        return "userlist";
    }	
    
    @RequestMapping(value = "/add")
    public String addCustomer(Model model){
    	model.addAttribute("user", new User());
        return "register";
    }     
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(User user){
    	userDAO.save(user);
        return "redirect:users";
    } 
}
