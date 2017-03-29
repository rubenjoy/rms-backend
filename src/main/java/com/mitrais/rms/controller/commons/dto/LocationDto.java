package com.mitrais.rms.controller.commons.dto;

import com.mitrais.rms.entity.Location;
import static com.mitrais.rms.utils.DateFormatter.format;
import static com.mitrais.rms.utils.DateFormatter.parse;

public class LocationDto
{
	private Integer id;
	private String branchOffice;
	private String startDate;
	private String endDate;
	private Boolean activeInd;
	private Integer employeeId = null;
	private final static
		String URL_ID_TEMPLATE = "/employees/%d/locations/%d";

	public LocationDto()
	{
		this.id = null;
		this.branchOffice = null;
		this.startDate = null;
		this.endDate = null;
		this.activeInd = null;
	}

	public LocationDto(
			Integer id,
			String branchOffice,
			String startDate,
			String endDate,
			Boolean activeInd
		)
	{
		this.id = id;
		this.branchOffice = branchOffice;
		this.startDate = startDate;
		this.endDate = endDate;
		this.activeInd = activeInd;
	}

	public String getId()
	{
		if (employeeId != null && id != null)
			return String.format(URL_ID_TEMPLATE,
				employeeId, id);
		return id.toString();
	}

	public LocationDto setId(Integer id)
	{
		this.id = id;
		return this;
	}

	public String getBranchOffice()
	{
		return branchOffice;
	}

	public LocationDto setBranchOffice(
			String branchOffice
		)
	{
		this.branchOffice = branchOffice;
		return this;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public LocationDto setStartDate(
			String startDate
		)
	{
		this.startDate = startDate;
		return this;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public LocationDto setEndDate(
			String endDate
		)
	{
		this.endDate = endDate;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public LocationDto setActiveInd(
			Boolean activeInd
		)
	{
		this.activeInd = activeInd;
		return this;
	}

	public static LocationDto fromEntity(
			Location entity
		)
	{
		LocationDto dto = new LocationDto();
		dto.setId(entity.getId())
			.setBranchOffice(entity.getBranchOffice())
			.setActiveInd(entity.getActiveInd());
		if (entity.getStartDate() != null)
			dto.setStartDate(format(entity.getStartDate()));
		if (entity.getEndDate() != null)
			dto.setEndDate(format(entity.getEndDate()));
		dto.employeeId = entity.getEmployeeId();
		return dto;
	}

	/**
	 *  Create unpersisted entity
	 *  @return Location
	 **/
	public Location createEntity()
	{
		Location location = new Location();
		location.setBranchOffice(branchOffice)
			.setStartDate(parse(startDate))
			.setActiveInd(activeInd);
		if (endDate != null)
			location.setEndDate(parse(endDate));
		return location;
	}

	/**
	 *  Update persisted entity with DTO instance
	 *  @param entity to be updated
	 *  @return Location entity already updated
	 **/
	public Location updateEntity(
			Location entity
		)
	{
		if (branchOffice != null)
			entity.setBranchOffice(branchOffice);
		if (startDate != null)
			entity.setStartDate(parse(startDate));
		if (endDate != null)
			entity.setEndDate(parse(endDate));
		if (activeInd != null)
			entity.setActiveInd(activeInd);
		return entity;
	}
}