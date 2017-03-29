package com.mitrais.rms.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPostController;
import com.mitrais.rms.controller.commons.dto.AddressDto;
import com.mitrais.rms.controller.commons.exception.EmployeeNotFoundException;
import com.mitrais.rms.entity.Address;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.AddressService;

@RestController(value = "addressPostController")
@RequestMapping(value = "/employees/{employeeId}/addresses")
public class PostController
	implements IPostController<AddressDto>
{
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private AddressService service;

	@Override
	@RequestMapping(
		method = RequestMethod.POST,
		value = ""
	)
	@ResponseBody
	public AddressDto save(
			@PathVariable
			Integer employeeId,
			@RequestBody
			AddressDto dto
		)
		throws EmployeeNotFoundException
	{
		// check whether the employee exists
		Employee employee = employeeRepo
			.findOne(employeeId);
		if (employee == null)
				throw new EmployeeNotFoundException();
		// insert into table
		Address address = dto.createEntity();
		this.service.createAssociation(
			employee, address);
		return AddressDto.fromEntity(address);
	}
}