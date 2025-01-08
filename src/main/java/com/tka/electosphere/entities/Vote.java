package com.tka.electosphere.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voter_id", nullable = false)
    @NotNull(message = "Voter ID is required")
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @NotNull(message = "Candidate ID is required")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    @NotNull(message = "Election ID is required")
    private Election election;

    private LocalDateTime voteTime = LocalDateTime.now();

    private boolean isVerified;
}