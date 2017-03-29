package com.mitrais.rms.controller.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPutController;
import com.mitrais.rms.controller.commons.dto.LocationDto;
import com.mitrais.rms.controller.commons.exception.LocationNotFoundException;
import com.mitrais.rms.entity.Location;
import com.mitrais.rms.repository.LocationRepository;
import com.mitrais.rms.service.LocationService;

@RestController(value = "locationPutController")
@RequestMapping(value = "/employees/{employeeId}/locations")
public class PutController
	implements IPutController<LocationDto>
{
	@Autowired
	private LocationRepository repository;
	@Autowired
	private LocationService service;

	@RequestMapping(
		method = {RequestMethod.PUT, RequestMethod.PATCH},
		value = "/{locationId}"
	)
	@ResponseBody
	public LocationDto save(
			@PathVariable
			Integer employeeId,
			@PathVariable
			Integer locationId,
			@RequestBody
			LocationDto dto
		)
	{
		Location entity = this.repository
			.findByIdAndEmployeeId(
				locationId, employeeId
			);
		if (entity == null) 
			throw new LocationNotFoundException();
		entity = dto.updateEntity(entity);
		this.service.updateAssociation(entity);
		return LocationDto.fromEntity(entity);
	}
}