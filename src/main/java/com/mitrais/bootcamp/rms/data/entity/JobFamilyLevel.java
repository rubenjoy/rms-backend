package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;

@Entity
@Table(name="const_jf_level")
public class JobFamilyLevel {
    @Id
    @Column(name = "level_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long levelId;
    @Column(name = "grade")
    private String grade;
    @Column(name = "min_ds")
    private int minDs;
    @Column(name = "max_ds")
    private int maxDs;

    @ManyToOne
    private JobFamily jobFamily;

    public JobFamilyLevel() {

    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
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

    public JobFamily getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(JobFamily jobFamily) {
        this.jobFamily = jobFamily;
    }

    public JobFamilyLevel(String grade, int minDs, int maxDs, JobFamily jobFamily) {

        this.grade = grade;
        this.minDs = minDs;
        this.maxDs = maxDs;
        this.jobFamily = jobFamily;
    }
}
