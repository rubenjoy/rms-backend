package com.mitrais.bootcamp.rms.service;

import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
