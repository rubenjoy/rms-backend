package com.mitrais.rms.controller.dependent;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.DependentRepository;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.DependentService;
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
@ComponentScan(basePackageClasses = {PostController.class, DependentService.class})
@RunWith(SpringRunner.class)
public class PostControllerTest
{
	@Autowired
	@Qualifier("dependentPostController")
	private PostController postController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DependentRepository dependentRepository;

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
		dependentRepository.deleteAll();
		employeeRepository.deleteAll();
		employeeId = null;
	}

	@Test
	public void itShouldPostDependent()
	{
		String dependent = "{\"name\": \"Mary Jane\", \"birthDate\": \"1985-10-10\", \"type\": \"WIFE\"}";

		given().
			contentType("application/json").
			body(dependent).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/dependents", employeeId).
		then().
			statusCode(200).
			body("id", startsWith("/employees/" + employeeId + "/dependents/")).
			body("name", equalTo("Mary Jane")).
			body("birthDate", equalTo("1985-10-10")).
			body("type", equalTo("WIFE"));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		String dependent = "{\"name\": \"Mary Jane\", \"birthDate\": \"1985-10-10\", \"type\": \"WIFE\"}";

		given().
			contentType("application/json").
			body(dependent).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/dependents", 0).
		then().
			statusCode(404);
	}
}