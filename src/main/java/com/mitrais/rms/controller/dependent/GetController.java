package com.mitrais.rms.controller.dependent;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IGetController;
import com.mitrais.rms.controller.commons.dto.DependentDto;
import com.mitrais.rms.controller.commons.exception.DependentNotFoundException;
import com.mitrais.rms.entity.Dependent;
import com.mitrais.rms.repository.DependentRepository;

@RestController(value = "dependentGetController")
@RequestMapping(value = "/employees/{employeeId}/dependents")
public class GetController
	implements IGetController<DependentDto>
{
	@Autowired
	private DependentRepository repository;

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = "/{dependentId}"
	)
	@ResponseBody
	public DependentDto findByEmployeeIdAndId(
			@PathVariable
			Integer employeeId,
			@PathVariable
			Integer dependentId
		)
	{
		Dependent dependent = this.repository
			.findByIdAndEmployeeId(
				dependentId, employeeId
			);
		if (dependent == null)
			throw new DependentNotFoundException();
		assert dependent.getId() != null : dependent;
		return DependentDto.fromEntity(dependent);
	}

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = ""
	)
	@ResponseBody
	public List<DependentDto> findByEmployeeId(
			@PathVariable
			Integer employeeId
		)
	{
		List<Dependent> dependents = this.repository
			.findByEmployeeId(employeeId);
		return dependents.stream()
			.map(dependent -> DependentDto.fromEntity(dependent))
			.collect(Collectors.toList());
	}
}