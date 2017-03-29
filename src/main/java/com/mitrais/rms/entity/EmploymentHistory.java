package com.mitrais.rms.entity;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "t_employment_history")
public class EmploymentHistory
{
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "employment_seq"
	)
	@SequenceGenerator(
		name = "employment_seq",
		sequenceName = "employment_seq"
	)
	@Column(
		name = "employment_id"
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
		name = "employer",
		length = 45,
		nullable = false
	)
	private String employer;
	@Column(
		name = "job_title",
		length = 45,
		nullable = false
	)
	private String jobTitle;
	@Column(
		name = "active_ind"
	)
	private Boolean activeInd = false;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
		name = "last_mod_date"
	)
	private Date lastModDate;
	@Column(
		name = "last_mod_user"
	)
	private String lastModUser;
	@ManyToOne(
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		fetch = FetchType.LAZY,
		optional = true
	)
	@JoinColumn(
		name = "employee_id",
		nullable = false
	)
	private Employee employee;
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "employment_id",
		nullable = false
	)
	private Set<JobDescription> jobDesc;
	@Column(
		name = "employee_id",
		nullable = false,
		insertable = false,
		updatable = false
	)
	private Integer employeeId;

	public EmploymentHistory()
	{
		jobDesc = new TreeSet<JobDescription>();
	}

	public Integer getId()
	{
		return id;
	}

	public Integer getEmployeeId()
	{
		return employeeId;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public EmploymentHistory setStartDate(Date startDate)
	{
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public EmploymentHistory setEndDate(Date endDate)
	{
		this.endDate = endDate;
		return this;
	}

	public String getEmployer()
	{
		return employer;
	}

	public EmploymentHistory setEmployer(String employer)
	{
		this.employer = employer;
		return this;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public EmploymentHistory setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public EmploymentHistory setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	public EmploymentHistory setEmployee(Employee employee)
	{
		this.employee = employee;
		return this;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public Set<JobDescription> getJobDesc()
	{
		return jobDesc;
	}
}