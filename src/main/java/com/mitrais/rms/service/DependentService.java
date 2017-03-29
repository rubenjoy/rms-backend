package com.mitrais.rms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.rms.entity.Dependent;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.DependentRepository;

@Service
public class DependentService
	implements IWeakEntityService<Employee,Dependent>
{
	@Autowired
	private DependentRepository repository;
	/** 
	 *  @param employee
	 *  @param dependent
	 *  @return Dependent
	 **/
	@Override
	public Dependent createAssociation(Employee employee, Dependent dependent)
	{
		dependent.setEmployee(employee);
		employee.getDependents().add(dependent);
		this.repository.save(dependent);
		assert dependent.getId() != null : dependent;
		return dependent;
	}

	/**
	 *  @param dependent
	 **/
	@Override
	public void deleteAssociation(Dependent dependent)
	{
		dependent.getEmployee()
			.getDependents().remove(dependent);
		this.repository.delete(dependent);
	}

	/**
	 *  @param dependent
	 *  @return Dependent
	 **/
	@Override
	public Dependent updateAssociation(Dependent dependent)
	{
		this.repository.save(dependent);
		return dependent;
	}
}