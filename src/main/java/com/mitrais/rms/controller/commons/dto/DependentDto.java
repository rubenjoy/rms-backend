package com.mitrais.rms.controller.commons.dto;

import com.mitrais.rms.entity.Dependent;
import com.mitrais.rms.entity.enumerated.DependentType;
import static com.mitrais.rms.utils.DateFormatter.format;
import static com.mitrais.rms.utils.DateFormatter.parse;

public class DependentDto
{
	private Integer id;
	private DependentType type;
	private String name;
	private String birthDate;
	private Boolean activeInd;
	private Integer employeeId = null;

	private final static
		String URL_ID_TEMPLATE = "/employees/%d/dependents/%d";

	public DependentDto()
	{
		this.id = null;
		this.type = null;
		this.name = null;
		this.birthDate = null;
		this.activeInd = null;
	}

	public DependentDto(
			Integer id,
			DependentType type,
			String name, 
			String birthDate,
			Boolean activeInd
		)
	{
		this.id = id;
		this.type = type;
		this.name = name;
		this.birthDate = birthDate;
		this.activeInd = activeInd;
	}

	public String getId()
	{
		if (employeeId != null && id != null)
			return String.format(URL_ID_TEMPLATE,
				employeeId, id);
		return id.toString();
	}

	public DependentDto setId(Integer id)
	{
		this.id = id;
		return this;
	}

	public DependentType getType()
	{
		return type;
	}

	public DependentDto setType(DependentType type)
	{
		this.type = type;
		return this;
	}

	public String getName()
	{
		return name;
	}

	public DependentDto setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public DependentDto setBirthDate(
			String birthDate
		)
	{
		this.birthDate = birthDate;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public DependentDto setActiveInd(
			Boolean activeInd
		)
	{
		this.activeInd = activeInd;
		return this;
	}

	public DependentType getRelation()
	{
		return getType();
	}

	public DependentDto setRelation(DependentType relation) {
		return setType(relation);
	}

	public Dependent createEntity()
	{
		Dependent dependent = new Dependent();
		dependent.setType(type)
			.setName(name)
			.setBirthDate(parse(birthDate))
			.setActiveInd(activeInd);
		return dependent;
	}

	public static DependentDto fromEntity(
			Dependent entity
		)
	{
		DependentDto dto = new DependentDto(
			entity.getId(),
			entity.getType(),
			entity.getName(),
			format(entity.getBirthDate()),
			entity.getActiveInd()
		);
		dto.employeeId = entity.getEmployeeId();
		return dto;
	}

	public Dependent updateEntity(
			Dependent entity
		)
	{
		if (name != null)
			entity.setName(name);
		if (type != null)
			entity.setType(type);
		if (birthDate != null)
			entity.setBirthDate(parse(birthDate));
		if (activeInd != null)
			entity.setActiveInd(activeInd);
		return entity;
	}
}