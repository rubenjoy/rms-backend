package com.mitrais.rms.controller.commons.dto;

import com.mitrais.rms.entity.GradeHistory;
import static com.mitrais.rms.utils.DateFormatter.format;
import static com.mitrais.rms.utils.DateFormatter.parse;

public class GradeHistoryDto
{
	private Integer id;
	private String startDate;
	private String endDate;
	private String grade;
	private Integer devStage;
	private Boolean activeInd;
	private Integer employeeId = null;
	private final static
		String URL_ID_TEMPLATE = "/employees/%d/grades/%d";

	public GradeHistoryDto()
	{
		this.id = null;
		this.startDate = null;
		this.endDate = null;
		this.grade = null;
		this.devStage = null;
		this.activeInd = null;
	}

	public GradeHistoryDto(
			Integer id,
			String startDate, String endDate,
			String grade, Integer devStage,
			Boolean activeInd
		)
	{
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.grade = grade;
		this.devStage = devStage;
		this.activeInd = activeInd;
	}

	public String getId()
	{
		if (employeeId != null && id != null)
			return String.format(URL_ID_TEMPLATE,
				employeeId, id);
		return id.toString();
	}

	public GradeHistoryDto setId(Integer id)
	{
		this.id = id;
		return this;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public GradeHistoryDto setStartDate(String startDate)
	{
		this.startDate = startDate;
		return this;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public GradeHistoryDto setEndDate(String endDate)
	{
		this.endDate = endDate;
		return this;
	}

	public String getGrade()
	{
		return grade;
	}

	public GradeHistoryDto setGrade(String grade)
	{
		this.grade = grade;
		return this;
	}

	public Integer getDevStage()
	{
		return devStage;
	}

	public GradeHistoryDto setDevStage(Integer devStage)
	{
		this.devStage = devStage;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public GradeHistoryDto setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	/**
	 *  @return GradeHistory
	 **/
	public GradeHistory createEntity()
	{
		GradeHistory entity = new GradeHistory();
		entity.setStartDate(parse(startDate))
			.setGrade(grade)
			.setDevStage(devStage)
			.setActiveInd(activeInd);
		if (endDate != null)
			entity.setEndDate(parse(endDate));
		return entity;
	}

	/**
	 *  @param grade
	 *  @return GradeHistoryDto
	 **/
	public static GradeHistoryDto fromEntity(GradeHistory entity)
	{
		GradeHistoryDto dto = new GradeHistoryDto(entity.getId(),
			format(entity.getStartDate()), format(entity.getEndDate()),
			entity.getGrade(), entity.getDevStage(),
			entity.getActiveInd()
		);
		dto.employeeId = entity.getEmployeeId();
		return dto;
	}

	/**
	 *  @param grade
	 *  @return GradeHistory
	 **/
	public GradeHistory updateEntity(GradeHistory entity)
	{
		if (startDate != null)
			entity.setStartDate(parse(startDate));
		if (endDate != null)
			entity.setEndDate(parse(endDate));
		if (grade != null)
			entity.setGrade(grade);
		if (devStage != null)
			entity.setDevStage(devStage);
		if (activeInd != null)
			entity.setActiveInd(activeInd);
		return entity;
	}
}