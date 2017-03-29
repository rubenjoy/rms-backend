package com.mitrais.rms.controller.commons.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mitrais.rms.entity.Employee;
import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;
import static com.mitrais.rms.utils.DateFormatter.format;
import static com.mitrais.rms.utils.DateFormatter.parse;

public class EmployeeDto
{
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private Gender gender;
	private MaritalStatus maritalStatus;
	private Nationality nationality;
	private String division;
	private String hireDate;
	private String suspendDate;
	private String birthDate;
	private String employmentStatus;
	private Boolean activeInd;
	private String jobFamily;
	private String jobTitle;
	private String businessUnit;

	private List<AddressDto> addresses;
	private List<LocationDto> locations;
	private List<DependentDto> dependents;
	private List<GradeHistoryDto> grades;
	private List<EmploymentHistoryDto> employments;

	private final static
		String URL_ID_TEMPLATE = "/employees/%d";

	public EmployeeDto()
	{
		this.addresses = new ArrayList<AddressDto>();
		this.locations = new ArrayList<LocationDto>();
		this.dependents = new ArrayList<DependentDto>();
		this.grades = new ArrayList<GradeHistoryDto>();
		this.employments = new ArrayList<EmploymentHistoryDto>();
	}

	public String getId()
	{
		return String.format(
			URL_ID_TEMPLATE, id);
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus()
	{
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}

	public Nationality getNationality()
	{
		return nationality;
	}

	public void setNationality(Nationality nationality)
	{
		this.nationality = nationality;
	}

	public String getDivision()
	{
		return division;
	}

	public void setDivision(String division)
	{
		this.division = division;
	}

	public String getHireDate()
	{
		return hireDate;
	}

	public void setHireDate(String hireDate)
	{
		this.hireDate = hireDate;
	}

	public String getSuspendDate()
	{
		return suspendDate;
	}

	public void setSuspendDate(String suspendDate)
	{
		this.suspendDate = suspendDate;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getEmploymentStatus()
	{
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus)
	{
		this.employmentStatus = employmentStatus;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	public String getJobFamily()
	{
		return jobFamily;
	}

	public void setJobFamily(String jobFamily)
	{
		this.jobFamily = jobFamily;
	}

	public String getBusinessUnit()
	{
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit)
	{
		this.businessUnit = businessUnit;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public void setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
	}

	public List<AddressDto> getAddresses()
	{
		return addresses;
	}

	public List<LocationDto> getLocations()
	{
		return locations;
	}

	public List<DependentDto> getDependents()
	{
		return dependents;
	}

	public List<GradeHistoryDto> getGrades()
	{
		return grades;
	}

	public List<EmploymentHistoryDto> getEmployments()
	{
		return employments;
	}

	/**
	 *  create unpersisted Employee entity 
	 *  @return Employee
	 **/
	public Employee createEntity()
	{
		Employee entity = new Employee();
		entity.setName(name)
			.setPhone(phone)
			.setEmail(email)
			.setGender(gender)
			.setNationality(nationality)
			.setMaritalStatus(maritalStatus)
			.setBirthDate(parse(birthDate))
			.setJobFamily(jobFamily)
			.setJobTitle(jobTitle)
			.setDivision(division)
			.setBusinessUnit(businessUnit)
			.setActiveInd(activeInd)
			.setEmploymentStatus(employmentStatus);
		if (suspendDate != null)
			entity.setSuspendDate(parse(suspendDate));
		if (hireDate != null)
			entity.setHireDate(parse(hireDate));
		return entity;
	}

	/**
	 *  @param entity
	 *  @return EmployeeDto
	 **/
	public static EmployeeDto fromEntity(Employee entity)
	{
		EmployeeDto dto = new EmployeeDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
		dto.setPhone(entity.getPhone());
		dto.setGender(entity.getGender());
		dto.setMaritalStatus(entity.getMaritalStatus());
		dto.setNationality(entity.getNationality());
		dto.setActiveInd(entity.getActiveInd());
		if (entity.getSuspendDate() != null)
			dto.setSuspendDate(format(entity.getSuspendDate()));
		if (entity.getHireDate() != null)
			dto.setHireDate(format(entity.getHireDate()));
		dto.setBirthDate(format(entity.getBirthDate()));
		dto.setJobFamily(entity.getJobFamily());
		dto.setJobTitle(entity.getJobTitle());
		dto.setDivision(entity.getDivision());
		dto.setBusinessUnit(entity.getBusinessUnit());
		dto.setEmploymentStatus(entity.getEmploymentStatus());
		dto.getAddresses().addAll(
			entity.getAddresses().stream()
				.map(address -> AddressDto.fromEntity(address))
				.collect(Collectors.toList())
		);
		dto.getLocations().addAll(
			entity.getLocations().stream()
				.map(location -> LocationDto.fromEntity(location))
				.collect(Collectors.toList())
		);
		dto.getDependents().addAll(
			entity.getDependents().stream()
				.map(dependent -> DependentDto.fromEntity(dependent))
				.collect(Collectors.toList())
		);
		dto.getGrades().addAll(
			entity.getGrades().stream()
				.map(grade -> GradeHistoryDto.fromEntity(grade))
				.collect(Collectors.toList())
		);
		dto.getEmployments().addAll(
			entity.getEmployments().stream()
				.map(employment -> EmploymentHistoryDto.fromEntity(employment))
				.collect(Collectors.toList())
		);
		return dto;
	}

	/**
	 *  @param entity
	 *  @return Employee
	 **/
	public Employee updateEntity(Employee entity)
	{
		if (name != null)
			entity.setName(name);
		if (phone != null)
			entity.setPhone(phone);
		if (email != null)
			entity.setEmail(email);
		if (gender != null)
			entity.setGender(gender);
		if (maritalStatus != null)
			entity.setMaritalStatus(maritalStatus);
		if (nationality != null)
			entity.setNationality(nationality);
		if (suspendDate != null)
			entity.setSuspendDate(parse(suspendDate));
		if (hireDate != null)
			entity.setHireDate(parse(hireDate));
		if (birthDate != null)
			entity.setBirthDate(parse(birthDate));
		if (jobFamily != null)
			entity.setJobFamily(jobFamily);
		if (jobTitle != null)
			entity.setJobTitle(jobTitle);
		if (division != null)
			entity.setDivision(division);
		if (activeInd != null)
			entity.setActiveInd(activeInd);
		if (employmentStatus != null)
			entity.setEmploymentStatus(employmentStatus);
		if (businessUnit != null)
			entity.setBusinessUnit(businessUnit);
		return entity;
	}
}