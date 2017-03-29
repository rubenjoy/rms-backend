package com.mitrais.rms.controller.employment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPostController;
import com.mitrais.rms.controller.commons.dto.EmploymentHistoryDto;
import com.mitrais.rms.controller.commons.exception.EmployeeNotFoundException;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.EmploymentService;

@RestController(value = "employmentPostController")
@RequestMapping(value = "/employees/{employeeId}/employments")
public class PostController
	implements IPostController<EmploymentHistoryDto>
{
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private EmploymentService service;

	@Override
	@RequestMapping(
		method = RequestMethod.POST
	)
	@ResponseBody
	public EmploymentHistoryDto save(
			@PathVariable Integer employeeId, 
			@RequestBody EmploymentHistoryDto dto
		)
	{
		Employee employee = this.employeeRepo
			.findOne( employeeId );
		if (employee == null)
			throw new EmployeeNotFoundException();
		EmploymentHistory entity = dto.createEntity();
		entity.setEmployee( employee );
		this.service.createAssociation(employee, entity);
		assert entity.getId() != null : entity;
		return EmploymentHistoryDto.fromEntity(entity);
	}
}