package com.mitrais.rms.controller.commons.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitrais.rms.entity.Address;
import com.mitrais.rms.repository.AddressRepository;

public class AddressDto
{
	private Integer id;
	private String address;
	private Boolean activeInd;
	private Integer employeeId = null;

	private final static 
		String URL_ID_TEMPLATE = "/employees/%d/addresses/%d";

	@Autowired
	private AddressRepository repository;

	public AddressDto()
	{
		id = null;
		address = null; 
		activeInd =  null;
	}

	public AddressDto(
			Integer id,
			String address,
			Boolean activeInd
		)
	{
		this.id = id;
		this.address = address;
		this.activeInd = activeInd;
	}

	public String getId()
	{
		if (employeeId != null && id != null)
			return String.format(URL_ID_TEMPLATE,
				employeeId, id);
		return id.toString();
	}

	public AddressDto setId(Integer id)
	{
		this.id = id;
		return this;
	}

	public String getAddress()
	{
		return address;
	}

	public AddressDto setAddress(String address)
	{
		this.address = address;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public AddressDto setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	/**
	 *  Get persisted entity if exists
	 *  @return Address persisted entity if exists, 
	 *			otherwise null
	 **/
	public Address toEntity()
	{
		Address result = repository
			.findOne(id);	
		return result;
	}

	public static AddressDto fromEntity(Address entity)
	{
		AddressDto dto = new AddressDto(
			entity.getId(), entity.getAddress(), 
			entity.getActiveInd()
		);	
		dto.employeeId = entity.getEmployeeId();
		return dto;
	}

	/**
	 *  Create unpersisted Address with empty employee field
	 *  @return Address unpersisted entity
	 **/
	public Address createEntity()
	{
		Address entity = new Address();
		entity.setAddress(address)
			.setActiveInd(activeInd);
		return entity;
	}

	public Address updateEntity(Address entity)
	{
		if (this.address != null)
			entity.setAddress(this.address);
		if (this.activeInd != null)
			entity.setActiveInd(this.activeInd);
		return entity;
	}
}