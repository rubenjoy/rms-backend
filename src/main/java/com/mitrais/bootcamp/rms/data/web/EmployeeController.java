package com.mitrais.bootcamp.rms.data.web;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    protected EmployeeController() {
    }

    @RequestMapping(value = "employees/filter", method = RequestMethod.POST)
    HttpEntity<PagedResources<Employee>> getEmployeeByFilter(@RequestBody FilterDto filter, Pageable pageable,
                                                             PagedResourcesAssembler pagedAssembler) {

        BooleanBuilder empFilter = new FilterExpression(filter).getExpression();
        Page<Employee> filterResult = employeeRepository.findAll(empFilter, pageable);

        return new ResponseEntity<>(pagedAssembler.toResource(filterResult), HttpStatus.OK);
    }
}
