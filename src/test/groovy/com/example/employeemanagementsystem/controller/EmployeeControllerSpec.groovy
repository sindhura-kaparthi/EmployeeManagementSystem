package com.example.employeemanagementsystem.controller

import com.example.employeemanagementsystem.dto.EmployeeDTO
import com.example.employeemanagementsystem.service.EmployeeService
import com.example.employeemanagementsystem.utility.Constants
import com.example.employeemanagementsystem.utility.ResponseHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest(controllers = [EmployeeController])
class EmployeeControllerSpec extends Specification {

    @MockBean
    private EmployeeService employeeService

    @Autowired
    private MockMvc mvc

    def "should add an employee"() {
        given:
        def employeeJSON = '{\"aadhar\":123,\"name\":\"ABc\",\"dob\":\"30/05/2023\",' +
                '\"age\":20,\"dept\":"CSE",' + '\"city\":\"Banglore\"}'
        String expectedMessage = Constants.EMPLOYEE_ADDED
        def expectedResponse = ResponseHandler.generateResponse(expectedMessage, HttpStatus.CREATED)

        when:
        when(employeeService.addEmployee(any(EmployeeDTO))).thenReturn(expectedResponse)
        def result = mvc.perform(MockMvcRequestBuilders.post("/employees").content(employeeJSON)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()

        then:
        result.response.status == expectedResponse.statusCode.value()
        def responseJson = result.getResponse().getContentAsString()
        def response = new groovy.json.JsonSlurper().parseText(responseJson)
        response.message == expectedMessage
    }

    def "should get all employees with status 200"() {
        given:
        def employee1 = new EmployeeDTO(aadhar: 100, name: "hey", dob: LocalDate.parse("30/05/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")), age: 0, dept: "marketing", city: "hyd")
        def employee2 = new EmployeeDTO(aadhar: 100, name: "hey", dob: LocalDate.parse("30/05/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")), age: 0, dept: "marketing", city: "hyd")
        def employeeList = [employee1, employee2]

        when:
        when(employeeService.getAllEmployees()).thenReturn(employeeList)
        def result = mvc.perform(MockMvcRequestBuilders.get("/employees")
                .contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk()).andExpect(jsonPath('$.length()').value(2))
    }

    def "should get employee details"() {
        given:
        def employee1 = new EmployeeDTO(aadhar: 1234567890, name: "Abc",
                dob: LocalDate.parse("30/05/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                age: 30, dept: "CSE", city: "hyd")
        Long aadhar = 1234567890
        def expectedResponseEntity = new ResponseEntity<>(employee1, HttpStatus.OK)

        when:
        when(employeeService.getEmployeeDetails(aadhar)).thenReturn(employee1)
        def result = mvc.perform(MockMvcRequestBuilders.get("/employees/{aadhar}", aadhar)
                .contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        def responseJson = result.andReturn().getResponse().getContentAsString()
        def response = new groovy.json.JsonSlurper().parseText(responseJson)
        response.aadhar == employee1.aadhar
        response.name == employee1.name
        response.dept == employee1.dept
    }

    def "should delete an employee by aadhar"() {
        given:
        def aadhar = 123
        String expectedMessage = Constants.DELETE_SUCCESS
        def expectedResponse = ResponseHandler.generateResponse(Constants.DELETE_SUCCESS, HttpStatus.OK)

        when:
        employeeService.deleteEmployee(aadhar) >> {}

        def result = mvc.perform(MockMvcRequestBuilders.delete("/employees/{aadhar}", aadhar))

        then:
        result.andExpect(status().isOk())
        def responseJson = result.andReturn().getResponse().getContentAsString()
        def response = new groovy.json.JsonSlurper().parseText(responseJson)
        response.message == expectedMessage
    }

}
