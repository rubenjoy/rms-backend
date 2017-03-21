package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @Column(name = "proj_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projId;
    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    public Project() {

    }

    public long getProjId() {
        return projId;
    }

    public void setProjId(long projId) {
        this.projId = projId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Project(String name, Date startDate, Date endDate) {

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
