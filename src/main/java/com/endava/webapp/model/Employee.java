package com.endava.webapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "The first_name should not be blank")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "The last_name should not be blank")
    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Department department;

    @NotBlank(message = "The email should not be blank")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "The phone_number should not be blank")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Min(value = 1, message = "Salary should not be less than 1.0")
    private Double salary;

}

