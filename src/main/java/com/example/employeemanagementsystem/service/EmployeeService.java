package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dto.EmployeeDTO;
import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import com.example.employeemanagementsystem.utility.Constants;
import com.example.employeemanagementsystem.utility.ResponseHandler;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRespository;

    private static int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public ResponseEntity<Object> addEmployee(EmployeeDTO employeeDTO) {
        if (employeeRespository.findById(employeeDTO.getAadhar()).isPresent())
            return ResponseHandler.generateResponse(Constants.EMPLOYEE_ALREADY_EXISTS, HttpStatus.CONFLICT);

        employeeDTO.setAge(calculateAge(employeeDTO.getDob()));
        ModelMapper modelMapper = new ModelMapper();
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employeeRespository.save(employee);
        return ResponseHandler.generateResponse(Constants.EMPLOYEE_ADDED, HttpStatus.CREATED);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList = employeeRespository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<EmployeeDTO> employeeDTOList = employeeList.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return employeeDTOList;
    }

    public EmployeeDTO getEmployeeDetails(Long aadhar) throws EmployeeNotFoundException {
        Employee employee = employeeRespository.findById(aadhar)
                .orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_DOES_NOT_EXIST));
        ModelMapper modelMapper = new ModelMapper();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        employeeDTO.setAge(calculateAge(employee.getDob()));
        return employeeDTO;
//        EmployeeDTO employeeDTO = EmployeeDTO.builder()
//                .aadhar(employee.getAadhar())
//                .name(employee.getName())
//                .dob(employee.getDob())
//                .age(calculateAge(employee.getDob()))
//                .dept(employee.getDept())
//                .city(employee.getCity())
//                .build();
    }

    public void updateEmployee(Long aadhar, String dept) throws EmployeeNotFoundException {
        Employee employee = employeeRespository.findById(aadhar)
                .orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_DOES_NOT_EXIST));
        employee.setDept(dept);
    }

    public void deleteEmployee(Long aadhar) throws EmployeeNotFoundException {
        employeeRespository.findById(aadhar)
                .orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_DOES_NOT_EXIST));
        employeeRespository.deleteById(aadhar);
    }
}
