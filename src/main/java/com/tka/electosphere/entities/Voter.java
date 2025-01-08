package com.tka.electosphere.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Voter name is required")
    @Size(min = 3, max = 100, message = "Voter name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Voter ID is required")
    @Size(min = 6, max = 20, message = "Voter ID must be between 6 and 20 characters")
    @Column(nullable = false, unique = true)
    private String voterID;

    @Min(value = 18, message = "Voter must be at least 18 years old")
    @Max(value = 120, message = "Voter age must be less than 120")
    @Column(nullable = false)
    private int age;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Invalid phone number")
    private String phone;

    @Size(max = 250, message = "Address cannot exceed 250 characters")
    private String address;

    private String city;
    private String state;
    private String zipCode;

    @Column(nullable = false)
    private boolean isEligible;

    private boolean hasVoted;
}