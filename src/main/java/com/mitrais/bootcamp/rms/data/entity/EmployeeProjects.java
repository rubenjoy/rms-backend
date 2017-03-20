package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="employee_projects")
public class EmployeeProjects {
    @Id
    @Column(name = "emp_proj_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long empProjId;
    @Column(name = "role")
    private String role;
    @Column(name = "job_desc")
    private String jobDesc;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Projects projects;

    public EmployeeProjects() {

    }

    public long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(long empProjId) {
        this.empProjId = empProjId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public EmployeeProjects(String role, String jobDesc, Date startDate, Date endDate, Employee employee, Projects projects) {

        this.role = role;
        this.jobDesc = jobDesc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.projects = projects;
    }
}
