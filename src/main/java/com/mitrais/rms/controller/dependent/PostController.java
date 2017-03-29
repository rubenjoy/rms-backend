package com.mitrais.rms.controller.dependent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPostController;
import com.mitrais.rms.controller.commons.dto.DependentDto;
import com.mitrais.rms.controller.commons.exception.EmployeeNotFoundException;
import com.mitrais.rms.entity.Dependent;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.DependentService;

@RestController(value = "dependentPostController")
@RequestMapping(value = "/employees/{employeeId}/dependents")
public class PostController
	implements IPostController<DependentDto>
{
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private DependentService service;

	@Override
	@RequestMapping(
		method = RequestMethod.POST,
		value = ""
	)
	@ResponseBody
	public DependentDto save(
			@PathVariable
			Integer employeeId, 
			@RequestBody
			DependentDto dto
		)
	{
		Employee employee = this.employeeRepo
			.findOne( employeeId );
		if (employee == null)
			throw new EmployeeNotFoundException();
		Dependent dependent = dto.createEntity();
		this.service.createAssociation(
			employee, dependent
		);
		return DependentDto.fromEntity(dependent);
	}
}