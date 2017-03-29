package com.mitrais.rms.controller.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPostController;
import com.mitrais.rms.controller.commons.dto.GradeHistoryDto;
import com.mitrais.rms.controller.commons.exception.EmployeeNotFoundException;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.GradeHistory;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.GradeService;

@RestController(value = "gradePostController")
@RequestMapping(value = "/employees/{employeeId}/grades")
public class PostController
	implements IPostController<GradeHistoryDto>
{
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private GradeService service;

	@Override
	@RequestMapping(
		method = RequestMethod.POST,
		value = ""
	)
	@ResponseBody
	public GradeHistoryDto save(
			@PathVariable
			Integer employeeId, 
			@RequestBody
			GradeHistoryDto dto
		)
	{
		Employee employee = this.employeeRepo
			.findOne( employeeId );
		if (employee == null)
			throw new EmployeeNotFoundException();
		GradeHistory grade = dto.createEntity();
		this.service.createAssociation(
			employee, grade
		);
		return GradeHistoryDto.fromEntity(grade);
	}
}