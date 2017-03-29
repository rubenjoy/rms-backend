package com.mitrais.rms.controller.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IDeleteController;
import com.mitrais.rms.entity.GradeHistory;
import com.mitrais.rms.repository.GradeRepository;
import com.mitrais.rms.service.GradeService;

@RestController(value = "gradeDeleteController")
@RequestMapping(value = "/employees/{employeeId}/grades")
public class DeleteController
	implements IDeleteController
{
	@Autowired
	private GradeRepository repository;
	@Autowired
	private GradeService service;

	@Override
	@RequestMapping(
		method = RequestMethod.DELETE,
		value = "/{gradeId}"
	)
	@ResponseStatus(
		value = HttpStatus.NO_CONTENT
	)
	public void delete(
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
		if (grade != null)
			this.service.deleteAssociation(grade);
	}
}