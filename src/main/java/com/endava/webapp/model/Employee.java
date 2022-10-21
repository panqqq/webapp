package com.endava.webapp.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "Employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "The first_name should not be null")
    @NotEmpty(message = "The first_name should not be empty")
    @NotBlank(message = "The first_name should not be blank")
    @Column(name = "first_name")
    private String firstName;
    @NotNull(message = "The last_name should not be null")
    @NotEmpty(message = "The last_name should not be empty")
    @NotBlank(message = "The last_name should not be blank")
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Department department;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, Department department, String email, String phoneNumber, Double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
    }

    @NotNull(message = "The email should not be null")
    @NotEmpty(message = "The email should not be empty")
    @NotBlank(message = "The email should not be blank")
    @Column(unique = true)
    private String email;
    @NotNull(message = "The phone_number should not be null")
    @NotEmpty(message = "The phone_number should not be empty")
    @NotBlank(message = "The phone_number should not be blank")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Min(value = 1, message = "Salary should not be less than 1.0")
    private Double salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && Objects.equals(department, employee.department) && email.equals(employee.email) && phoneNumber.equals(employee.phoneNumber) && Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, department, email, phoneNumber, salary);
    }
}
