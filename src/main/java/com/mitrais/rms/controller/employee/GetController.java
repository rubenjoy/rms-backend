package com.mitrais.rms.controller.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.commons.dto.EmployeeDto;
import com.mitrais.rms.controller.commons.dto.ShortEmployeeDto;
import com.mitrais.rms.controller.commons.exception.EmployeeNotFoundException;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.EmployeeRepository;

@RestController(value = "employeeGetController")
@RequestMapping(value = "/employees")
public class GetController
{
	@Autowired
	private EmployeeRepository repository;

	@RequestMapping(
		method = RequestMethod.GET,
		value = "/{employeeId}"
	)
	@ResponseBody
	public EmployeeDto findById(
			@PathVariable Integer employeeId
		)
	{
		Employee entity = this.repository
			.findOne( employeeId );
		if (entity == null)
			throw new EmployeeNotFoundException();
		return EmployeeDto.fromEntity(entity);
	}

	@RequestMapping(
		method = RequestMethod.GET,
		value = ""
	)
	@ResponseBody
	public List<ShortEmployeeDto> findAll(
			@RequestParam(
				value = "offset",
				defaultValue = "0"
			) 
			int offset,
			@RequestParam(
				value = "size",
				defaultValue = "20"
			) 
			int size
		)
	{
		// TODO paging
		List<Employee> employees = new ArrayList<Employee>();
		for (Employee employee : this.repository.findAll()) {
			employees.add(employee);
		}
		return employees.stream()
			.map(employee -> ShortEmployeeDto.fromEntity(employee))
			.collect(Collectors.toList());
	}
}