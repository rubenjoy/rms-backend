package com.mitrais.bootcamp.rms.service;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.references.OfficeAddress;
import com.mitrais.bootcamp.rms.data.entity.OfficeLocation;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.references.JobFamilyRepository;
import com.mitrais.bootcamp.rms.data.repository.references.OfficeAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private OfficeAddressRepository officeAddressRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, JobFamilyRepository jobFamilyRepository, OfficeAddressRepository officeAddressRepository) {
        this.employeeRepository = employeeRepository;
        this.officeAddressRepository = officeAddressRepository;
    }

    public void employeeExampleData() {
        Employee emp = new Employee("employee", "1", Gender.Female, "+62132123", "email@email.com",
                EmployeeStatus.Contract, "SE");

        employeeRepository.save(emp);

        OfficeAddress bdgOffice = officeAddressRepository.findByAddressId("BDG");

        OfficeLocation officeLocation = new OfficeLocation();
        officeLocation.setOfficeLocation(bdgOffice.getAddressId());
        officeLocation.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
        officeLocation.setLocId("add-1");

        emp.addOfficeLocation(officeLocation);

        employeeRepository.save(emp);
    }
}
