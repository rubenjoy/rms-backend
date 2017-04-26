package com.mitrais.rms.controller.employment;

import java.util.Date;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.repository.EmploymentRepository;
import com.mitrais.rms.service.EmploymentService;
import static com.mitrais.rms.utils.DateFormatter.parse;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

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
@ComponentScan(basePackageClasses = {DeleteController.class, EmploymentService.class})
@RunWith(SpringRunner.class)
public class DeleteControllerTest
{
	@Autowired
	@Qualifier("employmentDeleteController")
	private DeleteController deleteController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmploymentRepository employmentRepository;

	private Integer employeeId;
	private Integer employmentId;

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

		EmploymentHistory employment = new EmploymentHistory();
		employment.setStartDate(new Date()).
			setEndDate(new Date()).
			setEmployer("PT Maju Mundur Kena").
			setJobTitle("Cleaning Service").
			setActiveInd(true);
		employment.setEmployee(employee);
		employee.getEmployments().add(employment);
		employmentRepository.save(employment);
		this.employmentId = employment.getId();
		assert employmentId != null;
	}

	@After
	public void tearDown()
	{
		this.employmentRepository.deleteAll();
		this.employeeRepository.deleteAll();
		this.employeeId = null;
		this.employmentId = null;
	}


	@Test
	public void itShouldDeleteEmployment()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/employments/{employmentId}",
				employeeId, employmentId).
		then().
			statusCode(204);
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/employments/{employmentId}",
				0, employmentId).
		then().
			statusCode(404);

	}

	@Test
	public void itShouldResponse404ForInvalidEmployment()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/employments/{employmentId}",
				employeeId, 0).
		then().
			statusCode(404);
	}
}