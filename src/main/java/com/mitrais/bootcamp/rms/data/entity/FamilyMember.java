package com.mitrais.bootcamp.rms.data.entity;

import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.constanta.Relation;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="family_members")
public class FamilyMember {
    @Id
    @Column(name = "fam_id")
    private String famId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "dob", nullable = false)
    private Date dob;
    @Column(name = "relation", nullable = false)
    private Relation relation;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="emp_id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public FamilyMember() {

    }

    public String getFamId() {
        return famId;
    }

    public void setFamId(String famId) {
        this.famId = famId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
