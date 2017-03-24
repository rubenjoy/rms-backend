package com.mitrais.bootcamp.rms.data.entity.refs;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="ref_division")
public class Division {
    @Id
    @Column(name = "div_code")
    private String divCode;
    @Column(name = "division", nullable = false)
    private String division;

    @OneToMany(mappedBy="division", cascade = CascadeType.ALL)
    private Set<SubDivision> subDivisions;

    @ManyToOne
    @JoinColumn(name="job_family")
    private JobFamily jobFamily;

    public JobFamily getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(JobFamily jobFamily) {
        this.jobFamily = jobFamily;
    }

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
