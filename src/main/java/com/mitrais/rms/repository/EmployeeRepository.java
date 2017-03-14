package com.mitrais.rms.repository;

import com.mitrais.rms.entity.Employee;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
	collectionResourceRel = "employees",
	collectionResourceDescription = @Description("RMS Employees collection"),
	path = "employees"
)
public interface EmployeeRepository
	extends PagingAndSortingRepository<Employee,Integer>
{
}