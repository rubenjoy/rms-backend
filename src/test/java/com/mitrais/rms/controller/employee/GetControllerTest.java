package com.mitrais.rms.controller.employee;

import java.lang.reflect.Field;
import static java.util.Collections.nCopies;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.EmployeeRepository;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import static com.mitrais.rms.utils.DateFormatter.parse;

import io.restassured.http.ContentType;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GetControllerTest
{
	@Autowired
	private GetController getController;

	@Configuration
	@Import(GetController.class)
	static class Config {}

	@MockBean
	private EmployeeRepository employeeRepository;

	@Before
	public void setup()
	{
		Employee e = new Employee();
		e.setName("Mukidi")
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

		try {
			Field id = e.getClass().getDeclaredField("id");
			id.setAccessible(true);
			id.set(e, new Integer(2));
		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		}

		Mockito.when(employeeRepository.findAll()).
			thenReturn(nCopies(1,e));
		Mockito.when(employeeRepository.findOne(anyInt())).
			thenAnswer(new Answer() {
				public Object answer(InvocationOnMock invocation) {
					Integer intId = (Integer) invocation.getArguments()[0];

					try {
						Field id = e.getClass().getDeclaredField("id");
						id.setAccessible(true);
						id.set(e, intId);
					} catch(Exception ex) {
						System.err.println(ex.getMessage());
					}

					return e;
				}
			});
	}

	@Test
	public void itShouldFindById()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}", 3).
		then().
			statusCode(200).
			contentType(ContentType.JSON).
			body("id", equalTo("/employees/" + 3)).
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
			body("id", hasItems("/employees/" + 2)).
			body("name", hasItems("Mukidi"));
	}
}