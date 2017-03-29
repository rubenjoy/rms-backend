package com.mitrais.rms.repository;

import java.util.List;

import com.mitrais.rms.entity.Location;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
	collectionResourceRel = "locations",
	path = "locations",
	exported = false
)
public interface LocationRepository
	extends CrudRepository<Location, Integer>
{
	Location findByIdAndEmployeeId(Integer id, Integer employeeId);
	List<Location> findByEmployeeId(Integer employeeId);
}