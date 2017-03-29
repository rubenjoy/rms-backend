package com.mitrais.rms.controller.employment;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPutController;
import com.mitrais.rms.controller.commons.dto.EmploymentHistoryDto;
import com.mitrais.rms.controller.commons.exception.EmploymentNotFoundException;
import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.entity.JobDescription;
import com.mitrais.rms.repository.EmploymentRepository;
import com.mitrais.rms.service.EmploymentService;

@RestController(value = "employmentPutController")
@RequestMapping(value = "/employees/{employeeId}/employments")
public class PutController 
	implements IPutController<EmploymentHistoryDto>
{
	@Autowired
	private EmploymentRepository repository;
	@Autowired
	private EmploymentService service;

	@Override
	@RequestMapping(
		method = {RequestMethod.PUT, RequestMethod.PATCH},
		value = "/{employmentId}"
	)
	@ResponseBody
	public EmploymentHistoryDto save(
			@PathVariable Integer employeeId, 
			@PathVariable Integer employmentId, 
			@RequestBody EmploymentHistoryDto dto
		)
	{
		EmploymentHistory entity = this.repository
			.findByIdAndEmployeeId(
				employmentId, employeeId
			);
		Set<JobDescription> deletedEntities = 
			dto.getDeletedEntities(entity);
		this.service.deleteJobDesc( 
			entity, deletedEntities
		);
		entity = dto.updateEntity( entity );
		this.service.updateAssociation( entity );

		return EmploymentHistoryDto.fromEntity( entity );
	}
}