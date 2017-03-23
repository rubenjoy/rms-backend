package com.mitrais.bootcamp.rms.data.repository;

import com.mitrais.bootcamp.rms.data.entity.FamilyMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface FamilyMemberRepository extends CrudRepository<FamilyMember, String> {
}
