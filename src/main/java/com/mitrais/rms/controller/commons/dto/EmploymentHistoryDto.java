package com.mitrais.rms.controller.commons.dto;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.mitrais.rms.entity.EmploymentHistory;
import com.mitrais.rms.entity.JobDescription;
import com.mitrais.rms.repository.JobDescriptionRepository;
import static com.mitrais.rms.utils.DateFormatter.format;
import static com.mitrais.rms.utils.DateFormatter.parse;

public class EmploymentHistoryDto
{
	private Integer id;
	private String startDate;
	private String endDate;
	private String employer;
	private String jobTitle;
	private Boolean activeInd;
	private Set<JobDescriptionDto> descDtos;
	private Integer employeeId = null;
	private final static
		String URL_ID_TEMPLATE = "/employees/%d/employments/%d";

	public EmploymentHistoryDto()
	{
		this.id = null;
		this.startDate = null;
		this.endDate = null;
		this.employer = null;
		this.jobTitle = null;
		this.activeInd = null;
		this.descDtos = new TreeSet<JobDescriptionDto>();
	}

	public EmploymentHistoryDto(Integer id,
			String startDate, String endDate,
			String employer, String jobTitle,
			Boolean activeInd,
			Set<JobDescription> jobDesc
		)

	{
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employer = employer;
		this.jobTitle = jobTitle;
		this.activeInd = activeInd;
		this.descDtos = jobDesc.stream()
			.map(desc -> JobDescriptionDto.fromEntity(desc))
			.collect(Collectors.toSet());
	}

	public String getId()
	{
		if (employeeId != null && id != null)
			return String.format(URL_ID_TEMPLATE,
				employeeId, id);
		return id.toString();
	}

	public EmploymentHistoryDto setId(Integer id)
	{
		this.id = id;
		return this;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public EmploymentHistoryDto setStartDate(String startDate)
	{
		this.startDate = startDate;
		return this;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public EmploymentHistoryDto setEndDate(String endDate)
	{
		this.endDate = endDate;
		return this;
	}

	public String getEmployer()
	{
		return employer;
	}

	public EmploymentHistoryDto setEmployer(String employer)
	{
		this.employer = employer;
		return this;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public EmploymentHistoryDto setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public EmploymentHistoryDto setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	public Set<JobDescriptionDto> getJobDesc()
	{
		return descDtos;
	}

	public EmploymentHistoryDto setJobDesc(Set<JobDescriptionDto> jobDesc)
	{
		this.descDtos = jobDesc;
		return this;
	}

	/**
	 *  all JobDescriptionDto are discarded
	 *  @return EmploymentHistory
	 **/
	public EmploymentHistory createEntity()
	{
		EmploymentHistory entity = new EmploymentHistory();
		entity.setStartDate(parse(startDate))
			.setEmployer(employer)
			.setJobTitle(jobTitle)
			.setActiveInd(activeInd);
		if (endDate != null)
			entity.setEndDate(parse(endDate));
		entity.getJobDesc().addAll(
			descDtos.stream()
				.map(descDto -> descDto.createEntity())
				.collect(Collectors.toList())
		);
		return entity;
	}

	public static EmploymentHistoryDto fromEntity(EmploymentHistory entity)
	{
		return new EmploymentHistoryDto(entity.getId(),
			format(entity.getStartDate()), 
			format(entity.getEndDate()), 
			entity.getEmployer(),
			entity.getJobTitle(), 
			entity.getActiveInd(),
			entity.getJobDesc()
		);
	}

	public EmploymentHistory updateEntity(EmploymentHistory entity)
	{
		if (startDate != null)
			entity.setStartDate(parse(startDate));
		if (endDate != null)
			entity.setEndDate(parse(endDate));
		if (employer != null)
			entity.setEmployer(employer);
		if (jobTitle != null)
			entity.setJobTitle(jobTitle);
		if (activeInd != null)
			entity.setActiveInd(activeInd);
		if (descDtos.size() > 0) {
			Map<Integer,JobDescription> persistedMapIdEntity = 
				entity.getJobDesc().stream().collect(
					Collectors.toMap(e -> e.getId(), e -> e)
				);

			Set<JobDescription> updatedEntities = descDtos.stream()
				.filter(dto -> dto.getOp() == JobDescriptionDto.OpType.UPDATE)
				.map(dto -> {
					JobDescription persisted = persistedMapIdEntity.get(dto.getId());
					if (persisted == null) return null;
					persisted = dto.updateEntity(persisted);
					return persisted;
				})
				.filter(e -> e != null)
				.collect(Collectors.toSet());

			// add newly create entity
			entity.getJobDesc().addAll(
			 	descDtos.stream()
					.filter(dto -> dto.getOp() == JobDescriptionDto.OpType.CREATE)
					.map(dto -> dto.createEntity())
					.collect(Collectors.toSet())
			);
		}
		return entity;
	}

	public Set<JobDescription> getDeletedEntities(EmploymentHistory entity)
	{
		Map<Integer,JobDescription> persistedMapIdEntity = 
			entity.getJobDesc().stream().collect(
				Collectors.toMap(e -> e.getId(), e -> e)
			);
		Set<JobDescription> deletedEntities = descDtos.stream()
			.filter(dto -> dto.getOp() == JobDescriptionDto.OpType.DELETE)
			.map(dto -> {
				return persistedMapIdEntity.get(dto.getId());
			})
			.filter(e -> e != null)
			.collect(Collectors.toSet());
		return deletedEntities;
	}
}