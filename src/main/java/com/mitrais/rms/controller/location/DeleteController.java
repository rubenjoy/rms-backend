package com.mitrais.rms.controller.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IDeleteController;
import com.mitrais.rms.controller.commons.exception.LocationNotFoundException;
import com.mitrais.rms.entity.Location;
import com.mitrais.rms.repository.LocationRepository;
import com.mitrais.rms.service.LocationService;

@RestController(value = "locationDeleteController")
@RequestMapping(value = "/employees/{employeeId}/locations")
public class DeleteController
	implements IDeleteController
{
	@Autowired
	private LocationRepository repository;
	@Autowired
	private LocationService service;

	@Override
	@RequestMapping(
		method = RequestMethod.DELETE,
		value = "/{locationId}"
	)
	@ResponseStatus(
		value = HttpStatus.NO_CONTENT
	)
	public void delete(
			@PathVariable
			Integer employeeId,
			@PathVariable
			Integer locationId
		)
	{
		Location entity = this.repository
			.findByIdAndEmployeeId(
				locationId, employeeId
			);
		if (entity == null)
			throw new LocationNotFoundException();
		this.service.deleteAssociation(
			entity
		);
	}
}