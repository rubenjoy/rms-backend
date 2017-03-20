package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;

@Entity
@Table(name="const_subdivision")
public class SubDivision {
    @Id
    @Column(name = "sub_div_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long subDivId;
    @Column(name = "sub_division")
    private String subDivision;

    @ManyToOne
    private Division division;

    public SubDivision() {
    }

    public long getSubDivId() {
        return subDivId;
    }

    public void setSubDivId(long subDivId) {
        this.subDivId = subDivId;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public SubDivision(String subDivision, Division division) {

        this.subDivision = subDivision;
        this.division = division;
    }
}
