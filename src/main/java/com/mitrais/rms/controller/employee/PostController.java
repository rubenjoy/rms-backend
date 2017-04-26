package com.mitrais.rms.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.commons.dto.EmployeeDto;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.EmployeeRepository;

@RestController(value = "employeePostController")
@RequestMapping(value = "/employees")
public class PostController
{
	@Autowired
	private EmployeeRepository repository;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EmployeeDto save(@RequestBody EmployeeDto dto)
	{
		Employee entity = dto.createEntity();
		this.repository.save( entity );
		return EmployeeDto.fromEntity(entity);
	}
}