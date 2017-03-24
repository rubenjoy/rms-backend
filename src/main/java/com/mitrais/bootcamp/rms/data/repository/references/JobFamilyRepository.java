package com.mitrais.bootcamp.rms.data.repository.references;

import com.mitrais.bootcamp.rms.data.entity.refs.JobFamily;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface JobFamilyRepository extends CrudRepository<JobFamily,String> {

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
