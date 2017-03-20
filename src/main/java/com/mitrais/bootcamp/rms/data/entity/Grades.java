package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="grades")
public class Grades {
    @Id
    @Column(name = "grade_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long gradeId;
    @Column(name = "ds")
    private int ds;
    @Column(name = "grade")
    private String grade;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    private Employee employee;

    public Grades() {

    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Grades(int ds, String grade, Date startDate, Date endDate, Employee employee) {

        this.ds = ds;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
    }
}
