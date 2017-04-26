package com.mitrais.rms.controller.grade;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.GradeHistory;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.repository.GradeRepository;
import com.mitrais.rms.service.GradeService;
import static com.mitrais.rms.utils.DateFormatter.parse;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

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
@ComponentScan(basePackageClasses = {GetController.class, GradeService.class})
@RunWith(SpringRunner.class)
public class GetControllerTest
{
	@Autowired
	@Qualifier("gradeGetController")
	private GetController getController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private GradeRepository gradeRepository;

	private Integer employeeId;
	private Integer gradeId;

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

		GradeHistory grade = new GradeHistory();
		grade.setStartDate(parse("2017-01-04"))
			.setEndDate(parse("2017-12-24"))
			.setGrade("SE-JP")
			.setDevStage(3);
		grade.setEmployee(employee);
		employee.getGrades().add(grade);
		gradeRepository.save(grade);
		gradeId = grade.getId();
		assert gradeId != null;
	}

	@After
	public void tearDown()
	{
		gradeRepository.deleteAll();
		employeeRepository.deleteAll();
		gradeId = null;
		employeeId = null;
	}

	@Test
	public void itShouldGetByEmployeeId()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/grades", employeeId).
		then().
			statusCode(200).
			body("id", hasItems("/employees/" + employeeId + "/grades/" + gradeId)).
			body("devStage", hasItems(3)).
			body("grade", hasItems("SE-JP")).
			body("startDate", hasItems("2017-01-04")).
			body("endDate", hasItems("2017-12-24"));
	}

	@Test
	public void itShouldGetByEmployeeIdAndGradeId()
	{

		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/grades/{gradeId}", 
				employeeId, gradeId).
		then().
			statusCode(200).
			body("id", equalTo("/employees/" + employeeId + "/grades/" + gradeId)).
			body("devStage", equalTo(3)).
			body("grade", equalTo("SE-JP")).
			body("startDate", equalTo("2017-01-04")).
			body("endDate", equalTo("2017-12-24"));
	}

	@Test
	public void itShouldResponse404ForInvalidEmployeeAndValidGrade()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/grades/{gradeId}", 
				0, gradeId).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse404ForInvalidGradeAndValidEmployee()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/grades/{gradeId}", 
				employeeId, 0).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse200WithEmptyArrayForInvalidEmployee()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/grades", 0).
		then().
			statusCode(200).
			body(equalTo("[]"));
	}
}