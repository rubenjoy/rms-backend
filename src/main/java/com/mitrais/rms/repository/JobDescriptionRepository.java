package com.mitrais.rms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mitrais.rms.entity.JobDescription;

@RepositoryRestResource(
	path = "jobdesc",
	exported = false
)
public interface JobDescriptionRepository
	extends CrudRepository<JobDescription, Integer>
{
}