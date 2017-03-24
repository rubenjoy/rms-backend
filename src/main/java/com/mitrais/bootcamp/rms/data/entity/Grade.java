package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="grades")
public class Grade {
    @Id
    @Column(name = "grade_id")
    private String gradeId;
    @Column(name = "ds", nullable = false)
    private int ds;
    @Column(name = "grade", nullable = false)
    private String grade;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name="emp_id")
    private Employee employee;

    public Grade() {

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public int getDs() {
        return ds;
    }

    public void setDs(int ds) {
        this.ds = ds;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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
