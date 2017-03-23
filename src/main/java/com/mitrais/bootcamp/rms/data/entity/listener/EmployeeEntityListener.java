package com.mitrais.bootcamp.rms.data.entity.listener;

import com.mitrais.bootcamp.rms.data.entity.Employee;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.sql.Timestamp;
import java.util.Date;

@RepositoryEventHandler(Employee.class)
public class EmployeeEntityListener {

    @HandleBeforeSave
    public void handleBeforeSave(Employee employee) {
        employee.setLastModified(new Timestamp(new Date().getTime()));
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(Employee employee) {
        Date currentTime = new Date();
        employee.setDateAdded(new Timestamp(currentTime.getTime()));
        employee.setLastModified(new Timestamp(currentTime.getTime()));
    }
}
