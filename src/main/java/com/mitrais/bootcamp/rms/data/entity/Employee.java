package com.mitrais.bootcamp.rms.data.entity;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.constanta.MaritalStatus;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="employee")
public class Employee {
    @Id
    @Column(name="emp_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long empId;
    @Column(name="first_name", nullable = false)
    private String firstName;
    @Column(name="last_name", nullable = false)
    private String lastName;
    @Column(name="gender", nullable = false)
    private Gender gender;
    @Column(name="dob")
    private Date dob;
    @Column(name="marital_status")
    private MaritalStatus maritalStatus;
    @Column(name="phone", nullable = false, unique=true)
    private String phone;
    @Column(name="email", nullable = false, unique=true)
    private String email;
    @Column(name="emp_status", nullable = false)
    private EmployeeStatus empStatus;
    @Column(name="suspend_date")
    private Date suspendDate;
    @Column(name="hired_date", nullable = false)
    private Date hiredDate;
    @Column(name="nationality")
    private String nationality;
    @Lob
    @Column(name="avatar")
    private String avatar;
    @Column(name="job_family", nullable = false)
    private String jobFamily;
    @Column(name="division")
    private String division;
    @Column(name="sub_division")
    private String subDivision;

    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "post_code")
    private String postCode;

    @Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    private Set<FamilyMember> familyMembers;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    private Set<OfficeLocation> officeLocations;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    private Set<Grade> grades;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    private Set<Project> projects;

    public Employee() {
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeStatus getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(EmployeeStatus empStatus) {
        this.empStatus = empStatus;
    }

    public Date getSuspendDate() {
        return suspendDate;
    }

    public void setSuspendDate(Date suspendDate) {
        this.suspendDate = suspendDate;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(String jobFamily) {
        this.jobFamily = jobFamily;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public Set<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public Set<OfficeLocation> getOfficeLocations() {
        return officeLocations;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void addGrade(Grade grade) {
        if (this.grades == null) {
            this.grades = new HashSet<>();
        }

        this.grades.add(grade);
        if (grade.getEmployee() != this) {
            grade.setEmployee(this);
        }
    }

    public void removeGrade(Grade grade) {
        this.grades.remove(grade);
        grade.setEmployee(null);
    }

    public void addFamilyMember(FamilyMember familyMember) {
        if (this.familyMembers == null) {
            this.familyMembers = new HashSet<>();
        }

        this.familyMembers.add(familyMember);
        if (familyMember.getEmployee() != this) {
            familyMember.setEmployee(this);
        }
    }

    public void removeFamilyMember(FamilyMember familyMember) {
        this.familyMembers.remove(familyMember);
        familyMember.setEmployee(null);
    }

    public void addOfficeLocation(OfficeLocation officeLocation) {
        if (this.officeLocations == null) {
            this.officeLocations = new HashSet<>();
        }

        this.officeLocations.add(officeLocation);
        if (officeLocation.getEmployee() != this) {
            officeLocation.setEmployee(this);
        }
    }

    public void removeOfficeLocation(OfficeLocation officeLocation) {
        this.officeLocations.remove(officeLocation);
        officeLocation.setEmployee(null);
    }

    public void addProject(Project project) {
        if (this.projects == null) {
            this.projects = new HashSet<>();
        }

        this.projects.add(project);
        if (project.getEmployee() != this) {
            project.setEmployee(this);
        }
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.setEmployee(null);
    }

    public Set<Project> getProjects() {
        return projects;
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

    public Employee(String firstName, String lastName, Gender gender, String phone, String email, EmployeeStatus empStatus, String jobFamily) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.empStatus = empStatus;
        this.jobFamily = jobFamily;
    }
}
