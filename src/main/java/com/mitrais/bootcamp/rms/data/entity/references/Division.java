package com.mitrais.bootcamp.rms.data.entity.references;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="const_division")
public class Division {
    @Id
    @Column(name = "div_code")
    private String divCode;
    @Column(name = "division")
    private String division;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<SubDivision> subDivisions;

    public String getDivCode() {
        return divCode;
    }

    public void setDivCode(String divCode) {
        this.divCode = divCode;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Set<SubDivision> getSubDivisions() {
        return subDivisions;
    }

    public void setSubDivisions(Set<SubDivision> subDivisions) {
        this.subDivisions = subDivisions;
    }

    public Division() {

    }

}
