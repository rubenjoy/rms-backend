package com.mitrais.rms.loader;

import java.util.Date;

import com.mitrais.rms.entity.Address;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.Location;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import com.mitrais.rms.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeLoader implements CommandLineRunner
{
	private final EmployeeRepository repository;

	@Autowired
	public EmployeeLoader(EmployeeRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public void run(String... args)
	{
		Address address = new Address();
		address.setAddress("Jalan")
			.setActiveInd(true);
		Location location = new Location();
		location.setBranchOffice("bali suwung")
			.setStartDate(new Date())
			.setEndDate(new Date())
			.setActiveInd(true);
		Employee employee = new Employee();
		employee.setName("Michael Jacob")
			.setGender(Gender.MALE)
			.setNationality(Nationality.INDONESIAN)
			.setBirthDate(new Date())
			.setEmail("mike@mitrais.com")
			.setPhone("+62812346")
			.setMaritalStatus(MaritalStatus.SINGLE)
			.setJobFamily("SE")
			.setJobTitle("PG");
		address.setEmployee(employee);
		location.setEmployee(employee);
		employee.getAddresses().add(address);
		employee.getLocations().add(location);
		this.repository.save(employee);
	}
}