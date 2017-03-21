package com.mitrais.bootcamp.rms;

import com.mitrais.bootcamp.rms.service.JobFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RmsApplication implements CommandLineRunner {

	@Autowired
	private JobFamilyService jobFamilyService;

	public static void main(String[] args) {
		SpringApplication.run(RmsApplication.class, args);
	}


	/**
	 * Method invoked after this class has been instantiated by Spring container
	 * Initializes the in-memory database with all the TourPackages and Tours.
	 *
	 * @param strings
	 * @throws Exception if problem occurs.
	 */
	@Override
	public void run(String... strings) throws Exception {

		jobFamilyService.initJobFamily();


	}
}
