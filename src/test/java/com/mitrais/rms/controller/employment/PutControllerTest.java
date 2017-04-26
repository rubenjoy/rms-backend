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
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

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
@ComponentScan(basePackageClasses = {PutController.class, EmploymentService.class})
@RunWith(SpringRunner.class)
public class PutControllerTest
{
	@Autowired
	@Qualifier("employmentPutController")
	private PutController putController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmploymentRepository employmentRepository;

	private Integer employeeId;
	private Integer employmentId;
	private Integer jobdescId;
	private Integer jobdesc2Id;

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
		this.employeeRepository.save(employee);
		this.employeeId = employee.getId();
		assert employeeId != null;

		EmploymentHistory employment = new EmploymentHistory();
		employment.setStartDate(parse("2017-12-02")).
			setEndDate(parse("2017-12-24")).
			setEmployer("PT Maju Mundur Kena").
			setJobTitle("Cleaning Service").
			setActiveInd(true);
		JobDescription jobDesc = new JobDescription();
		jobDesc.setDescription("wipe window and table");
		JobDescription jobdesc2 = new JobDescription();
		jobdesc2.setDescription("Develop RMS App using Java Spring");
		employment.getJobDesc().add(jobDesc);
		employment.getJobDesc().add(jobdesc2);
		employment.setEmployee(employee);
		employee.getEmployments().add(employment);
		this.employmentRepository.save(employment);
		this.employmentId = employment.getId();
		assert employmentId != null;
		this.jobdescId = jobDesc.getId();
		assert jobdescId != null;
		this.jobdesc2Id = jobdesc2.getId();
		assert jobdesc2Id != null;
	}

	@After
	public void tearDown()
	{
		employeeRepository.deleteAll();
		employmentRepository.deleteAll();
		employeeId = null;
		employmentId = null;
		jobdescId = null;
		jobdesc2Id = null;
	}

	@Test
	public void itShouldPutEmployment()
	{
		String employment = "{\"employer\": \"PT Mitrais\", \"jobTitle\": \"Programmer\","+
			"\"startDate\": \"2015-06-06\", \"endDate\": \"2015-07-07\", \"activeInd\": false}";

		given().
			contentType("application/json").
			body(employment).
			standaloneSetup(this.putController).
		when().
			patch("/employees/{employeeId}/employments/{employmentId}",
				employeeId, employmentId).
		then().
			statusCode(200).
			body("id", equalTo("/employees/" + employeeId + "/employments/" + employmentId)).
			body("employer", not("PT Maju Mundur Kena")).
			body("employer", equalTo("PT Mitrais")).
			body("jobTitle", not("Cleaning Service")).
			body("jobTitle", equalTo("Programmer")).
			body("activeInd", not(true)).
			body("startDate", equalTo("2015-06-06")).
			body("endDate", equalTo("2015-07-07"));
	}

	@Test
	public void itShouldPatchEmploymentWithJobDesc()
	{
		String employment = "{\"jobDesc\": [{\"id\": " + jobdescId + ", \"op\": \"DELETE\"},"+
			"{\"description\": \"deploy to heroku\", \"op\": \"CREATE\"}, "+
			"{\"id\": " + jobdesc2Id + ", \"description\": \"Develop RMS in Angular\", \"op\": \"UPDATE\" }]}";

		given().
			contentType("application/json").
			body(employment).
			standaloneSetup(this.putController).
		when().
			patch("/employees/{employeeId}/employments/{employmentId}",
				employeeId, employmentId).
		then().
			statusCode(200).
			body("jobDesc.id", not(contains(nullValue()))).
			body("jobDesc.id", not(hasItems(jobdescId))).
			body("jobDesc.description", hasItems("deploy to heroku")).
			body("jobDesc.description", hasItems("Develop RMS in Angular")).
			body("jobDesc.description", not(hasItems("wipe window and table"))).
			body("jobDesc.description", not(hasItems("Develop RMS App using Java Spring")));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		String employment = "{\"employer\": \"PT Mitrais\", \"jobTitle\": \"Programmer\","+
			"\"startDate\": \"2015-06-06\", \"endDate\": \"2015-07-07\", \"activeInd\": false}";

		given().
			contentType("application/json").
			body(employment).
			standaloneSetup(this.putController).
		when().
			patch("/employees/{employeeId}/employments/{employmentId}",
				0, employmentId).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse404ForInvalidEmployment()
	{
		String employment = "{\"employer\": \"PT Mitrais\", \"jobTitle\": \"Programmer\","+
			"\"startDate\": \"2015-06-06\", \"endDate\": \"2015-07-07\", \"activeInd\": false}";

		given().
			contentType("application/json").
			body(employment).
			standaloneSetup(this.putController).
		when().
			patch("/employees/{employeeId}/employments/{employmentId}",
				employeeId, 0).
		then().
			statusCode(404);
	}
}