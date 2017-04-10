package com.mitrais.bootcamp.rms;

import com.mitrais.bootcamp.rms.data.constanta.EmployeeStatus;
import com.mitrais.bootcamp.rms.data.constanta.Gender;
import com.mitrais.bootcamp.rms.data.entity.Employee;
import com.mitrais.bootcamp.rms.data.entity.Project;
import com.mitrais.bootcamp.rms.data.repository.EmployeeRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RmsApplication.class)
@WebAppConfiguration
public class ProjectControllerTest {

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
    public void addProject() throws Exception {

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

        ArrayList<Project> projects = new ArrayList<>();
        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
        projects.add(project1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.projects", hasSize(1)));
    }

    @Test
    public void addProjectToExisting() throws Exception {

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

        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addProject(project1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<Project> projects = new ArrayList<>();
        projects.add(project1);

        Project project2 = new Project();
        project2.setProjectId("p_"+savedEmployee.getEmpId()+"_1");
        project2.setProjectName("RMS Angular");
        project2.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        projects.add(project2);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"1\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"2\"")))
                .andExpect(jsonPath("$.projects", hasSize(2)));
    }

    @Test
    public void setProjectEmpty() throws Exception {

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

        ArrayList<Project> projects = new ArrayList<>();

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.projects", hasSize(0)));
    }

    @Test
    public void setProjectEmptyFromExisting() throws Exception {

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

        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addProject(project1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<Project> projects = new ArrayList<>();

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"1\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"2\"")))
                .andExpect(jsonPath("$.projects", hasSize(0)));
    }

    @Test
    public void removeProjectFromExisting() throws Exception {

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

        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addProject(project1);

        Project project2 = new Project();
        project2.setProjectId("p_"+savedEmployee.getEmpId()+"_1");
        project2.setProjectName("RMS Angular");
        project2.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addProject(project2);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<Project> projects = new ArrayList<>();
        projects.add(project2);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"1\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"2\"")))
                .andExpect(jsonPath("$.projects", hasSize(1)));
    }

    @Test
    public void updateExistingProject() throws Exception {

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

        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));

        savedEmployee.addProject(project1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<Project> projects = new ArrayList<>();
        project1.setEndDate(new Date(Calendar.getInstance().getTimeInMillis()));
        projects.add(project1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"1\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"2\"")))
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].endDate", equalToIgnoringCase(project1.getEndDate().toString())));
    }

    @Test
    public void addProjectWithJobDesc() throws Exception {

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

        ArrayList<Project> projects = new ArrayList<>();
        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
        project1.setJobDesc(new String[]{"Implement UI component as described in mockup", "Use one of react state management", "Connect with spring based backend system"});

        projects.add(project1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"0\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"1\"")))
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].jobDesc", hasSize(3)))
                .andExpect(jsonPath("$.projects[0].jobDesc", containsInAnyOrder(project1.getJobDesc())));
    }

    @Test
    public void updateJobDesc() throws Exception {

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

        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
        project1.setJobDesc(new String[]{"Implement UI component as described in mockup", "Use one of react state management", "Connect with spring based backend system"});

        savedEmployee.addProject(project1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<Project> projects = new ArrayList<>();
        project1.setJobDesc(new String[]{"Implement UI component as described in mockup", "Use one of react state management like redux or mobx", "Connect with spring based backend system"});
        projects.add(project1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"1\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"2\"")))
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].jobDesc", hasSize(3)))
                .andExpect(jsonPath("$.projects[0].jobDesc", containsInAnyOrder(project1.getJobDesc())));
    }

    @Test
    public void removeJobDesc() throws Exception {

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

        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
        project1.setJobDesc(new String[]{"Implement UI component as described in mockup", "Use one of react state management", "Connect with spring based backend system"});

        savedEmployee.addProject(project1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<Project> projects = new ArrayList<>();
        project1.setJobDesc(new String[]{"Implement UI component as described in mockup"});
        projects.add(project1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"1\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"2\"")))
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].jobDesc", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].jobDesc", containsInAnyOrder(project1.getJobDesc())));
    }

    @Test
    public void setJobDescEmpty() throws Exception {

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

        Project project1 = new Project();
        project1.setProjectId("p_"+savedEmployee.getEmpId()+"_0");
        project1.setProjectName("RMS");
        project1.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
        project1.setJobDesc(new String[]{"Implement UI component as described in mockup", "Use one of react state management", "Connect with spring based backend system"});

        savedEmployee.addProject(project1);

        savedEmployee = employeeRepository.save(newEmployee);

        ArrayList<Project> projects = new ArrayList<>();
        String[] jobDesc = new String[0];
        project1.setJobDesc(jobDesc);
        projects.add(project1);

        mockMvc.perform(put("/employees/"+savedEmployee.getEmpId()+"/projects")
                .contentType(jsonContentType)
                .header("If-Match", "\"1\"")
                .content(this.json(projects)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/"+savedEmployee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Etag", equalToIgnoringCase("\"2\"")))
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].jobDesc", isEmptyOrNullString() ));
    }
}
