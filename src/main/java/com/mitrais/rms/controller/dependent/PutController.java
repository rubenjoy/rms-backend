package com.mitrais.rms.controller.dependent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPutController;
import com.mitrais.rms.controller.commons.dto.DependentDto;
import com.mitrais.rms.controller.commons.exception.DependentNotFoundException;
import com.mitrais.rms.entity.Dependent;
import com.mitrais.rms.repository.DependentRepository;
import com.mitrais.rms.service.DependentService;

@RestController(value = "DependentPutController")
@RequestMapping(value = "/employees/{employeeId}/dependents")
public class PutController
	implements IPutController<DependentDto>
{
	@Autowired
	private DependentRepository repository;
	@Autowired
	private DependentService service;

	@Override
	@RequestMapping(
		method = {RequestMethod.PUT,RequestMethod.PATCH},
		value = "/{dependentId}"
	)
	@ResponseBody
	public DependentDto save(
			@PathVariable
			Integer employeeId, 
			@PathVariable
			Integer dependentId, 
			@RequestBody
			DependentDto dto
		)
	{
		Dependent entity = this.repository
			.findByIdAndEmployeeId(
				dependentId, employeeId
			);
		if (entity == null)
			throw new DependentNotFoundException();
		entity = dto.updateEntity(entity);
		this.service.updateAssociation(entity);
		return DependentDto.fromEntity(entity);
	}
}