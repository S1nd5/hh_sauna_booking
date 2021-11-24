package com.sauna.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
import java.time.LocalDateTime;
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
			
			/*User user1 = new User("Testi", "Useri", "user", "$2a$12$fgUkahGWGyWXx8fT08ix8uVOPi7b/gieCVpSoOjytMGHG5E.3Tuue", "USER");
			User user2 = new User("Testi", "Ylläpitäjä", "admin", "$2a$12$5nDhI9lcAjEdlGD4P40.jerKOXuGbWfn/HFIawRuO/yzYKU54y2Ty", "ADMIN");
			userRepository.save(user1);
			userRepository.save(user2);
			
			// Dates
			
	        LocalDateTime date = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
	        
	        LocalDateTime toinenAika = date.withHour(18);
	        LocalDateTime kolmasAika = date.plusHours(19);
	        LocalDateTime neljasAika = date.plusHours(20);
	        
	        LocalDateTime date2 = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
	        LocalDateTime viidesAika = date2.plusDays(1).withHour(18);
	        LocalDateTime kuudesAika = viidesAika.plusHours(1);
	        
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
			
			// Vuorot seur pv.
			reservationSlotRepo.save(new ReservationSlot(neljasAika, 0,0, bSauna));
			
			// Vuorot seur pv.
			reservationSlotRepo.save(new ReservationSlot(viidesAika, 0,0, aSauna));
			
			// Vuorot seur pv.
			reservationSlotRepo.save(new ReservationSlot(kuudesAika, 0,0, aSauna));
			
			// Vuorot pidemmalla pv.
			reservationSlotRepo.save(new ReservationSlot(kuudesAika.plusDays(1).withHour(18), 0,0, aSauna));
			
			// Vuorot pidemmalla pv.
			reservationSlotRepo.save(new ReservationSlot(kuudesAika.plusDays(1).withHour(19), 0,0, aSauna));
			
			// Vuorot pidemmalla pv.
			reservationSlotRepo.save(new ReservationSlot(kuudesAika.plusDays(2).withHour(18), 0,0, aSauna));
			
			// Vuorot pidemmalla pv.
			reservationSlotRepo.save(new ReservationSlot(kuudesAika.plusDays(2).withHour(19), 0,0, aSauna));
			
			// Vuorot pidemmalla pv.
			reservationSlotRepo.save(new ReservationSlot(kuudesAika.plusDays(3).withHour(18), 0,0, bSauna));
			
			// Vuorot pidemmalla pv.
			reservationSlotRepo.save(new ReservationSlot(kuudesAika.plusDays(3).withHour(19), 0,0, bSauna));
			
			// Varaus
			Reservation uusiVaraus = new Reservation(uusiVuoro, user1);
			reservationRepository.save(uusiVaraus);*/
		};
	}
}
