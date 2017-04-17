package com.mitrais.bootcamp.rms;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.constanta.Relation;
import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.FamilyMember;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FamilyMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MediaType halContentType = new MediaType(MediaTypes.HAL_JSON, Charset.forName("utf8"));
    private MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private EmployeeRepository employeeRepository;

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.employeeRepository.deleteAll();
    }

    @Test
    public void addFamilyMember() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("employee");
        newEmployee.setLastName("1");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        Employee savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<FamilyMember> family = new ArrayList<>();
        FamilyMember fam1 = new FamilyMember();
        fam1.setFamId("f_"+savedEmployee.getEmpId()+"_0");
        fam1.setRelation(Relation.Husband);
        fam1.setName("employee 1 husband");
        fam1.setGender(Gender.Male);
        fam1.setActive(true);
        fam1.setDob(new Date(Calendar.getInstance().getTimeInMillis()));

        family.add(fam1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/family")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(family)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.familyMembers", hasSize(1)));
    }

    @Test
    public void addFamilyMemberToExisting() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("employee");
        newEmployee.setLastName("1");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        Employee savedEmployee = employeeRepository.save(newEmployee);

        FamilyMember fam1 = new FamilyMember();
        fam1.setFamId("f_"+savedEmployee.getEmpId()+"_0");
        fam1.setRelation(Relation.Husband);
        fam1.setName("employee 1 husband");
        fam1.setGender(Gender.Male);
        fam1.setActive(true);
        fam1.setDob(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addFamilyMember(fam1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<FamilyMember> family = new ArrayList<>();
        family.add(fam1);

        FamilyMember fam2 = new FamilyMember();
        fam2.setFamId("f_"+savedEmployee.getEmpId()+"_1");
        fam2.setRelation(Relation.Daughter);
        fam2.setName("employee 1 daughter");
        fam2.setGender(Gender.Female);
        fam2.setActive(true);
        fam2.setDob(new Date(Calendar.getInstance().getTimeInMillis()));
        family.add(fam2);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/family")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(family)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.familyMembers", hasSize(2)));
    }

    @Test
    public void setFamilyMemberEmpty() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("employee");
        newEmployee.setLastName("1");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        Employee savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<FamilyMember> family = new ArrayList<>();

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/family")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(family)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.familyMembers", hasSize(0)));
    }

    @Test
    public void setFamilyMemberEmptyFromExisting() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("employee");
        newEmployee.setLastName("1");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        Employee savedEmployee = employeeRepository.save(newEmployee);

        FamilyMember fam1 = new FamilyMember();
        fam1.setFamId("f_"+savedEmployee.getEmpId()+"_0");
        fam1.setRelation(Relation.Husband);
        fam1.setName("employee 1 husband");
        fam1.setGender(Gender.Male);
        fam1.setActive(true);
        fam1.setDob(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addFamilyMember(fam1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<FamilyMember> family = new ArrayList<>();

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/family")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(family)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.familyMembers", hasSize(0)));
    }

    @Test
    public void removeFamilyMemberFromExisting() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("employee");
        newEmployee.setLastName("1");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        Employee savedEmployee = employeeRepository.save(newEmployee);

        FamilyMember fam1 = new FamilyMember();
        fam1.setFamId("f_"+savedEmployee.getEmpId()+"_0");
        fam1.setRelation(Relation.Husband);
        fam1.setName("employee 1 husband");
        fam1.setGender(Gender.Male);
        fam1.setActive(true);
        fam1.setDob(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addFamilyMember(fam1);

        FamilyMember fam2 = new FamilyMember();
        fam2.setFamId("f_"+savedEmployee.getEmpId()+"_1");
        fam2.setRelation(Relation.Daughter);
        fam2.setName("employee 1 daughter");
        fam2.setGender(Gender.Female);
        fam2.setActive(true);
        fam2.setDob(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addFamilyMember(fam2);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<FamilyMember> family = new ArrayList<>();
        family.add(fam2);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/family")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(family)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.familyMembers", hasSize(1)));
    }

    @Test
    public void updateExistingFamilyMember() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("employee");
        newEmployee.setLastName("1");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        Employee savedEmployee = employeeRepository.save(newEmployee);

        FamilyMember fam1 = new FamilyMember();
        fam1.setFamId("f_"+savedEmployee.getEmpId()+"_0");
        fam1.setRelation(Relation.Husband);
        fam1.setName("employee 1 husband");
        fam1.setGender(Gender.Male);
        fam1.setActive(true);
        fam1.setDob(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addFamilyMember(fam1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<FamilyMember> family = new ArrayList<>();
        fam1.setActive(false);
        family.add(fam1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/family")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(family)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.familyMembers", hasSize(1)))
                .andExpect(jsonPath("$.familyMembers[0].active", is(fam1.isActive())));
    }
}
