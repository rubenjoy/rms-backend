package com.mitrais.rms.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "t_grade_history")
public class GradeHistory
{
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "grade_seq"
	)
	@SequenceGenerator(
		name = "grade_seq",
		sequenceName = "grade_seq"
	)
	@Column(
		name = "grade_id"
	)
	private Integer id;
	@Column(
		name = "start_date",
		nullable = false
	)
	private Date startDate;
	@Column(
		name = "end_date",
		nullable = false
	)
	private Date endDate;
	@Column(
		name = "grade",
		length = 45,
		nullable = false
	)
	private String grade;
	@Column(
		name = "dev_stage",
		nullable = false
	)
	private Integer devStage;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
		name = "last_mod_date"
	)
	private Date lastModDate;
	@Column(
		name = "last_mod_user"
	)
	private String lastModuser;
	@Column(
		name = "active_ind"
	)
	private Boolean activeInd = false;
	@ManyToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = true
	)
	@JoinColumn(
		name = "employee_id",
		nullable = false
	)
	private Employee employee;

	public GradeHistory()
	{

	}

	public Integer getId()
	{
		return id;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public GradeHistory setStartDate(Date startDate)
	{
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public GradeHistory setEndDate(Date endDate)
	{
		this.endDate = endDate;
		return this;
	}

	public String getGrade()
	{
		return grade;
	}

	public GradeHistory setGrade(String grade)
	{
		this.grade = grade;
		return this;
	}

	public Integer getDevStage()
	{
		return devStage;
	}

	public GradeHistory setDevStage(Integer devStage)
	{
		this.devStage = devStage;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public GradeHistory setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	public GradeHistory setEmployee(Employee employee)
	{
		this.employee = employee;
		return this;
	}
}