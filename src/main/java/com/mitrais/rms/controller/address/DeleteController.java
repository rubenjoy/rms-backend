package com.mitrais.rms.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IDeleteController;
import com.mitrais.rms.entity.Address;
import com.mitrais.rms.repository.AddressRepository;
import com.mitrais.rms.service.AddressService;

@RestController(value = "addressDeleteController")
@RequestMapping(value = "/employees/{employeeId}/addresses")
public class DeleteController
	implements IDeleteController
{
	@Autowired
	private AddressService service;
	@Autowired
	private AddressRepository repository;

	@Override
	@RequestMapping(
		method = RequestMethod.DELETE,
		value = "/{addressId}"
	)
	@ResponseStatus(
		value = HttpStatus.NO_CONTENT
	)
	public void delete(
			@PathVariable
			Integer employeeId,
			@PathVariable
			Integer addressId
		)
	{
		Address entity = repository
			.findByIdAndEmployeeId(
				addressId, employeeId
			);
		if (entity == null) 
			return;
		this.service.deleteAssociation(entity);
	}
}