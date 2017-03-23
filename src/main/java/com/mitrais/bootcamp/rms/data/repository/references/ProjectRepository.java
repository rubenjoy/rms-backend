package com.mitrais.bootcamp.rms.data.repository.references;

import com.mitrais.bootcamp.rms.data.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ProjectRepository extends CrudRepository<Project, String> {
}
