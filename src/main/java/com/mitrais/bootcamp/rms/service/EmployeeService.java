package com.mitrais.bootcamp.rms.service;

import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.JobFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, JobFamilyRepository jobFamilyRepository) {
        this.employeeRepository = employeeRepository;
        this.jobFamilyRepository = jobFamilyRepository;
    }

//    public void employeeExampleData() {
//        JobFamily jfSE = jobFamilyRepository.findByJfCode("SE");
//        Employee emp = new Employee("employee", "1", Gender.Female, "+62132123", "email@email.com",
//                EmployeeStatus.Contract, jfSE.getJfCode());
//
//        employeeRepository.save(emp);
//
//    }
}
