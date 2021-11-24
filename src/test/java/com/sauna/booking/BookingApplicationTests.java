package com.sauna.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.*;

import com.sauna.booking.web.*;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookingApplicationTests {
	
	@Autowired
	private ReservationController reservationController;
	private SaunaController saunaController;
	private RestAPIController apiController;
	private UserController userController;
	private UserService userService;

	@Test
	void contextLoads() throws Exception {
		assertThat(reservationController).getClass();
		assertThat(saunaController).getClass();
		assertThat(apiController).getClass();
		assertThat(userController).getClass();
		assertThat(userService).getClass();
	}

}
