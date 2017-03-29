package com.mitrais.rms.repository;

import com.mitrais.rms.entity.Employee;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
	collectionResourceRel = "employees",
	collectionResourceDescription = @Description("RMS Employees collection"),
	path = "employees",
	exported = false
)
public interface EmployeeRepository
	extends PagingAndSortingRepository<Employee,Integer>
{
}