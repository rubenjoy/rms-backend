package com.mitrais.rms.repository;

import java.util.List;

import com.mitrais.rms.entity.Address;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
	collectionResourceRel = "addresses",
	collectionResourceDescription = @Description("RMS Addresses collection"),
	path = "addresses",
	exported = false
)
public interface AddressRepository
	extends CrudRepository<Address, Integer>
{
	Address findByIdAndEmployeeId(
			Integer id, Integer employeeId
		);
	List<Address> findByEmployeeId(
			Integer employeeId
		);
}