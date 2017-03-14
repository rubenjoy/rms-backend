package com.mitrais.rms.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
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

import com.mitrais.rms.entity.enumerated.DependentType;

@Entity
@Table(name = "t_dependent")
public class Dependent
{
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "depedent_seq"
	)
	@SequenceGenerator(
		name = "depedent_seq",
		sequenceName = "dependent_seq"
	)
	@Column(
		name = "dependent_id"
	)
	private Integer id;
	@Enumerated(EnumType.STRING)
	@Column(
		name = "type",
		nullable = false
	)
	private DependentType type;
	@Column(
		name = "name",
		length = 90,
		nullable = false
	)
	private String name;
	@Column(
		name = "birth_date",
		nullable = false
	)
	private Date birthDate;
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
		name = "last_mod_user",
		length = 45
	)
	private String lastModUser;
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

	public Dependent()
	{
	}

	public Integer getId()
	{
		return id;
	}

	public DependentType getType()
	{
		return type;
	}

	public Dependent setType(DependentType type)
	{
		this.type = type;
		return this;
	}

	public String getName()
	{
		return name;
	}

	public Dependent setName(String name)
	{
		this.name = name;
		return this;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public Dependent setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public Dependent setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	public Dependent setEmployee(Employee employee)
	{
		this.employee = employee;
		return this;
	}
}