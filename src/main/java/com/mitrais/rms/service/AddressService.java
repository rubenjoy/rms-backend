package com.mitrais.rms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.rms.entity.Address;
import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.repository.AddressRepository;

@Service
public class AddressService
	implements IWeakEntityService<Employee,Address>
{
	@Autowired
	private AddressRepository addressRepo;

	/**
	 *  @param employee, must be exists
	 *  @param address
	 *  @return Address
	 **/
	@Override
	public Address createAssociation(
			Employee employee, Address address
		)
	{
		address.setEmployee(employee);
		employee.getAddresses().add(address);
		this.addressRepo.save(address);
		assert address.getId() != null : address;
		return address;
	}
	/**
	 *  @param address
	 **/
	@Override
	public void deleteAssociation(Address address)
	{
		address.getEmployee()
			.getAddresses().remove(address);
		this.addressRepo.delete(address);
	}
	
	/**
	 *  @param address
	 *  @return Address
	 **/
	@Override
	public Address updateAssociation(Address address)
	{
		this.addressRepo.save(address);
		return address;
	}
}