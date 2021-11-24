package com.sauna.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sauna.booking.domain.Sauna;
import com.sauna.booking.domain.SaunaDAO;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class SaunaRepositoryTests {
	
	@Autowired
	private SaunaDAO saunaRepo;
	
    private static ArrayList<Sauna> saunas;

    @BeforeAll
    public static void init() {
        saunas = new ArrayList<Sauna>();
    }
	
	@Test
	public void shouldCreateSauna() {
		Sauna nSauna = new Sauna("Testisauna", "Testirappu", "Suuri", 10, 0);
		saunaRepo.save(nSauna);
		saunas.add(nSauna);
		assertThat(nSauna.getName().contains("Testisauna"));
		assertThat(saunaRepo.findByName("Testisauna").getName().contains("Testisauna"));
	}
	
	@Test
	public void shoulDeleteSauna() {
		Sauna nSauna = new Sauna("Testisauna", "Testirappu", "Suuri", 10, 0);
		saunaRepo.save(nSauna);
		Sauna nTest = saunaRepo.findByName("Testisauna");
		if ( nTest != null ) {
			saunaRepo.delete(nTest);
			assertNull(saunaRepo.findByName("Testinauna"));
		} else {
			assertThat("Not found");
		}
	}
	
	@Test
	public void shouldCreateAndFindMultipleSaunas() {
		Sauna nSauna = new Sauna("Testisauna", "Testirappu", "Suuri", 10, 0);
		saunaRepo.save(nSauna);
		Sauna nSauna2 = new Sauna("Testisauna 2", "Testirappu", "Suuri", 10, 0);
		saunaRepo.save(nSauna2);
		saunas.add(nSauna);
		assertThat(saunaRepo.findAll().size() == 2);
	}
	
	@Test
	public void shouldFindSauna() {
		Sauna nSauna = new Sauna("Testisauna 3", "Testirappu", "Suuri", 10, 0);
		saunaRepo.save(nSauna);
		assertNotNull(saunaRepo.findByName("Testisauna 3"));
	}
	
}
