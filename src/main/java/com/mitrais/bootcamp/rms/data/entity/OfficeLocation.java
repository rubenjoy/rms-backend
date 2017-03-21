package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="office_locations")
public class OfficeLocation {
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

    public OfficeLocation() {

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

}
