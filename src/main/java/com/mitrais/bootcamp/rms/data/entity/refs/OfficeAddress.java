package com.mitrais.bootcamp.rms.data.entity.refs;

import javax.persistence.*;

@Entity
@Table(name="ref_office_address")
public class OfficeAddress {
    @Id
    @Column(name = "address_id")
    private String addressId;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "post_code")
    private String postCode;

    public OfficeAddress(String streetAddress, String city, String province, String postCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postCode = postCode;
    }

    public OfficeAddress() {
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
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
}
