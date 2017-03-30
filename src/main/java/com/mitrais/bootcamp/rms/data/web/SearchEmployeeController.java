package com.mitrais.bootcamp.rms.data.web;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/employees/search")
public class SearchEmployeeController {
    EmployeeRepository employeeRepository;

    @Autowired
    public SearchEmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    protected SearchEmployeeController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployeeByName(@RequestParam("name") String empName) {

        String[] splitName = empName.split("[ ]");

        String firstName = splitName.length > 0 ? splitName[0] : empName;
        String lastName = splitName.length > 0 ? splitName[splitName.length-1] : empName;
        return employeeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(firstName, lastName);
    }
}
