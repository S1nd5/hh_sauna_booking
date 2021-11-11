package com.sauna.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.sauna.booking.domain.Reservation;
import com.sauna.booking.domain.ReservationDAO;
import com.sauna.booking.domain.ReservationSlot;
import com.sauna.booking.domain.ReservationSlotDAO;
import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;
import com.sauna.booking.domain.User;
import com.sauna.booking.domain.UserDAO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
@SpringBootApplication
@ComponentScan(basePackages = {"com.sauna.booking"})
public class BookingApplication {
	
	@SuppressWarnings("unused")
	@Autowired
	private UserDAO userRepository;
	@SuppressWarnings("unused")
	private SaunaDAO saunaRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(UserDAO userRepository, ReservationDAO reservationRepository, ReservationSlotDAO reservationSlotRepo, SaunaDAO saunaRepository) {
		return (args) -> {
			
			//User data
			
			User user1 = new User("user", "$2a$12$fgUkahGWGyWXx8fT08ix8uVOPi7b/gieCVpSoOjytMGHG5E.3Tuue", "USER");
			User user2 = new User("admin", "$2a$12$5nDhI9lcAjEdlGD4P40.jerKOXuGbWfn/HFIawRuO/yzYKU54y2Ty", "ADMIN");
			userRepository.save(user1);
			userRepository.save(user2);
			
			// Dates
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
	        Date date = new Date();
	        
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
			
			c.add(Calendar.HOUR, 1);
			Date toinenAika = c.getTime();
			
			c.add(Calendar.HOUR, 1);
			Date kolmasAika = c.getTime();
			// Saunas
			
			Sauna bSauna = new Sauna("B-talon sauna", "B-rappu", "Suuri", 15, 1);
			saunaRepository.save(bSauna);
			Sauna aSauna = new Sauna("A-talon sauna", "A-rappu", "Pieni", 5, 0);
			saunaRepository.save(aSauna);
			
			// Vuorot
			ReservationSlot uusiVuoro = new ReservationSlot(date, 0, 1, bSauna);
			reservationSlotRepo.save(uusiVuoro);
			ReservationSlot uusiVuoro2 = new ReservationSlot(toinenAika, 0, 0, aSauna);
			reservationSlotRepo.save(uusiVuoro2);
			ReservationSlot uusiVuoro3 = new ReservationSlot(kolmasAika, 0, 0, aSauna);
			reservationSlotRepo.save(uusiVuoro3);
			
			// Varaus
			Reservation uusiVaraus = new Reservation(uusiVuoro, user1);
			reservationRepository.save(uusiVaraus);
		};
	}
}
