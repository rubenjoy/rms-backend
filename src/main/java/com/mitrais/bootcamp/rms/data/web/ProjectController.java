package com.mitrais.bootcamp.rms.data.web;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.Project;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.repository.references.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping(path = "/employees/{empId}/projects")
public class ProjectController {
    EmployeeRepository employeeRepository;
    ProjectRepository projectRepository;

    @Autowired
    public ProjectController(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    protected ProjectController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<Project> getGrades(@PathVariable(value = "empId") long empId) {
        Employee emp = verifyEmployee(empId);
        return emp.getProjects();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Set<Project> update(@PathVariable(value = "empId") int empId, @RequestBody Set<Project> newProjects) {
        Employee emp = verifyEmployee(empId);

        Set<Project> oldProjects = new HashSet(emp.getProjects());

        for (Project project: oldProjects) {
            // handle delete
            if (!newProjects.contains(project)) {
                emp.removeProject(project);
                projectRepository.delete(project);
            } else {
                // handle update
                for (Project newProject : newProjects) {
                    if (project.equals(project)) {
                        project.setStartDate(newProject.getStartDate());
                        project.setEndDate(newProject.getEndDate());
                        project.setJobDesc(newProject.getJobDesc());
                        project.setRole(newProject.getRole());

                        projectRepository.save(project);
                    }
                }
            }
        }

        // handle add
        for (Project newProject: newProjects) {
            if (!oldProjects.contains(newProject)) {
                emp.addProject(newProject);
            }
        }

        employeeRepository.save(emp);
        return newProjects;
    }

    private Employee verifyEmployee(long empId) throws NoSuchElementException {
        Employee employee = employeeRepository.findByEmpId(empId);
        if (employee == null) {
            throw new NoSuchElementException("Employee does not exist " + empId);
        }
        return employee;
    }
}
