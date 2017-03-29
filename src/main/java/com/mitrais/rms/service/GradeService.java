package com.mitrais.rms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.GradeHistory;
import com.mitrais.rms.repository.GradeRepository;

@Service
public class GradeService
	implements IWeakEntityService<Employee,GradeHistory>
{
	@Autowired
	private GradeRepository repository;
	
	/**
	 *  @param employee
	 *  @param grade
	 *  @return GradeHistory
	 **/
	@Override
	public GradeHistory createAssociation(Employee employee, GradeHistory grade)
	{
		grade.setEmployee(employee);
		employee.getGrades().add(grade);
		this.repository.save(grade);
		assert grade.getId() != null : grade;
		return grade;
	}

	/**
	 *  @param grade
	 **/
	@Override
	public void deleteAssociation(GradeHistory grade)
	{
		grade.getEmployee()
			.getGrades().remove(grade);
		this.repository.delete(grade);
	}

	/**
	 *  @param grade
	 *  @return GradeHistory
	 **/
	@Override
	public GradeHistory updateAssociation(GradeHistory grade)
	{
		this.repository.save(grade);
		return grade;
	}
}