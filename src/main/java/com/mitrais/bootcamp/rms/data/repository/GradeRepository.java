package com.mitrais.bootcamp.rms.data.repository;

import com.mitrais.bootcamp.rms.data.entity.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface GradeRepository extends CrudRepository<Grade, String> {
    Grade findByGradeId(String gradeId);
}
