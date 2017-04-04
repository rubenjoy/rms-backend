package com.mitrais.bootcamp.rms.data.web;

import com.google.common.collect.Lists;
import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class EmployeeController {
    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    protected EmployeeController() {
    }

    @RequestMapping(value = "employees/search", method = RequestMethod.GET)
    ResponseEntity<Resources<Resource>> getEmployeeByName(@RequestParam("name") String empName) {

        String[] splitName = empName.split("[ ]");

        String firstName = splitName.length > 0 ? splitName[0] : empName;
        String lastName = splitName.length > 0 ? splitName[splitName.length-1] : empName;
        List<Employee> resultList = employeeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(firstName, lastName);

        List<Resource> empResources = new ArrayList<>();
        for (Employee emp :  resultList) {
            Link empLink = linkTo(EmployeeController.class).slash("/employees").slash(emp.getEmpId()).withSelfRel();
            Resource empResource = new Resource<Employee>(emp, empLink.expand(emp.getEmpId()));

            empResources.add(empResource);
        }

        Link empsLink = linkTo(EmployeeController.class).slash("/employees").withSelfRel();
        Resources<Resource> resourceList = new Resources<Resource>(empResources, empsLink);

        return new ResponseEntity<Resources<Resource>>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(value = "employees/filter", method = RequestMethod.POST)
    ResponseEntity<Resources<Resource>> getEmployeeByFilter(@RequestBody FilterDTO filter) {

        BooleanBuilder empFilter = new FilterExpression(filter).getExpression();
        List<Employee> resultList = Lists.newArrayList(employeeRepository.findAll(empFilter));

        Resources<Resource> resourceList = constructResponseWithLink(resultList);

        return new ResponseEntity<Resources<Resource>>(resourceList, HttpStatus.OK);
    }

    protected Resources<Resource> constructResponseWithLink(List<Employee> resultList) {
        List<Resource> empResources = new ArrayList<>();
        for (Employee emp :  resultList) {
            Link empLink = linkTo(EmployeeController.class).slash("/employees").slash(emp.getEmpId()).withSelfRel();
            Resource empResource = new Resource<Employee>(emp, empLink.expand(emp.getEmpId()));

            empResources.add(empResource);
        }

        Link empsLink = linkTo(EmployeeController.class).slash("/employees").withSelfRel();
        return new Resources<Resource>(empResources, empsLink);
    }
}
