package com.mitrais.bootcamp.rms.data.web;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.FamilyMember;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping(path = "/employees/{empId}/family")
public class FamilyMemberController {
    EmployeeRepository employeeRepository;
    FamilyMemberRepository familyMemberRepository;

    @Autowired
    public FamilyMemberController(EmployeeRepository employeeRepository, FamilyMemberRepository familyMemberRepository) {
        this.employeeRepository = employeeRepository;
        this.familyMemberRepository = familyMemberRepository;
    }

    protected FamilyMemberController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<FamilyMember> getGrades(@PathVariable(value = "empId") long empId) {
        Employee emp = verifyEmployee(empId);
        return emp.getFamilyMembers();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Set<FamilyMember> update(@PathVariable(value = "empId") int empId, @RequestBody Set<FamilyMember> newFamily) {
        Employee emp = verifyEmployee(empId);

        Set<FamilyMember> oldFamily = new HashSet(emp.getFamilyMembers());

        for (FamilyMember existingMember: oldFamily) {
            // handle delete
            if (!newFamily.contains(existingMember)) {
                emp.removeFamilyMember(existingMember);
                familyMemberRepository.delete(existingMember);
            } else {
                // handle update
                for (FamilyMember newMember : newFamily) {
                    if (newMember.equals(existingMember)) {
                        existingMember.setActive(newMember.isActive());
                        existingMember.setDob(newMember.getDob());
                        existingMember.setGender(newMember.getGender());
                        existingMember.setName(newMember.getName());
                        existingMember.setRelation(newMember.getRelation());

                        familyMemberRepository.save(existingMember);
                    }
                }
            }
        }

        // handle add
        for (FamilyMember newMember : newFamily) {
            if (!oldFamily.contains(newMember)) {
                emp.addFamilyMember(newMember);
            }
        }

        employeeRepository.save(emp);
        return newFamily;
    }

    private Employee verifyEmployee(long empId) throws NoSuchElementException {
        Employee employee = employeeRepository.findByEmpId(empId);
        if (employee == null) {
            throw new NoSuchElementException("Employee does not exist " + empId);
        }
        return employee;
    }
}
