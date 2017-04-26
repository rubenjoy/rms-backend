package com.mitrais.rms.controller.employee;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
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
@ComponentScan(basePackageClasses = {PutController.class})
@RunWith(SpringRunner.class)
public class PutControllerTest
{
	@Autowired
	@Qualifier("employeePutController")
	private PutController putController;
	@Autowired
	private EmployeeRepository employeeRepository;

	private Integer employeeId;

	@Before
	public void setup()
	{
		Employee employee = new Employee();
		employee.setName("Mukidi")
			.setBirthDate(parse("1989-10-10"))
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
		employeeRepository.deleteAll();
		employeeId = null;
	}

	@Test
	public void itShouldPutEmployee()
	{
		String employee = "{\"name\": \"Peter Parker\", \"birthDate\": \"1985-10-10\"}";

		given().
			contentType("application/json").
			body(employee).
			standaloneSetup(this.putController).
		when().
			put("/employees/{employeeId}", employeeId).
		then().
			statusCode(200).
			body("id", equalTo("/employees/" + employeeId)).
			body("name", not(equalTo("Mukidi"))).
			body("name", equalTo("Peter Parker")).
			body("birthDate", not(equalTo("1989-10-10"))).
			body("birthDate", equalTo("1985-10-10"));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		String employee = "{\"name\": \"Peter Parker\", \"birthDate\": \"1985-10-10\"}";

		given().
			contentType("application/json").
			body(employee).
			standaloneSetup(this.putController).
		when().
			put("/employees/{employeeId}", 0).
		then().
			statusCode(404);
	}
}