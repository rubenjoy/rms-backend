package com.mitrais.rms.controller.employee;

import io.restassured.http.ContentType;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@ComponentScan(basePackageClasses = {PostController.class})
@RunWith(SpringRunner.class)
public class PostControllerWithJpaTest
{
	@Autowired
	@Qualifier("employeePostController")
	private PostController postController;

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
			body("name", equalTo("Mukidi")).
			body("birthDate", equalTo("2017-12-12")).
			body("suspendDate", equalTo("2017-12-12"));
	}
}