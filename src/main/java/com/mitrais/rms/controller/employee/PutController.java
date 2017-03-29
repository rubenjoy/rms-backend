package com.mitrais.rms.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.commons.dto.EmployeeDto;
import com.mitrais.rms.controller.commons.exception.EmployeeNotFoundException;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.EmployeeRepository;

@RestController(value = "employeePutController")
@RequestMapping(value = "/employees")
public class PutController
{
	@Autowired
	private EmployeeRepository repository;

	@RequestMapping(
		method = {RequestMethod.PUT, RequestMethod.PATCH},
		value = "/{employeeId}"
	)
	public EmployeeDto save(
			@PathVariable Integer employeeId, 
			@RequestBody EmployeeDto dto
		)
	{
		Employee entity = this.repository
			.findOne( employeeId );
		if (entity == null)
			throw new EmployeeNotFoundException();
		entity = dto.updateEntity( entity );
		this.repository.save(entity);
		return EmployeeDto.fromEntity(entity);
	}
}