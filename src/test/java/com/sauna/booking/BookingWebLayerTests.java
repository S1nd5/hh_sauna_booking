package com.sauna.booking;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookingWebLayerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Kirjautuminen")));
	}
	
	@Test
	public void shouldReturnRegisterPage() throws Exception {
		this.mockMvc.perform(get("/register")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Rekisteröityminen")));
	}
	
	@Test
	public void shouldRedirectToLogin() throws Exception {
		this.mockMvc.perform(get("/home")).andDo(print()).andExpect(status().is(302));
	}
}