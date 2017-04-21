package com.mitrais.rms.controller.address;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.AddressService;
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
@ComponentScan(basePackageClasses = {PostController.class, AddressService.class})
@RunWith(SpringRunner.class)
public class PostControllerTest
{
	@Autowired
	private PostController postController;
	@Autowired
	private EmployeeRepository employeeRepository;

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
		this.employeeId  = employee.getId();
		assert employeeId != null;
	}

	@After
	public void tearDown()
	{
		employeeRepository.deleteAll();
	}

	@Test
	public void itShouldPost()
	{
		String address = "{\"address\": \"hello world\", \"activeInd\": false}";

		given().
			contentType("application/json").
			body(address).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/addresses", employeeId).
		then().
			statusCode(200).
			body("id", startsWith("/employees/" + employeeId + "/addresses/")).
			body("address", equalTo("hello world")).
			body("activeInd", equalTo(false));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		String address = "{\"address\": \"hello world\", \"activeInd\": false}";

		given().
			contentType("application/json").
			body(address).
			standaloneSetup(this.postController).
		when().
			post("/employees/{employeeId}/addresses", 0).
		then().
			statusCode(404);
	}
}