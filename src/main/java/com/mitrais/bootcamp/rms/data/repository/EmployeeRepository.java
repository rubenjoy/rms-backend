package com.mitrais.bootcamp.rms.data.repository;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    Employee findByEmpId(Long empId);

    @Override
    @RestResource(exported = false)
    <S extends Employee> Iterable<S> save(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends Employee> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
