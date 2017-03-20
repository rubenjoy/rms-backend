package com.mitrais.bootcamp.rms.data.entity;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.constanta.MaritalStatus;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="employee")
public class Employee {
    @Id
    @Column(name="emp_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long empId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="gender")
    private Gender gender;
    @Column(name="dob")
    private Date dob;
    @Column(name="marital_status")
    private MaritalStatus maritalStatus;
    @Column(name="phone")
    private String phone;
    @Column(name="email")
    private String email;
    @Column(name="emp_status")
    private EmployeeStatus empStatus;
    @Column(name="suspend_date")
    private Date suspendDate;
    @Column(name="hired_date")
    private Date hiredDate;
    @Column(name="nationality")
    private String nationality;
    @Lob
    @Column(name="avatar")
    private String avatar;

    public Employee() {
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeStatus getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(EmployeeStatus empStatus) {
        this.empStatus = empStatus;
    }

    public Date getSuspendDate() {
        return suspendDate;
    }

    public void setSuspendDate(Date suspendDate) {
        this.suspendDate = suspendDate;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Employee(String firstName, String lastName, Gender gender, Date dob, MaritalStatus maritalStatus, String phone, String email, EmployeeStatus empStatus, Date suspendDate, Date hiredDate, String nationality, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.maritalStatus = maritalStatus;
        this.phone = phone;
        this.email = email;
        this.empStatus = empStatus;
        this.suspendDate = suspendDate;
        this.hiredDate = hiredDate;
        this.nationality = nationality;
        this.avatar = avatar;
    }
}
