package com.mitrais.rms.controller.commons.dto;

import com.mitrais.rms.entity.JobDescription;

public class JobDescriptionDto
{
	public static enum OpType {CREATE, READ, UPDATE, DELETE};
	private Integer id;
	private String description;
	private OpType op = OpType.READ;

	public JobDescriptionDto()
	{
		this.id = null;
		this.description = null;
	}

	public JobDescriptionDto(
			Integer id, String description
		)
	{
		this.id = id;
		this.description = description;
	}

	public Integer getId()
	{
		return id;
	}

	public JobDescriptionDto setId(Integer id)
	{
		this.id = id;
		return this;
	}

	public OpType getOp() {
		return op;
	}

	public JobDescriptionDto setOp(OpType op)
	{
		this.op = op;
		return this;
	}

	public String getDescription()
	{
		return description;
	}

	public JobDescriptionDto setDescription(String description)
	{
		this.description = description;
		return this;
	}

	public JobDescription createEntity()
	{
		JobDescription entity = new JobDescription();
		entity.setDescription(description);
		return entity;
	}

	public static JobDescriptionDto fromEntity(JobDescription entity)
	{
		return new JobDescriptionDto(
			entity.getId(), entity.getDescription());
	}

	public JobDescription updateEntity(JobDescription entity)
	{
		if (description != null)
			entity.setDescription(description);
		return entity;
	}
}