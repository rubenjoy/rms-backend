package com.mitrais.bootcamp.rms.service;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.Grade;
import com.mitrais.bootcamp.rms.data.entity.JobFamily;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.JobFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, JobFamilyRepository jobFamilyRepository) {
        this.employeeRepository = employeeRepository;
        this.jobFamilyRepository = jobFamilyRepository;
    }

    public void employeeExampleData() {
        JobFamily jfSE = jobFamilyRepository.findByJfCode("SE");
        Employee emp = new Employee("employee", "1", Gender.Female, "+62132123", "email@email.com",
                EmployeeStatus.Contract, jfSE.getJfCode());

        Grade grade = new Grade();
        grade.setGradeId("g_1_2");
        grade.setGrade("SE-JP");
        grade.setDs(1);
        grade.setStartDate(new java.sql.Date(new Date().getTime()));

        emp.addGrade(grade);

        employeeRepository.save(emp);

        emp.removeGrade(grade);

        employeeRepository.save(emp);
    }
}
