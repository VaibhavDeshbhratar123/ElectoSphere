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
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Candidate name is required")
    @Size(min = 3, max = 100, message = "Candidate name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    @NotNull(message = "Election ID is required")
    private Election election;

    @NotNull(message = "Party name is required")
    private String party;

    @Min(value = 18, message = "Candidate must be at least 18 years old")
    private int age;

    @Size(max = 1000, message = "Biography cannot exceed 1000 characters")
    private String biography;


    @Size(max = 500, message = "Experience cannot exceed 500 characters")
    private String experience;
}