package com.mitrais.rms.controller.location;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IGetController;
import com.mitrais.rms.controller.commons.dto.LocationDto;
import com.mitrais.rms.controller.commons.exception.LocationNotFoundException;
import com.mitrais.rms.entity.Location;
import com.mitrais.rms.repository.LocationRepository;

@RestController(value = "locationGetController")
@RequestMapping(value = "/employees/{employeeId}/locations")
public class GetController
	implements IGetController<LocationDto>
{
	@Autowired
	private LocationRepository repository;

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = "/{locationId}"
	)
	@ResponseBody
	public LocationDto findByEmployeeIdAndId(
			@PathVariable
			Integer employeeId,
			@PathVariable
			Integer locationId
		)
		throws LocationNotFoundException
	{
		Location location = repository
			.findByIdAndEmployeeId(
				locationId, employeeId
			);
		if (location == null) 
			throw new LocationNotFoundException();
		return LocationDto.fromEntity(location);
	}

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = ""
	)
	@ResponseBody
	public List<LocationDto> findByEmployeeId(
			@PathVariable
			Integer employeeId
		)
	{
		List<Location> locations = repository
			.findByEmployeeId( employeeId );
		return locations.stream()
			.map(location -> LocationDto.fromEntity(location))
			.collect(Collectors.toList());
	}
}