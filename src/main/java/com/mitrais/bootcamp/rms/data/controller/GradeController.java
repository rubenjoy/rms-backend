package com.mitrais.bootcamp.rms.data.controller;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.Grade;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addGrade(@PathVariable(value = "empId") long empId, @RequestBody Grade grade) {
        Employee emp = verifyEmployee(empId);

        emp.addGrade(grade);

        employeeRepository.save(emp);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<Grade> getGrades(@PathVariable(value = "empId") long empId) {
        Employee emp = verifyEmployee(empId);
        return emp.getGrades();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{gradeId}")
    public void delete(@PathVariable(value = "empId") long empId, @PathVariable(value = "gradeId") String gradeId) {
        Employee emp = verifyEmployee(empId);
        Grade grade = verifyGrade(gradeId);

        emp.removeGrade(grade);
        gradeRepository.delete(grade);
        employeeRepository.save(emp);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Grade update(@PathVariable(value = "empId") int empId, @RequestBody Grade newGrade) {
        Employee emp = verifyEmployee(empId);
        Grade grade = verifyGrade(newGrade.getGradeId());
        grade.setGrade(newGrade.getGrade());
        grade.setStartDate(newGrade.getStartDate());
        grade.setEndDate(newGrade.getEndDate());
        grade.setDs(newGrade.getDs());

        return gradeRepository.save(grade);
    }

    private Employee verifyEmployee(long empId) throws NoSuchElementException {
        Employee employee = employeeRepository.findByEmpId(empId);
        if (employee == null) {
            throw new NoSuchElementException("Employee does not exist " + empId);
        }
        return employee;
    }

    private Grade verifyGrade(String gradeId) throws NoSuchElementException {
        Grade grade = gradeRepository.findByGradeId(gradeId);
        if (grade == null) {
            throw new NoSuchElementException("Grade does not exist " + gradeId);
        }
        return grade;
    }
}
