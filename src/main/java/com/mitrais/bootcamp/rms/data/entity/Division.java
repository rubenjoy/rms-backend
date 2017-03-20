package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;

@Entity
@Table(name="const_division")
public class Division {
    @Id
    @Column(name = "div_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long divId;
    @Column(name = "division")
    private String division;

    @ManyToOne
    private JobFamily jobFamily;

    public long getDivId() {
        return divId;
    }

    public void setDivId(long divId) {
        this.divId = divId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public JobFamily getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(JobFamily jobFamily) {
        this.jobFamily = jobFamily;
    }

    public Division(String division, JobFamily jobFamily) {

        this.division = division;
        this.jobFamily = jobFamily;
    }

    public Division() {

    }
}
