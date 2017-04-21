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
@ComponentScan(basePackageClasses = {DeleteController.class, AddressService.class})
@RunWith(SpringRunner.class)
public class DeleteControllerTest
{
	@Autowired
	@Qualifier("addressDeleteController")
	private DeleteController deleteController;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private AddressRepository addressRepository;

	private Integer employeeId;
	private Integer addressId;

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
	public void itShouldDeleteAddressById()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/addresses/{addressId}",
					employeeId, addressId).
		then().
			statusCode(204);
	}

	@Test
	public void itShouldResponse404ForInvalidAddress()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/addresses/{addressId}",
					employeeId, 0).
		then().
			statusCode(404);
	}

	@Test
	public void itShouldResponse404ForInvalidEmployee()
	{
		given().
			standaloneSetup(this.deleteController).
		when().
			delete("/employees/{employeeId}/addresses/{addressId}",
					0, addressId).
		then().
			statusCode(404);
	}
}