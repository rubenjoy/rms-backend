package com.mitrais.bootcamp.rms.data.entity.refs;

import javax.persistence.*;

@Entity
@Table(name="ref_subdivision")
public class SubDivision {
    @Id
    @Column(name = "sub_div_code")
    private String subDivCode;
    @Column(name = "sub_division", nullable = false)
    private String subDivision;

    @ManyToOne
    @JoinColumn(name="division")
    private Division division;

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
