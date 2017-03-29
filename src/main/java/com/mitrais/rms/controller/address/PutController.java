package com.mitrais.rms.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IPutController;
import com.mitrais.rms.controller.commons.dto.AddressDto;
import com.mitrais.rms.controller.commons.exception.AddressNotFoundException;
import com.mitrais.rms.entity.Address;
import com.mitrais.rms.repository.AddressRepository;
import com.mitrais.rms.service.AddressService;

@RestController(value = "addressPutController")
@RequestMapping(value = "/employees/{employeeId}/addresses")
public class PutController
	implements IPutController<AddressDto>
{
	@Autowired
	private AddressRepository repository;
	@Autowired
	private AddressService service;

	@RequestMapping(
		method = {RequestMethod.PUT, RequestMethod.PATCH},
		value = "/{addressId}"
	)
	@ResponseBody
	public AddressDto save(
			@PathVariable
			Integer employeeId,
			@PathVariable
			Integer addressId,
			@RequestBody
			AddressDto dto
		)
	{
		Address address = repository
			.findByIdAndEmployeeId(
				addressId, employeeId
			);
		if (address == null)
			throw new AddressNotFoundException();
		address = dto.updateEntity(address);
		this.service.updateAssociation(address);
		return AddressDto.fromEntity(address);
	}
}