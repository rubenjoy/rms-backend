package com.mitrais.rms.controller.dependent;

import com.mitrais.rms.entity.Dependent;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.DependentType;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.DependentRepository;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.DependentService;
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
@ComponentScan(basePackageClasses = {DeleteController.class, DependentService.class})
@RunWith(SpringRunner.class)
public class DeleteControllerTest
{
	@Autowired
	@Qualifier("dependentDeleteController")
	private DeleteController deleteController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DependentRepository dependentRepository;

	private Integer employeeId;
	private Integer dependentId;

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

		Dependent dependent = new Dependent();
		dependent.setName("Mary Jane")
			.setType(DependentType.WIFE)
			.setActiveInd(true)
			.setBirthDate(parse("1989-10-10"));
		dependent.setEmployee(employee);
		employee.getDependents().add(dependent);
		dependentRepository.save(dependent);
		dependentId = dependent.getId();
		assert dependentId != null;
	}

	@After
	public void tearDown()
	{
		dependentRepository.deleteAll();
		employeeRepository.deleteAll();
		dependentId = null;
		employeeId = null;
	}

	@Test
	public void itShouldDeleteDependent()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/dependents/{dependentId}",
				employeeId, dependentId).
		then().
			statusCode(204);
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/dependents/{dependentId}",
				0, dependentId).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse404ForInvalidDependent()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/dependents/{dependentId}",
				employeeId, 0).
		then().
			statusCode(404);
	}
}