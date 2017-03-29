package com.mitrais.rms.controller.commons.dto;

import java.util.Optional;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.Location;

public class ShortEmployeeDto
{
	private Integer id;
	private String name;
	private String phone;
	private String jobFamily;
	private String jobTitle;
	private String division;
	private String location = null;

	private final static 
		String URL_ID_TEMPLATE = "/employees/%d";

	public ShortEmployeeDto(Integer id, String name, 
			String phone, String jobFamily, 
			String jobTitle, String division)
	{
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.jobTitle = jobTitle;
		this.jobFamily = jobFamily;
		this.division = division;
	}

	public static ShortEmployeeDto fromEntity(Employee entity)
	{
		ShortEmployeeDto dto = new ShortEmployeeDto(
			entity.getId(), entity.getName(), entity.getPhone(),
			entity.getJobFamily(), entity.getJobTitle(),
			entity.getDivision()
		);
		Optional<Location> location = entity.getLocations().stream()
			.filter(e -> e.getActiveInd())
			.sorted((e1, e2) -> -1 * e1.getStartDate().compareTo(e2.getStartDate()))
			.findFirst();
		if (location.isPresent()) {
			dto.location = location.get().getBranchOffice();
		}
		return dto;
	}

	public String getId()
	{
		return String.format(URL_ID_TEMPLATE, id);
	}

	public String getName()
	{
		return name;
	}

	public String getPhone()
	{
		return phone;
	}

	public String jobFamily()
	{
		return jobFamily;
	}

	public String jobTitle()
	{
		return jobTitle;
	}

	public String getDivision()
	{
		return division;
	}

	public String getLocation()
	{
		return location;
	}
}