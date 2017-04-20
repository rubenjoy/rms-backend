package com.mitrais.bootcamp.rms;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.OfficeLocation;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
public class OfficeLocationControllerTest {

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
    public void addOfficeLocation() throws Exception {

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

        ArrayList<OfficeLocation> locations = new ArrayList<>();
        OfficeLocation loc1 = new OfficeLocation();
        loc1.setLocId("l_"+savedEmployee.getEmpId()+"_0");
        loc1.setOfficeLocation("Bandung");
        loc1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
        locations.add(loc1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/locations")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(locations)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.officeLocations", hasSize(1)));
    }

    @Test
    public void addOfficeLocationToExisting() throws Exception {

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

        OfficeLocation loc1 = new OfficeLocation();
        loc1.setLocId("l_"+savedEmployee.getEmpId()+"_0");
        loc1.setOfficeLocation("Bandung");
        loc1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addOfficeLocation(loc1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<OfficeLocation> locations = new ArrayList<>();
        locations.add(loc1);

        OfficeLocation loc2 = new OfficeLocation();
        loc2.setLocId("l_"+savedEmployee.getEmpId()+"_1");
        loc2.setOfficeLocation("Yogyakarta");
        loc2.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        locations.add(loc2);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/locations")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(locations)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.officeLocations", hasSize(2)));
    }

    @Test
    public void setOfficeLocationEmpty() throws Exception {

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

        ArrayList<OfficeLocation> locations = new ArrayList<>();

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/locations")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(locations)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.officeLocations", hasSize(0)));
    }

    @Test
    public void setOfficeLocationEmptyFromExisting() throws Exception {

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

        OfficeLocation loc1 = new OfficeLocation();
        loc1.setLocId("l_"+savedEmployee.getEmpId()+"_0");
        loc1.setOfficeLocation("Bandung");
        loc1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addOfficeLocation(loc1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<OfficeLocation> locations = new ArrayList<>();

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/locations")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(locations)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.officeLocations", hasSize(0)));
    }

    @Test
    public void removeOfficeLocationFromExisting() throws Exception {

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

        OfficeLocation loc1 = new OfficeLocation();
        loc1.setLocId("l_"+savedEmployee.getEmpId()+"_0");
        loc1.setOfficeLocation("Bandung");
        loc1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addOfficeLocation(loc1);

        OfficeLocation loc2 = new OfficeLocation();
        loc2.setLocId("l_"+savedEmployee.getEmpId()+"_1");
        loc2.setOfficeLocation("Yogyakarta");
        loc2.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addOfficeLocation(loc2);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<OfficeLocation> locations = new ArrayList<>();
        locations.add(loc2);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/locations")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(locations)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.officeLocations", hasSize(1)));
    }

    @Test
    public void updateExistingOfficeLocation() throws Exception {

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

        OfficeLocation loc1 = new OfficeLocation();
        loc1.setLocId("l_"+savedEmployee.getEmpId()+"_0");
        loc1.setOfficeLocation("Bandung");
        loc1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addOfficeLocation(loc1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<OfficeLocation> locations = new ArrayList<>();
        loc1.setEndDate(new Date(Calendar.getInstance().getTimeInMillis()));
        locations.add(loc1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/locations")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(locations)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.officeLocations", hasSize(1)))
                .andExpect(jsonPath("$.officeLocations[0].endDate", equalToIgnoringCase(loc1.getEndDate().toString())));
    }
}
