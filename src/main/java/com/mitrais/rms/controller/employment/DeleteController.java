package com.mitrais.rms.controller.employment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IDeleteController;
import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.repository.EmploymentRepository;
import com.mitrais.rms.service.EmploymentService;

@RestController(value = "employmentDeleteController")
@RequestMapping(value = "/employees/{employeeId}/employments")
public class DeleteController
	implements IDeleteController
{
	@Autowired
	private EmploymentRepository repository;
	@Autowired
	private EmploymentService service;

	@Override
	@RequestMapping(
		method = RequestMethod.DELETE,
		value = "/{employmentId}"
	)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(
			@PathVariable Integer employeeId, 
			@PathVariable Integer employmentId
		)
	{
		EmploymentHistory entity = this.repository
			.findByIdAndEmployeeId(
				employmentId, employeeId
			);
		if (entity != null)
			this.service.deleteAssociation( entity );
	}
}