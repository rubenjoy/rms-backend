package com.mitrais.rms.repository;

import java.util.List;

import com.mitrais.rms.entity.EmploymentHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
	collectionResourceRel = "employments",
	path = "employments",
	exported = false
)
public interface EmploymentRepository
	extends CrudRepository<EmploymentHistory, Integer>
{
	public EmploymentHistory findByIdAndEmployeeId(Integer id, Integer employeeId);
	public List<EmploymentHistory> findByEmployeeId(Integer employeeId);
}