package com.mitrais.rms.repository;

import java.util.List;

import com.mitrais.rms.entity.GradeHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
	collectionResourceRel = "grades",
	path = "grades",
	exported = false
)
public interface GradeRepository
	extends CrudRepository<GradeHistory, Integer>
{
	public GradeHistory findByIdAndEmployeeId(Integer id, Integer employeeId);
	public List<GradeHistory> findByEmployeeId(Integer employeeId);
}