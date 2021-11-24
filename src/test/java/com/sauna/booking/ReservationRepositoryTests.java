package com.sauna.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sauna.booking.domain.Reservation;
import com.sauna.booking.domain.ReservationDAO;
import com.sauna.booking.domain.ReservationSlot;
import com.sauna.booking.domain.ReservationSlotDAO;
import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;
import com.sauna.booking.domain.User;
import com.sauna.booking.domain.UserDAO;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTests {
	
	@Autowired
	private ReservationDAO reservationRepo;
	
	@Autowired
	private ReservationSlotDAO slotRepo;
	
	@Autowired
	private SaunaDAO saunaRepo;
	
	@Autowired UserDAO userRepo;
	
	@Test
	public void shouldCreateReservationForUser() {
		User user1 = new User("Testi", "Useri", "user", "$2a$12$fgUkahGWGyWXx8fT08ix8uVOPi7b/gieCVpSoOjytMGHG5E.3Tuue", "USER");
		userRepo.save(user1);
		Sauna nSauna = new Sauna("Testisauna", "Testirappu", "Suuri", 10, 0);
		saunaRepo.save(nSauna);
		LocalDateTime date = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		ReservationSlot slot = new ReservationSlot(date, 0, 0, nSauna);
		slotRepo.save(slot);
		// Varaus
		Reservation uusiVaraus = new Reservation(slot, user1);
		reservationRepo.save(uusiVaraus);
		assertThat(reservationRepo.findAllBySubscriber(user1).size() > 0);
	}
	
	@Test
	public void shouldRemoveReservationFromUser() {
		User user1 = new User("Testi", "Useri", "user", "$2a$12$fgUkahGWGyWXx8fT08ix8uVOPi7b/gieCVpSoOjytMGHG5E.3Tuue", "USER");
		userRepo.save(user1);
		Sauna nSauna = new Sauna("Testisauna", "Testirappu", "Suuri", 10, 0);
		saunaRepo.save(nSauna);
		LocalDateTime date = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		ReservationSlot slot = new ReservationSlot(date, 0, 0, nSauna);
		slotRepo.save(slot);
		// Varaus
		Reservation varaus = new Reservation(slot, user1);
		reservationRepo.save(varaus);
		reservationRepo.delete(varaus);
		assertThat(reservationRepo.findAllBySubscriber(user1).size() < 1);
	}
}
