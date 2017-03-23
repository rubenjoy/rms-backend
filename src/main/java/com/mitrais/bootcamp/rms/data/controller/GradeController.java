package com.mitrais.bootcamp.rms.data.controller;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.Grade;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping(path = "/employees/{empId}/grades")
public class GradeController {
    EmployeeRepository employeeRepository;
    GradeRepository gradeRepository;

    @Autowired
    public GradeController(EmployeeRepository employeeRepository, GradeRepository gradeRepository) {
        this.employeeRepository = employeeRepository;
        this.gradeRepository = gradeRepository;
    }

    protected GradeController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<Grade> getGrades(@PathVariable(value = "empId") long empId) {
        Employee emp = verifyEmployee(empId);
        return emp.getGrades();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Set<Grade> update(@PathVariable(value = "empId") int empId, @RequestBody Set<Grade> newGrades) {
        Employee emp = verifyEmployee(empId);

        Set<Grade> oldGrades = new HashSet<Grade>(emp.getGrades());

        for (Grade grade: oldGrades) {
            // handle delete
            if (!newGrades.contains(grade)) {
                emp.removeGrade(grade);
                gradeRepository.delete(grade);
            } else {
                // handle update
                for (Grade newGrade : newGrades) {
                    if (newGrade.equals(grade)) {
                        grade.setGrade(newGrade.getGrade());
                        grade.setStartDate(newGrade.getStartDate());
                        grade.setEndDate(newGrade.getEndDate());
                        grade.setDs(newGrade.getDs());

                        gradeRepository.save(grade);
                    }
                }
            }
        }

        // handle add
        for (Grade newGrade: newGrades) {
            if (!oldGrades.contains(newGrade)) {
                emp.addGrade(newGrade);
            }
        }

        employeeRepository.save(emp);
        return newGrades;
    }

    private Employee verifyEmployee(long empId) throws NoSuchElementException {
        Employee employee = employeeRepository.findByEmpId(empId);
        if (employee == null) {
            throw new NoSuchElementException("Employee does not exist " + empId);
        }
        return employee;
    }
}
