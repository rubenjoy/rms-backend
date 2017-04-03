package com.mitrais.bootcamp.rms.data.repository;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

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

    List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String firstName,
                                                                         String lastName);

//    @Query("SELECT e FROM Employee e where e.firstName = :firstName AND e.lastName = :lastName")
//    public List<Employee> findByFullName(@Param("firstName") String firstName,
//                                         @Param("lastName") String lastName);
//
//    @Query("SELECT e FROM Employee e where e.firstName like %:firstName%")
//    public List<Employee> findByFirstName(@Param("firstName") String firstName);
//
//    @Query("SELECT e FROM Employee e where e.lastName like %:lastName%")
//    public List<Employee> findByLastName(@Param("lastName") String lastName);
}
