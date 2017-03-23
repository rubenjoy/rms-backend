package com.mitrais.bootcamp.rms.config;

import com.mitrais.bootcamp.rms.data.entity.*;
import com.mitrais.bootcamp.rms.data.entity.references.Division;
import com.mitrais.bootcamp.rms.data.entity.references.JobFamily;
import com.mitrais.bootcamp.rms.data.entity.references.OfficeAddress;
import com.mitrais.bootcamp.rms.data.entity.references.SubDivision;
import com.mitrais.bootcamp.rms.data.entity.listener.EmployeeEntityListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
public class RepositoryConfig extends RepositoryRestMvcConfiguration {

    @Override
    public RepositoryRestConfiguration config() {
        RepositoryRestConfiguration config = super.config();
        config.exposeIdsFor(JobFamily.class);
        config.exposeIdsFor(Division.class);
        config.exposeIdsFor(SubDivision.class);
        config.exposeIdsFor(Employee.class);
        config.exposeIdsFor(Grade.class);
        config.exposeIdsFor(FamilyMember.class);
        config.exposeIdsFor(OfficeLocation.class);
        config.exposeIdsFor(OfficeAddress.class);
        return config;
    }

    @Bean
    EmployeeEntityListener employeeEntityListener() {
        return new EmployeeEntityListener();
    }
}
