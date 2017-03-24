package com.mitrais.bootcamp.rms.data.entity.refs;

import javax.persistence.*;

@Entity
@Table(name="ref_jf_level")
public class JobFamilyLevel {
    @Id
    @Column(name = "level_id")
    private String levelId;
    @Column(name = "grade")
    private String grade;
    @Column(name = "min_ds")
    private int minDs;
    @Column(name = "max_ds")
    private int maxDs;

    @ManyToOne
    @JoinColumn(name="job_family")
    private JobFamily jobFamily;

    public JobFamily getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(JobFamily jobFamily) {
        this.jobFamily = jobFamily;
    }

    public JobFamilyLevel() {

    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

}
