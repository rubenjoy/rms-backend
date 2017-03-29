package com.mitrais.rms.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_job_description")
public class JobDescription
	implements Comparable<JobDescription>
{
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "job_description_seq"
	)
	@SequenceGenerator(
		name = "job_description_seq",
		sequenceName = "job_description_seq"
	)
	@Column(
		name = "job_description_id"
	)
	private Integer id;
	@Column(
		name = "description",
		length = 255,
		nullable = false
	)
	private String description = "";
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
		name = "last_mod_date"
	)
	private Date lastModDate;
	@Column(
		name = "last_mod_user"
	)
	private String lastModUser;

	public JobDescription()
	{

	}

	@Override
	public int compareTo(JobDescription other)
	{
		if (id != null && other.id != null)
			return this.id - other.id;
		if (id != null)
			return this.id;
		if (description != null && other.description != null)
			return description.compareTo(other.description);
		return 0;
	}

	public String getDescription()
	{
		return description;
	}

	public JobDescription setDescription(
		String description)
	{
		this.description = description;
		return this;
	}

	public Integer getId()
	{
		return id;
	}
}