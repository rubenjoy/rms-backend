package com.mitrais.bootcamp.rms;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.constanta.MaritalStatus;
import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
import com.mitrais.bootcamp.rms.data.web.FilterDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

import static java.lang.Math.toIntExact;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RmsApplication.class)
@WebAppConfiguration
public class FilterTest {

    private MockMvc mockMvc;

    private MediaType halContentType = new MediaType(MediaTypes.HAL_JSON, Charset.forName("utf8"));
    private MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.employeeRepository.deleteAll();
    }

    @Test
    public void filterEmpty() throws Exception {
        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(new FilterDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(0)));
    }

    @Test
    public void emptyFilter() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Cal");
        newEmployee.setLastName("Supreme");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        newEmployee = employeeRepository.save(newEmployee);

        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(new FilterDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(1)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", notNullValue()))
                .andExpect(jsonPath("$._embedded.employees[0].firstName", equalToIgnoringCase(newEmployee.getFirstName())))
                .andExpect(jsonPath("$._embedded.employees[0].lastName", equalToIgnoringCase(newEmployee.getLastName())))
                .andExpect(jsonPath("$._embedded.employees[0].phone", equalToIgnoringCase(newEmployee.getPhone())))
                .andExpect(jsonPath("$._embedded.employees[0].gender", equalToIgnoringCase(newEmployee.getGender().name())))
                .andExpect(jsonPath("$._embedded.employees[0].empStatus", equalToIgnoringCase(newEmployee.getEmpStatus().name())))
                .andExpect(jsonPath("$._embedded.employees[0].jobFamily", equalToIgnoringCase(newEmployee.getJobFamily())))
                .andExpect(jsonPath("$._embedded.employees[0].hiredDate", equalToIgnoringCase(newEmployee.getHiredDate().toString())));
    }

    @Test
    public void filterByGender() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Cal");
        newEmployee.setLastName("Supreme");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        newEmployee = employeeRepository.save(newEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Cal");
        secondEmployee.setLastName("Superman");
        secondEmployee.setPhone("+622");
        secondEmployee.setEmail("employee.2@mitrais.com");
        secondEmployee.setGender(Gender.Male);
        secondEmployee.setEmpStatus(EmployeeStatus.Permanent);
        secondEmployee.setJobFamily("SE");
        secondEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        secondEmployee = employeeRepository.save(secondEmployee);

        Employee thirdEmployee = new Employee();
        thirdEmployee.setFirstName("Employee");
        thirdEmployee.setLastName("3");
        thirdEmployee.setPhone("+624");
        thirdEmployee.setEmail("employee.4@mitrais.com");
        thirdEmployee.setGender(Gender.Female);
        thirdEmployee.setEmpStatus(EmployeeStatus.Permanent);
        thirdEmployee.setJobFamily("SE");
        thirdEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        thirdEmployee = employeeRepository.save(thirdEmployee);

        FilterDto filter = new FilterDto();
        filter.setGender(Gender.Male);

        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(2)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(newEmployee.getEmpId()))))
                .andExpect(jsonPath("$._embedded.employees[1].empId", comparesEqualTo(toIntExact(secondEmployee.getEmpId()))));

        filter.setGender(Gender.Female);
        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(1)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(thirdEmployee.getEmpId()))));
    }

    @Test
    public void filterByActiveEmployee() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Cal");
        newEmployee.setLastName("Supreme");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        newEmployee = employeeRepository.save(newEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Cal");
        secondEmployee.setLastName("Superman");
        secondEmployee.setPhone("+622");
        secondEmployee.setEmail("employee.2@mitrais.com");
        secondEmployee.setGender(Gender.Male);
        secondEmployee.setEmpStatus(EmployeeStatus.Permanent);
        secondEmployee.setJobFamily("SE");
        secondEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        secondEmployee = employeeRepository.save(secondEmployee);

        secondEmployee.setSuspendDate(new Date(Calendar.getInstance().getTimeInMillis()));

        secondEmployee = employeeRepository.save(secondEmployee);

        Employee thirdEmployee = new Employee();
        thirdEmployee.setFirstName("Employee");
        thirdEmployee.setLastName("3");
        thirdEmployee.setPhone("+624");
        thirdEmployee.setEmail("employee.4@mitrais.com");
        thirdEmployee.setGender(Gender.Female);
        thirdEmployee.setEmpStatus(EmployeeStatus.Permanent);
        thirdEmployee.setJobFamily("SE");
        thirdEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        thirdEmployee = employeeRepository.save(thirdEmployee);

        FilterDto filter = new FilterDto();
        filter.setIsActive("true");

        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(2)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(newEmployee.getEmpId()))))
                .andExpect(jsonPath("$._embedded.employees[1].empId", comparesEqualTo(toIntExact(thirdEmployee.getEmpId()))));

        filter.setIsActive("false");
        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(1)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(secondEmployee.getEmpId()))));
    }

    @Test
    public void filterByEmployeeContract() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Cal");
        newEmployee.setLastName("Supreme");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        newEmployee = employeeRepository.save(newEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Cal");
        secondEmployee.setLastName("Superman");
        secondEmployee.setPhone("+622");
        secondEmployee.setEmail("employee.2@mitrais.com");
        secondEmployee.setGender(Gender.Male);
        secondEmployee.setEmpStatus(EmployeeStatus.Permanent);
        secondEmployee.setJobFamily("SE");
        secondEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        secondEmployee = employeeRepository.save(secondEmployee);

        Employee thirdEmployee = new Employee();
        thirdEmployee.setFirstName("Employee");
        thirdEmployee.setLastName("3");
        thirdEmployee.setPhone("+624");
        thirdEmployee.setEmail("employee.4@mitrais.com");
        thirdEmployee.setGender(Gender.Female);
        thirdEmployee.setEmpStatus(EmployeeStatus.Permanent);
        thirdEmployee.setJobFamily("SE");
        thirdEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        thirdEmployee = employeeRepository.save(thirdEmployee);

        FilterDto filter = new FilterDto();
        filter.setEmpStatus(EmployeeStatus.Permanent);

        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(2)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(secondEmployee.getEmpId()))))
                .andExpect(jsonPath("$._embedded.employees[1].empId", comparesEqualTo(toIntExact(thirdEmployee.getEmpId()))));

        filter.setEmpStatus(EmployeeStatus.Contract);
        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(1)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(newEmployee.getEmpId()))));
    }

    @Test
    public void filterByMaritalStatus() throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Cal");
        newEmployee.setLastName("Supreme");
        newEmployee.setPhone("+621");
        newEmployee.setEmail("employee.1@mitrais.com");
        newEmployee.setGender(Gender.Male);
        newEmployee.setEmpStatus(EmployeeStatus.Contract);
        newEmployee.setJobFamily("SE");
        newEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        newEmployee.setMaritalStatus(MaritalStatus.Married);

        newEmployee = employeeRepository.save(newEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Cal");
        secondEmployee.setLastName("Superman");
        secondEmployee.setPhone("+622");
        secondEmployee.setEmail("employee.2@mitrais.com");
        secondEmployee.setGender(Gender.Male);
        secondEmployee.setEmpStatus(EmployeeStatus.Permanent);
        secondEmployee.setJobFamily("SE");
        secondEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        secondEmployee.setMaritalStatus(MaritalStatus.Married);

        secondEmployee = employeeRepository.save(secondEmployee);

        Employee thirdEmployee = new Employee();
        thirdEmployee.setFirstName("Employee");
        thirdEmployee.setLastName("3");
        thirdEmployee.setPhone("+624");
        thirdEmployee.setEmail("employee.4@mitrais.com");
        thirdEmployee.setGender(Gender.Female);
        thirdEmployee.setEmpStatus(EmployeeStatus.Permanent);
        thirdEmployee.setJobFamily("SE");
        thirdEmployee.setHiredDate(new Date(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setDateAdded(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        thirdEmployee.setMaritalStatus(MaritalStatus.Single);

        thirdEmployee = employeeRepository.save(thirdEmployee);

        FilterDto filter = new FilterDto();
        filter.setMaritalStatus(MaritalStatus.Married);

        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(2)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(newEmployee.getEmpId()))))
                .andExpect(jsonPath("$._embedded.employees[1].empId", comparesEqualTo(toIntExact(secondEmployee.getEmpId()))));

        filter.setMaritalStatus(MaritalStatus.Single);
        mockMvc.perform(post("/employees/filter?sort=dateAdded,desc")
                .contentType(jsonContentType)
                .content(this.json(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$._embedded.employees", hasSize(1)))
                .andExpect(jsonPath("$._embedded.employees[0].empId", comparesEqualTo(toIntExact(thirdEmployee.getEmpId()))));
    }
}
