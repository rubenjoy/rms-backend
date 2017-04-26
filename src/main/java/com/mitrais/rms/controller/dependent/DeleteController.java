package com.mitrais.rms.controller.dependent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IDeleteController;
import com.mitrais.rms.controller.commons.exception.DependentNotFoundException;
import com.mitrais.rms.entity.Dependent;
import com.mitrais.rms.repository.DependentRepository;
import com.mitrais.rms.service.DependentService;

@RestController(value = "dependentDeleteController")
@RequestMapping(value = "/employees/{employeeId}/dependents")
public class DeleteController
	implements IDeleteController
{
	@Autowired
	private DependentRepository repository;
	@Autowired
	private DependentService service;

	@Override
	@RequestMapping(
		method = RequestMethod.DELETE,
		value = "/{dependentId}"
	)
	@ResponseStatus(
		value = HttpStatus.NO_CONTENT
	)
	public void delete(
			@PathVariable
			Integer employeeId, 
			@PathVariable
			Integer dependentId
		)
	{
		Dependent entity = this.repository
			.findByIdAndEmployeeId(
				dependentId, employeeId
			);
		if (entity == null)
			throw new DependentNotFoundException();
		this.service.deleteAssociation(
			entity
		);
	}
}