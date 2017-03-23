package com.mitrais.bootcamp.rms.data.entity.references;

import javax.persistence.*;

@Entity
@Table(name="const_subdivision")
public class SubDivision {
    @Id
    @Column(name = "sub_div_code")
    private String subDivCode;
    @Column(name = "sub_division")
    private String subDivision;

    public SubDivision() {
    }

    public String getSubDivCode() {
        return subDivCode;
    }

    public void setSubDivCode(String subDivCode) {
        this.subDivCode = subDivCode;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }
}
