package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.EmployeeDTO;
import com.example.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.example.employeemanagementsystem.service.EmployeeService;
import com.example.employeemanagementsystem.utility.Constants;
import com.example.employeemanagementsystem.utility.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        return employeeService.addEmployee(employeeDTO);
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(value = "/employees/{aadhar}")
    public ResponseEntity<EmployeeDTO> getEmployeeDetails(@PathVariable Long aadhar) throws EmployeeNotFoundException {
        EmployeeDTO employeeDTO = employeeService.getEmployeeDetails(aadhar);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/employees")
    public ResponseEntity<Object> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        employeeService.updateEmployee(employeeDTO.getAadhar(), employeeDTO.getDept());
        return ResponseHandler.generateResponse(Constants.UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/employees/{aadhar}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long aadhar) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(aadhar);
        return ResponseHandler.generateResponse(Constants.DELETE_SUCCESS, HttpStatus.OK);
    }
}