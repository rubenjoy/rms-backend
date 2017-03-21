package com.mitrais.bootcamp.rms.data.repository;

import com.mitrais.bootcamp.rms.data.entity.JobFamily;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface JobFamilyRepository extends CrudRepository<JobFamily,String> {
    JobFamily findByJfCode(String jfCode);

    @Override
    @RestResource(exported = false)
    <S extends JobFamily> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends JobFamily> Iterable<S> save(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void delete(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(JobFamily jobFamily);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends JobFamily> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
