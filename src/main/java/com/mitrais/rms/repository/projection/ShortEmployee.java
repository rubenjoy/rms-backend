package com.mitrais.rms.repository.projection;

import org.springframework.data.rest.core.config.Projection;

import com.mitrais.rms.entity.Employee;

@Projection(name = "short", types = Employee.class)
public interface ShortEmployee
{
	String getName();
	String getPhone();
	String getJobTitle();
	String getJobFamily();
	String getDivision();
}