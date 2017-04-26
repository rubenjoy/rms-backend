package com.mitrais.rms.controller.location;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.Location;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.repository.LocationRepository;
import com.mitrais.rms.service.LocationService;
import static com.mitrais.rms.utils.DateFormatter.parse;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@ComponentScan(basePackageClasses = {PutController.class, LocationService.class})
@RunWith(SpringRunner.class)
public class PutControllerTest
{
	@Autowired
	@Qualifier("locationPutController")
	private PutController putController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private LocationRepository locationRepository;

	private Integer employeeId;
	private Integer locationId;

	@Before
	public void setup()
	{
		Employee employee = new Employee();
		employee.setName("Mukidi")
			.setBirthDate(parse("2017-12-12"))
			.setGender(Gender.MALE)
			.setMaritalStatus(MaritalStatus.SINGLE)
			.setPhone("0812")
			.setEmail("mukidi@gmail.com")
			.setHireDate(parse("2016-12-12"))
			.setActiveInd(true)
			.setEmploymentStatus("contract")
			.setSuspendDate(parse("2016-12-12"))
			.setJobFamily("SE")
			.setJobTitle("PG")
			.setDivision("CDC Java")
			.setBusinessUnit("buscom")
			.setNationality(Nationality.INDONESIAN);
		employeeRepository.save(employee);
		employeeId = employee.getId();
		assert employeeId != null;

		Location location = new Location();
		location.setBranchOffice("Bandung Surya Sumantri")
			.setStartDate(parse("2017-12-02"))
			.setEndDate(parse("2017-12-24"))
			.setActiveInd(true);
		location.setEmployee(employee);
		employee.getLocations().add(location);
		locationRepository.save(location);
		locationId = location.getId();
		assert locationId != null;
	}

	@After
	public void tearDown()
	{
		locationRepository.deleteAll();
		employeeRepository.deleteAll();
		locationId = null;
		employeeId = null;
	}

	@Test
	public void itShouldPutLocation()
	{
		String location = "{\"branchOffice\": \"Bali Suwung\"}";

		given().
			contentType("application/json").
			body(location).
			standaloneSetup(this.putController).
		when().
			put("/employees/{employeeId}/locations/{locationId}",
				employeeId, locationId).
		then().
			statusCode(200).
			body("branchOffice", equalTo("Bali Suwung")).
			body("branchOffice", not(equalTo("Bandung Surya Sumantri")));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		String location = "{\"branchOffice\": \"Bali Suwung\"}";

		given().
			contentType("application/json").
			body(location).
			standaloneSetup(this.putController).
		when().
			put("/employees/{employeeId}/locations/{locationId}",
				0, locationId).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse404ForInvalidLocation()
	{
		String location = "{\"branchOffice\": \"Bali Suwung\"}";

		given().
			contentType("application/json").
			body(location).
			standaloneSetup(this.putController).
		when().
			put("/employees/{employeeId}/locations/{locationId}",
				employeeId, 0).
		then().
			statusCode(404);
	}
}