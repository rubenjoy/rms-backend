package com.mitrais.rms.repository;

import java.util.List;

import com.mitrais.rms.entity.Dependent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
	collectionResourceRel = "dependents",
	path = "dependents",
	exported = false
)
public interface DependentRepository
	extends CrudRepository<Dependent, Integer>
{
	Dependent findByIdAndEmployeeId(Integer id, Integer employeeId);
	List<Dependent> findByEmployeeId(Integer employeeId);
}