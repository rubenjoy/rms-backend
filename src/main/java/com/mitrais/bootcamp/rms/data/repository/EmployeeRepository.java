package com.mitrais.bootcamp.rms.data.repository;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long>, QueryDslPredicateExecutor {
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

    @Query("SELECT e FROM Employee e where lower(concat(firstName, ' ', lastName)) like %:name%")
    Page<Employee> findByName(@Param("name") String name, Pageable pageable);
//
//    @Query("SELECT e FROM Employee e where e.firstName like %:firstName%")
//    public List<Employee> findByFirstName(@Param("firstName") String firstName);
//
//    @Query("SELECT e FROM Employee e where e.lastName like %:lastName%")
//    public List<Employee> findByLastName(@Param("lastName") String lastName);
}
