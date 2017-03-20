package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="office_locations")
public class OfficeLocations {
    @Id
    @Column(name = "loc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long locId;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    private OfficeAddress officeLocation;

    @ManyToOne
    private Employee employee;

    public OfficeLocations() {

    }

    public long getLocId() {
        return locId;
    }

    public void setLocId(long locId) {
        this.locId = locId;
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

    public OfficeAddress getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(OfficeAddress officeLocation) {
        this.officeLocation = officeLocation;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public OfficeLocations(Date startDate, Date endDate, OfficeAddress officeLocation, Employee employee) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.officeLocation = officeLocation;
        this.employee = employee;
    }
}
