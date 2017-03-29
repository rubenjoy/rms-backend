package com.mitrais.rms.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.entity.JobDescription;
import com.mitrais.rms.repository.EmploymentRepository;
import com.mitrais.rms.repository.JobDescriptionRepository;

@Service
public class EmploymentService
	implements IWeakEntityService<Employee, EmploymentHistory>
{
	@Autowired
	private EmploymentRepository repository;
	@Autowired
	private JobDescriptionRepository jobDescRepo;

	/** 
	 *  @param employee
	 *  @param employment
	 *  @return EmploymentHistory
	 **/
	public EmploymentHistory createAssociation(
			Employee employee, EmploymentHistory employment
		)
	{
		employment.setEmployee( employee );
		employee.getEmployments().add( employment );
		this.repository.save( employment );
		assert employment.getId() != null : employment;
		return employment;
	}


	public void deleteAssociation(
			EmploymentHistory employment
		)
	{
		employment.getEmployee()
			.getEmployments().remove( employment );
		this.repository.delete( employment );
	}

	public EmploymentHistory updateAssociation(
			EmploymentHistory employment
		)
	{
		this.repository.save( employment );
		return employment;
	}

	public EmploymentHistory deleteJobDesc(
			EmploymentHistory employment,
			Set<JobDescription> deletedEntities
		)
	{
		employment.getJobDesc().removeAll(
			deletedEntities
		);
		this.jobDescRepo.delete( deletedEntities );
		return employment;
	}
}