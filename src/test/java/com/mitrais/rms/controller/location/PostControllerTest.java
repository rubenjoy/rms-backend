package com.mitrais.rms.controller.location;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.repository.LocationRepository;
import com.mitrais.rms.service.LocationService;
import static com.mitrais.rms.utils.DateFormatter.parse;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;

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
@ComponentScan(basePackageClasses = {PostController.class, LocationService.class})
@RunWith(SpringRunner.class)
public class PostControllerTest
{
	@Autowired
	@Qualifier("locationPostController")
	private PostController postController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private LocationRepository locationRepository;

	private Integer employeeId;

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
	}

	@After
	public void tearDown()
	{
		locationRepository.deleteAll();
		employeeRepository.deleteAll();
		employeeId = null;
	}

	@Test
	public void itShouldPostLocation()
	{
		String location = "{\"branchOffice\": \"Bandung Surya Sumantri\", \"startDate\": \"2017-12-01\","+
			"\"endDate\": \"2018-12-24\", \"activeInd\": true}";

		given().
			contentType("application/json").
			body(location).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/locations", employeeId).
		then().
			statusCode(200).
			body("branchOffice", equalTo("Bandung Surya Sumantri")).
			body("startDate", equalTo("2017-12-01")).
			body("endDate", equalTo("2018-12-24")).
			body("activeInd", equalTo(true));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		String location = "{\"branchOffice\": \"Bandung Surya Sumantri\", \"startDate\": \"2017-12-01\","+
			"\"endDate\": \"2018-12-12\", \"activeInd\": true}";

		given().
			contentType("application/json").
			body(location).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/locations", 0).
		then().
			statusCode(404);
	}
}