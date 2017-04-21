package com.mitrais.rms.controller.address;

import com.mitrais.rms.entity.Address;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.AddressRepository;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.AddressService;
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
@ComponentScan(basePackageClasses = {GetController.class, AddressService.class})
@RunWith(SpringRunner.class)
public class GetControllerTest
{
	@Autowired
	@Qualifier("addressGetController")
	private GetController getController;

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	private Integer addressId;
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
		this.employeeId = employee.getId();
		assert employeeId != null;
		Address address = new Address();
		address.setAddress("hello world")
			.setActiveInd(true);
		address.setEmployee(employee);
		employee.getAddresses().add(address);
		addressRepository.save(address);
		this.addressId = address.getId();
		assert addressId != null;
	}

	@After
	public void tearDown()
	{
		addressRepository.deleteAll();
		employeeRepository.deleteAll();
	}


	@Test
	public void itShouldFindAllByEmployeeId()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/addresses", employeeId).
		then().
			statusCode(200).
			body("id", hasItems("/employees/" + employeeId + "/addresses/" + addressId)).
			body("address", hasItems("hello world"));
	}

	@Test
	public void itShouldFindByEmployeeIdAndAddressId()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/addresses/{addressId}", employeeId, addressId).
		then().
			statusCode(200).
			body("id", equalTo("/employees/" + employeeId + "/addresses/" + addressId)).
			body("address", equalTo("hello world")).
			body("activeInd", equalTo(true));
	}

	@Test
	public void itShouldResponse404ForInvalidAddress()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/addresses/{addressId}", employeeId, 0).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		given().
			standaloneSetup(this.getController).
		when().
			get("/employees/{employeeId}/addresses/{addressId}", 0, addressId).
		then().
			statusCode(404);
	}
}