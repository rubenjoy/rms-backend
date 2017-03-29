package com.mitrais.rms.controller.address;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.rms.controller.IGetController;
import com.mitrais.rms.controller.commons.dto.AddressDto;
import com.mitrais.rms.controller.commons.exception.AddressNotFoundException;
import com.mitrais.rms.entity.Address;
import com.mitrais.rms.repository.AddressRepository;

@RestController(value = "addressGetController")
@RequestMapping(value = "/employees/{employeeId}/addresses")
public class GetController
	implements IGetController<AddressDto>
{
	@Autowired
	private AddressRepository repository;

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = "/{addressId}"
	)
	@ResponseBody
	public AddressDto findByEmployeeIdAndId(
			@PathVariable
			Integer employeeId,
			@PathVariable
			Integer addressId
		)
		throws AddressNotFoundException
	{
		Address address = repository
			.findByIdAndEmployeeId(
				addressId, employeeId
			);
		if (address == null) 
			throw new AddressNotFoundException();
		assert address.getId() != null : address;
		assert address.getAddress() != null : address;
		assert address.getActiveInd() != null : address;
		return AddressDto.fromEntity(address);
	}

	@RequestMapping(
		method = RequestMethod.GET,
		value = ""
	)
	@ResponseBody
	public List<AddressDto> findByEmployeeId(
			@PathVariable
			Integer employeeId
		)
	{
		List<Address> addresses = repository
			.findByEmployeeId( employeeId );
		return addresses.stream()
			.map(address -> AddressDto.fromEntity(address))
			.collect(Collectors.toList());
	}
}