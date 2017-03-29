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
@Table(name = "t_location")
public class Location
{
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "location_seq"
	)
	@SequenceGenerator(
		name = "location_seq",
		sequenceName = "location_seq"
	)
	@Column(
		name = "location_id"
	)
	private Integer id;
	@Column(
		name = "branch_office",
		length = 45,
		nullable = false
	)
	private String branchOffice;
	@Column(
		name = "start_date",
		nullable = false
	)
	private Date startDate;
	@Column(
		name = "end_date"
	)
	private Date endDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
		name = "last_mod_date"
	)
	private Date lastModDate;
	@Column(
		name = "last_mod_user",
		length = 45
	)
	private String lastModUser;
	@Column(
		name = "active_ind"
	)
	private Boolean activeInd = false;
	@ManyToOne(
		cascade = {CascadeType.PERSIST,CascadeType.MERGE},
		fetch = FetchType.LAZY,
		optional = true
	)
	@JoinColumn(
		name = "employee_id",
		nullable = false
	)
	private Employee employee;
	@Column(
		name = "employee_id",
		nullable = false,
		insertable = false,
		updatable = false
	)
	private Integer employeeId;

	public Location()
	{

	}

	public Integer getId()
	{
		return id;
	}

	public Integer getEmployeeId()
	{
		return employeeId;
	}

	public String getBranchOffice()
	{
		return branchOffice;
	}

	public Location setBranchOffice(String branchOffice)
	{
		this.branchOffice = branchOffice;
		return this;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public Location setStartDate(Date startDate)
	{
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public Location setEndDate(Date endDate)
	{
		this.endDate = endDate;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public Location setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	public Location setEmployee(Employee employee)
	{
		this.employee = employee;
		return this;
	}

	public Employee getEmployee()
	{
		return employee;
	}
}