package com.mitrais.bootcamp.rms.data.web;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.OfficeLocation;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.OfficeLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping(path = "/employees/{empId}/locations")
public class OfficeLocationController {
    EmployeeRepository employeeRepository;
    OfficeLocationRepository officeLocationRepository;

    @Autowired
    public OfficeLocationController(EmployeeRepository employeeRepository, OfficeLocationRepository officeLocationRepository) {
        this.employeeRepository = employeeRepository;
        this.officeLocationRepository = officeLocationRepository;
    }

    protected OfficeLocationController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<OfficeLocation> getGrades(@PathVariable(value = "empId") long empId) {
        Employee emp = verifyEmployee(empId);
        return emp.getOfficeLocations();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Set<OfficeLocation> update(@PathVariable(value = "empId") int empId, @RequestBody Set<OfficeLocation> newOffices,
                                      @RequestHeader("if-match") String etag) {
        Employee emp = verifyEmployee(empId);

        if (!emp.isEmployeeVersionMatch(etag)) {
            throw new EtagNotEqualException();
        }
        Set<OfficeLocation> oldOffices = new HashSet(emp.getOfficeLocations());

        for (OfficeLocation oldOffice: oldOffices) {
            // handle delete
            if (!newOffices.contains(oldOffice)) {
                emp.removeOfficeLocation(oldOffice);
                officeLocationRepository.delete(oldOffice);
            } else {
                // handle update
                for (OfficeLocation newOffice : newOffices) {
                    if (newOffice.equals(oldOffice)) {
                        oldOffice.setStartDate(oldOffice.getStartDate());
                        oldOffice.setEndDate(oldOffice.getEndDate());
                        oldOffice.setOfficeLocation(oldOffice.getOfficeLocation());
                        officeLocationRepository.save(oldOffice);
                    }
                }
            }
        }

        // handle add
        for (OfficeLocation newOffice: newOffices) {
            if (!oldOffices.contains(newOffice)) {
                emp.addOfficeLocation(newOffice);
            }
        }

        emp.setLastModified(new Timestamp(new Date().getTime()));
        employeeRepository.save(emp);
        return newOffices;
    }

    private Employee verifyEmployee(long empId) throws NoSuchElementException {
        Employee employee = employeeRepository.findByEmpId(empId);
        if (employee == null) {
            throw new NoSuchElementException("Employee does not exist " + empId);
        }
        return employee;
    }
}
