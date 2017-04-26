package com.mitrais.rms.controller.employee;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import static com.mitrais.rms.utils.DateFormatter.parse;

import io.restassured.http.ContentType;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@ComponentScan(basePackageClasses = {GetController.class})
@RunWith(SpringRunner.class)
public class GetControllerWithJpaTest
{
	@Autowired
	@Qualifier("employeeGetController")
	private GetController getController;

	@Autowired
	private EmployeeRepository employeeRepository;

	private Integer createdId;

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
		createdId = employee.getId();
		assert createdId != null;
	}

	@Test
	public void itShouldFindById()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}", createdId.intValue()).
		then().
			statusCode(200).
			contentType(ContentType.JSON).
			body("id", equalTo("/employees/" + createdId.toString())).
			body("name", equalTo("Mukidi")).
			body("birthDate", equalTo("2017-12-12"));
	}

	@Test
	public void itShouldFindAll()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees").
		then().
			statusCode(200).
			contentType(ContentType.JSON).
			body("id", hasItems("/employees/" + createdId.toString())).
			body("name", hasItems("Mukidi"));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}", 0).
		then().
			statusCode(404);
	}
}