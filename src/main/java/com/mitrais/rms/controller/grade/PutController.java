package com.mitrais.rms.controller.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPutController;
import com.mitrais.rms.controller.commons.dto.GradeHistoryDto;
import com.mitrais.rms.controller.commons.exception.GradeNotFoundException;
import com.mitrais.rms.entity.GradeHistory;
import com.mitrais.rms.repository.GradeRepository;
import com.mitrais.rms.service.GradeService;

@RestController(value = "gradePutController")
@RequestMapping(value = "/employees/{employeeId}/grades")
public class PutController
	implements IPutController<GradeHistoryDto>
{
	@Autowired
	private GradeRepository repository;
	@Autowired
	private GradeService service;

	@Override
	@RequestMapping(
		method = {RequestMethod.PUT,RequestMethod.PATCH},
		value = "/{gradeId}"
	)
	@ResponseBody
	public GradeHistoryDto save(
			@PathVariable
			Integer employeeId, 
			@PathVariable
			Integer gradeId, 
			@RequestBody
			GradeHistoryDto dto
		)
	{
		GradeHistory entity = this.repository
			.findByIdAndEmployeeId(
				gradeId, employeeId
			);
		if (entity == null) 
			throw new GradeNotFoundException();
		entity = this.service.updateAssociation(
					dto.updateEntity(entity));
		return GradeHistoryDto.fromEntity(entity);
	}
}