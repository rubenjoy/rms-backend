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
@ComponentScan(basePackageClasses = {DeleteController.class, GradeService.class})
@RunWith(SpringRunner.class)
public class DeleteControllerTest
{
	@Autowired
	@Qualifier("gradeDeleteController")
	private DeleteController deleteController;
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
		grade.setStartDate(parse("2016-01-04"))
			.setEndDate(parse("2017-01-04"))
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
	public void itShouldDeleteGrade()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/grades/{gradeId}",
				employeeId, gradeId).
		then().
			statusCode(204);
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/grades/{gradeId}",
				0, gradeId).
		then().
			statusCode(204);
	}

	@Test
	public void itShouldResponse404ForInvalidGrade()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/grades/{gradeId}",
				employeeId, 0).
		then().
			statusCode(204);
	}
}