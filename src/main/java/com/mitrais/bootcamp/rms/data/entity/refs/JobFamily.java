package com.mitrais.bootcamp.rms.data.entity.refs;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="ref_job_family")
public class JobFamily {
    @Id
    @Column(name = "jf_code")
    private String jfCode;
    @Column(name = "jobFamily")
    private String jobFamily;
    @Column(name = "min_ds")
    private int minDs;
    @Column(name = "max_ds")
    private int maxDs;

    @OneToMany(mappedBy="jobFamily", cascade = CascadeType.ALL)
    private Set<Division> divisions;

    @OneToMany(mappedBy="jobFamily", cascade = CascadeType.ALL)
    private Set<JobFamilyLevel> jfLevels;

    public String getJfCode() {
        return jfCode;
    }

    public void setJfCode(String jfCode) {
        this.jfCode = jfCode;
    }

    public String getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(String jobFamily) {
        this.jobFamily = jobFamily;
    }

    public int getMinDs() {
        return minDs;
    }

    public void setMinDs(int minDs) {
        this.minDs = minDs;
    }

    public int getMaxDs() {
        return maxDs;
    }

    public void setMaxDs(int maxDs) {
        this.maxDs = maxDs;
    }

    public Set<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<Division> divisions) {
        this.divisions = divisions;
    }

    public Set<JobFamilyLevel> getJfLevels() {
        return jfLevels;
    }

    public void setJfLevels(Set<JobFamilyLevel> jfLevels) {
        this.jfLevels = jfLevels;
    }

    public JobFamily() {

    }
}
