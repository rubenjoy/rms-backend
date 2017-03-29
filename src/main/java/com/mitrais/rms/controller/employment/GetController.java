package com.mitrais.rms.controller.employment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IGetController;
import com.mitrais.rms.controller.commons.dto.EmploymentHistoryDto;
import com.mitrais.rms.controller.commons.exception.EmploymentNotFoundException;
import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.repository.EmploymentRepository;

@RestController(value = "employmentGetController")
@RequestMapping(value = "/employees/{employeeId}/employments")
public class GetController
	implements IGetController<EmploymentHistoryDto>
{
	@Autowired
	private EmploymentRepository repository;

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = "/{employmentId}"
	)
	@ResponseBody
	public EmploymentHistoryDto findByEmployeeIdAndId(
			@PathVariable Integer employeeId, 
			@PathVariable Integer employmentId
		)
	{
		EmploymentHistory entity = this.repository
			.findByIdAndEmployeeId(
				employmentId, employeeId
			);
		if (entity == null)
			throw new EmploymentNotFoundException();
		return EmploymentHistoryDto.fromEntity(entity);
	}

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = ""
	)
	@ResponseBody
	public List<EmploymentHistoryDto> findByEmployeeId(
			@PathVariable Integer employeeId
		)
	{
		List<EmploymentHistory> entities = this.repository
			.findByEmployeeId( employeeId );
		return entities.stream()
			.map(employment -> EmploymentHistoryDto.fromEntity(employment))
			.collect(Collectors.toList());
	}
}