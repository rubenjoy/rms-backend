package com.mitrais.rms.controller.employment;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.entity.JobDescription;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.repository.EmploymentRepository;
import com.mitrais.rms.service.EmploymentService;
import static com.mitrais.rms.utils.DateFormatter.parse;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

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
@ComponentScan(basePackageClasses = {GetController.class, EmploymentService.class})
@RunWith(SpringRunner.class)
public class GetControllerTest
{
	@Autowired
	@Qualifier("employmentGetController")
	private GetController getController;

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
		employeeId = employee.getId();
		assert employeeId != null;

		EmploymentHistory employment = new EmploymentHistory();
		employment.setStartDate(parse("2017-02-12")).
			setEndDate(parse("2017-03-12")).
			setEmployer("PT Maju Mundur Kena").
			setJobTitle("Cleaning Service").
			setActiveInd(true);
		JobDescription jobDesc = new JobDescription();
		jobDesc.setDescription("wipe window and table");
		employment.getJobDesc().add(jobDesc);
		employment.setEmployee(employee);
		employee.getEmployments().add(employment);
		employmentRepository.save(employment);
		employmentId = employment.getId();
		assert employmentId != null;
	}

	@After
	public void tearDown()
	{
		employmentRepository.deleteAll();
		employeeRepository.deleteAll();
		employeeId = null;
		employmentId = null;
	}

	@Test
	public void itShouldFindAllByEmployeeId()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/employments",
				employeeId).
		then().
			statusCode(200).
			body("id", hasItems("/employees/"+employeeId+"/employments/"+employmentId)).
			body("employer", hasItems("PT Maju Mundur Kena")).
			body("jobTitle", hasItems("Cleaning Service")).
			body("activeInd", hasItems(true)).
			body("jobDesc.id", notNullValue()).
			body("jobDesc.description", contains(hasItems("wipe window and table"))).
			body("jobDesc.op", contains(hasItems("READ")));
	}

	@Test
	public void itShouldFindByEmployeeIdAndEmploymentId()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/employments/{employmentId}",
				employeeId, employmentId).
		then().
			statusCode(200).
			body("id", equalTo("/employees/"+employeeId+"/employments/"+employmentId)).
			body("employer", equalTo("PT Maju Mundur Kena")).
			body("jobTitle", equalTo("Cleaning Service")).
			body("activeInd", equalTo(true)).
			body("jobDesc.id", notNullValue()).
			body("jobDesc.description", hasItems("wipe window and table")).
			body("jobDesc.op", hasItems("READ"));
	}

	@Test
	public void itShouldResponse200WithEmptyArrayForInvalidEmployee()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/employments", 0).
		then().
			statusCode(200).
			body(equalTo("[]"));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployeeAndValidEmployment()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/employments/{employmentId}", 
				0, employmentId).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse404ForInvalidEmployment()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/employments/{employmentId}", 
				employeeId, 0).
		then().
			statusCode(404);
	}
}