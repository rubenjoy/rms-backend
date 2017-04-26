package com.mitrais.rms.controller.employment;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.repository.EmploymentRepository;
import com.mitrais.rms.service.EmploymentService;
import static com.mitrais.rms.utils.DateFormatter.parse;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

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
@ComponentScan(basePackageClasses = {PostController.class, EmploymentService.class})
@RunWith(SpringRunner.class)
public class PostControllerTest
{
	@Autowired
	@Qualifier("employmentPostController")
	private PostController postController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmploymentRepository employmentRepository;

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
		this.employeeId = employee.getId();
		assert employeeId != null;
	}

	@After
	public void tearDown()
	{
		employmentRepository.deleteAll();
		employeeRepository.deleteAll();
		employeeId = null;
	}

	@Test
	public void itShouldPostEmployment()
	{
		String employment = "{\"employer\": \"PT Maju Mundur Kena\", \"jobTitle\": \"Cleaning Service\", " +
			"\"startDate\": \"2017-12-02\", \"endDate\": \"2017-12-24\", \"activeInd\": true}";
		// TODO with jobdesc

		given().
			contentType("application/json").
			body(employment).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/employments", employeeId).
		then().
			statusCode(200).
			body("id", startsWith("/employees/" + employeeId + "/employments/")).
			body("employer", equalTo("PT Maju Mundur Kena")).
			body("jobTitle", equalTo("Cleaning Service")).
			body("startDate", equalTo("2017-12-02")).
			body("endDate", equalTo("2017-12-24")).
			body("activeInd", equalTo(true));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		String employment = "{\"employer\": \"PT Maju Mundur Kena\", \"jobTitle\": \"Cleaning Service\"}";

		given().
			contentType("application/json").
			body(employment).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/employments", 0).
		then().
			statusCode(404);
	}
}