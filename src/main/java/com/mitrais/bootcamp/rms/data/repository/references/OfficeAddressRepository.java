package com.mitrais.bootcamp.rms.data.repository.references;

import com.mitrais.bootcamp.rms.data.entity.references.OfficeAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "offices", path = "offices")
public interface OfficeAddressRepository extends CrudRepository<OfficeAddress, String> {
    OfficeAddress findByAddressId(String addressId);

    @Override
    @RestResource(exported = false)
    <S extends OfficeAddress> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends OfficeAddress> Iterable<S> save(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void delete(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(OfficeAddress s);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends OfficeAddress> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
