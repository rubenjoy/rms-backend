package com.mitrais.rms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mitrais.rms.entity.enumerated.Gender;
import com.mitrais.rms.entity.enumerated.MaritalStatus;
import com.mitrais.rms.entity.enumerated.Nationality;

/**
 *  Employee entity is mapped into 
 **/
@Entity
@Table(name = "t_employee")
public class Employee
{
	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "employee_seq"
	)
	@SequenceGenerator(
		name = "employee_seq",
		sequenceName = "employee_seq"
	)
	@Column(
		name = "employee_id",
		nullable = false
	)
	private Integer id;
	@Column(
		name = "name",
		length = 255,
		nullable = false
	)
	private String name;
	@Column(
		name = "birth_date",
		nullable = false
	)
	private Date birthDate;
	@Enumerated(EnumType.STRING)
	@Column(
		name = "gender",
		nullable = false
	)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	@Column(
		name = "marital_status",
		nullable = false
	)
	private MaritalStatus maritalStatus;
	@Column(
		name = "phone",
		length = 45,
		nullable = false
	)
	private String phone;
	@Column(
		name = "email",
		length = 45,
		nullable = false
	)
	private String email;
	@Column(
		name = "hire_date"
	)
	private Date hireDate;
	@Column(
		name = "employment_status",
		length = 45
	)
	private String employmentStatus;
	@Enumerated(EnumType.STRING)
	@Column(
		name = "nationality",
		nullable = false 
	)
	private Nationality nationality;
	@Column(
		name = "business_unit",
		length = 45
	)
	private String businessUnit;
	@Column(
		name = "division",
		length = 45
	)
	private String division;
	@Column(
		name = "job_family",
		length = 45,
		nullable = false
	)
	private String jobFamily;
	@Column(
		name = "job_title",
		length = 45,
		nullable = false
	)
	private String jobTitle;
	@Column(
		name = "stream",
		length = 45
	)
	private String stream;
	@Column(
		name = "reporting_manager"
	)
	private Integer reportingManager;
	@Column(
		name = "retire_date"
	)
	private Date retireDate;
	@Column(
		name = "suspend_date"
	)
	private Date suspendDate;
	@Column(
		name = "active_ind"
	)
	private Boolean activeInd = false;
	@Column(
		name = "last_degree",
		length = 45
	)
	private String lastDegree;
	@Column(
		name = "recruit_from",
		length = 255
	)
	private String recruitFrom;
	@Column(
		name = "contract_expiry_date"
	)
	private Date contractExpiryDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
		name = "last_mod_date"
	)
	private Date lastModDate;
	@Column(
		name = "last_mod_user"
	)
	private String lastModUser;

	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL,
		mappedBy = "employee"
	)
	private List<Address> addresses;
	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL,
		mappedBy = "employee"
	)
	private List<Dependent> dependents;
	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL,
		mappedBy = "employee"
	)
	private List<EmploymentHistory> employments;
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "employee"
	)
	private List<GradeHistory> grades;
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "employee"
	)
	private List<Location> locations;

	public Employee()
	{
		addresses = new ArrayList<Address>();
		dependents = new ArrayList<Dependent>();
		employments = new ArrayList<EmploymentHistory>();
		grades = new ArrayList<GradeHistory>();
		locations = new ArrayList<Location>();
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Employee setName(String name) {
		this.name = name;
		return this;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public Employee setBirthDate(Date dob) {
		this.birthDate = dob;
		return this;
	}

	public Gender getGender() {
		return this.gender;
	}

	public Employee setGender(Gender gender) {
		this.gender = gender;
		return this;
	}

	public MaritalStatus getMaritalStatus() {
		return this.maritalStatus;
	}

	public Employee setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
		return this;
	}

	public String getPhone() {
		return this.phone;
	}

	public Employee setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getEmail() {
		return this.email;
	}

	public Employee setEmail(String email) {
		this.email = email;
		return this;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public Employee setHireDate(Date hireDate) {
		this.hireDate = hireDate;
		return this;
	}

	public Boolean getActiveInd()
	{
		return activeInd;
	}

	public Employee setActiveInd(Boolean activeInd)
	{
		this.activeInd = activeInd;
		return this;
	}

	public String getEmploymentStatus()
	{
		return employmentStatus;
	}

	public Employee setEmploymentStatus(String status)
	{
		this.employmentStatus = status;
		return this;
	}

	public Date getSuspendDate()
	{
		return suspendDate;
	}

	public Employee setSuspendDate(Date date)
	{
		this.suspendDate = date;
		return this;
	}

	public String getJobFamily()
	{
		return jobFamily;
	}

	public Employee setJobFamily(String jobFamily)
	{
		this.jobFamily = jobFamily;
		return this;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public Employee setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
		return this;
	}

	public String getDivision()
	{
		return division;
	}

	public Employee setDivision(String division)
	{
		this.division = division;
		return this;
	}

	public String getBusinessUnit()
	{
		return businessUnit;
	}

	public Employee setBusinessUnit(String businessUnit)
	{
		this.businessUnit = businessUnit;
		return this;
	}

	public Nationality getNationality()
	{
		return nationality;
	}

	public Employee setNationality(Nationality nationality)
	{
		this.nationality = nationality;
		return this;
	}

	public List<Address> getAddresses()
	{
		return addresses;
	}

	public List<Location> getLocations()
	{
		return locations;
	}

	public List<Dependent> getDependents()
	{
		return dependents;
	}

	public List<GradeHistory> getGrades()
	{
		return grades;
	}

	public List<EmploymentHistory> getEmployments()
	{
		return employments;
	}
}