package com.mitrais.bootcamp.rms.data.entity;

import javax.persistence.*;

@Entity
@Table(name="employee_address")
public class EmployeeAddress {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "post_code")
    private String postCode;

    @ManyToOne
    private Employee employee;

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeAddress(String streetAddress, String city, String province, String postCode, Employee employee) {

        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postCode = postCode;
        this.employee = employee;
    }

    public EmployeeAddress() {

    }
}
