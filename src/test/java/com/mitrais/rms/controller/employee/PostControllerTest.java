package com.mitrais.rms.controller.employee;

import java.lang.reflect.Field;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.EmployeeRepository;

import io.restassured.http.ContentType;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PostControllerTest
{
	@MockBean
	private EmployeeRepository employee;

	@Autowired
	private PostController postController;

	@Configuration
	@Import(PostController.class)
	static class Config {}

	@Before
	public void setup() 
	{
		Mockito.when(employee.save(any(Employee.class))).
			thenAnswer(
				new Answer() {
					public Object answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						if (args.length > 0) {
							Object obj = args[0];
							if (obj instanceof Employee) {
								try {
									Field id = obj.getClass().getDeclaredField("id");
									id.setAccessible(true);
									id.set(obj, new Integer(2));
									return obj;
								} catch(Exception e) {
									System.err.println(e.getMessage());
								}
							}
							return obj;
						}
						return null;
					}
				}
			);
	}

	@Test
	public void itShouldReturnCreatedUser()
	{
		String employee = "{\"name\": \"Mukidi\", \"birthDate\": \"2017-12-12\", \"hireDate\": \"2017-12-12\", \"suspendDate\": \"2017-12-12\"}";

		given().
			contentType("application/json").
			body(employee).
			standaloneSetup(this.postController).
		when().
			post("/employees").
		then().
			statusCode(200).
			contentType(ContentType.JSON).
			body("id", equalTo("/employees/2")).
			body("name", equalTo("Mukidi")).
			body("birthDate", equalTo("2017-12-12")).
			body("suspendDate", equalTo("2017-12-12"));
	}
}