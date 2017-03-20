package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;

@Entity
@Table(name="const_job_family")
public class JobFamily {
    @Id
    @Column(name = "jf_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long jfId;
    @Column(name = "jobFamily")
    private String jobFamily;
    @Column(name = "min_ds")
    private int minDs;
    @Column(name = "max_ds")
    private int maxDs;

    public long getJfId() {
        return jfId;
    }

    public void setJfId(long jfId) {
        this.jfId = jfId;
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

    public JobFamily(String jobFamily, int minDs, int maxDs) {

        this.jobFamily = jobFamily;
        this.minDs = minDs;
        this.maxDs = maxDs;
    }

    public JobFamily() {

    }
}
