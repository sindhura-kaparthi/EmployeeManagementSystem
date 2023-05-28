package com.example.employeemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @Column(name = "aadhar", unique = true)
    private Long aadhar;

    @Pattern(regexp = "[A-Za-z]+( [A-Za-z]+)*", message = "Name should be alphabets only")
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Date should not be of future")
    private LocalDate dob;
    private Integer age;
    private String dept;
    private String city;

    //    @DateFormat
    //    @JsonDeserialize(using = CustomDateDeserializer.class)
    //    private Date dob;
    //    @Pattern(regexp = "^(0[1-9]|1\\d|2[0-9]|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$", message = "Invalid date format. Please use the format dd/MM/yyyy.")
    //    private String dob;
}