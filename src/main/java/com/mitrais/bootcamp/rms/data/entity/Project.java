package com.mitrais.bootcamp.rms.data.entity;

import com.mitrais.bootcamp.rms.data.entity.converter.ProjectJobDescConverter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @Column(name = "project_id")
    private String projectId;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "role")
    private String role;
    @Column(name = "job_desc")
    @Convert(converter = ProjectJobDescConverter.class)
    private String[] jobDesc;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @ManyToOne
    @JoinColumn(name="emp_id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project() {

    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String[] jobDesc) {
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

}
