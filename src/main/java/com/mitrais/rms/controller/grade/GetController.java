package com.mitrais.rms.controller.grade;

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
import com.mitrais.rms.controller.commons.dto.GradeHistoryDto;
import com.mitrais.rms.controller.commons.exception.GradeNotFoundException;
import com.mitrais.rms.entity.GradeHistory;
import com.mitrais.rms.repository.GradeRepository;

@RestController(value = "gradeGetController")
@RequestMapping(value = "/employees/{employeeId}/grades")
public class GetController
	implements IGetController<GradeHistoryDto>
{
	@Autowired
	private GradeRepository repository;

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = "/{gradeId}"
	)
	@ResponseBody
	public GradeHistoryDto findByEmployeeIdAndId(
			@PathVariable
			Integer employeeId, 
			@PathVariable
			Integer gradeId
		)
	{
		GradeHistory grade = this.repository
			.findByIdAndEmployeeId(
				gradeId, employeeId
			);
		if (grade == null)
			throw new GradeNotFoundException();
		return GradeHistoryDto.fromEntity(grade);
	}

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = ""
	)
	@ResponseBody
	public List<GradeHistoryDto> findByEmployeeId(
			@PathVariable
			Integer employeeId
		)
	{
		List<GradeHistory> grades = this.repository
			.findByEmployeeId( employeeId );
		return grades.stream()
			.map(grade -> GradeHistoryDto.fromEntity(grade))
			.collect(Collectors.toList());
	}
}