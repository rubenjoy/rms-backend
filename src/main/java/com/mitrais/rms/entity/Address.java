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
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "t_address")
public class Address
	implements Comparable<Address>
{
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "address_seq"
	)
	@SequenceGenerator(
		name = "address_seq",
		sequenceName = "address_seq"
	)
	@Column(
		name = "address_id"
	)
	private Integer id;
	@Column(
		name = "address",
		length = 255,
		nullable = false
	)
	private String address;
	@Column(
		name = "active_ind"
	)
	private Boolean activeInd = false;
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

	public Address()
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

	public String getAddress()
	{
		return address;
	}

	public Address setAddress(String address)
	{
		this.address = address;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public Address setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	public Address setEmployee(Employee employee)
	{
		this.employee = employee;
		return this;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public int compareTo(Address obj)
	{
		if (obj.id != null && id != null)
			return id - obj.id;
		if (id != null)
			return id;
		if (address != null && obj.address != null)
			return address.compareTo(obj.address);
		return 0;
	}
}