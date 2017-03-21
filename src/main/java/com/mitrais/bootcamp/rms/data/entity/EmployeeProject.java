package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="employee_projects")
public class EmployeeProject {
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

    public EmployeeProject() {

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

}
