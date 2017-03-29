package com.mitrais.rms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.Location;
import com.mitrais.rms.repository.LocationRepository;

@Service
public class LocationService
	implements IWeakEntityService<Employee,Location>
{
	@Autowired
	private LocationRepository repository;

	/**
	 *  @param employee, must be exists,
	 *  @param location to be persisted
	 *  @return Location persisted entity
	 **/
	@Override
	public Location createAssociation(Employee employee, Location location)
	{
		location.setEmployee(employee);
		employee.getLocations().add(location);
		this.repository.save(location);
		assert location.getId() != null : location;
		return location;
	}

	/**
	 *  @param location 
	 **/
	@Override
	public void deleteAssociation(Location location)
	{
		location.getEmployee()
			.getLocations().remove(location);
		this.repository.delete(location);
	}

	/**
	 *  @param location 
	 *  @return Location 
	 **/
	@Override
	public Location updateAssociation(Location location)
	{
		this.repository.save(location);
		return location;
	}
}