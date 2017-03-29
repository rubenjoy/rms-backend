package com.mitrais.rms.controller.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPostController;
import com.mitrais.rms.controller.commons.dto.LocationDto;
import com.mitrais.rms.controller.commons.exception.EmployeeNotFoundException;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.Location;
import com.mitrais.rms.repository.EmployeeRepository;
import com.mitrais.rms.service.LocationService;

@RestController(value = "locationPostController")
@RequestMapping(value = "/employees/{employeeId}/locations")
public class PostController
	implements IPostController<LocationDto>
{
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private LocationService service;

	@Override
	@RequestMapping(
		method = RequestMethod.POST,
		value = ""
	)
	@ResponseBody
	public LocationDto save(
			@PathVariable
			Integer employeeId,
			@RequestBody
			LocationDto dto
		)
		throws EmployeeNotFoundException
	{
		Employee employee = employeeRepo
			.findOne( employeeId );
		if (employee == null)
			throw new EmployeeNotFoundException();
		Location location = dto.createEntity();
		this.service.createAssociation(
			employee, location
		);
		return LocationDto.fromEntity(location);
	}
}