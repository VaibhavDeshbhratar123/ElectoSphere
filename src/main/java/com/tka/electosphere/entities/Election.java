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
public class Election {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Election name is required")
	@Size(min = 3, max = 100, message = "Election name must be between 3 and 100 characters")
	@Column(nullable = false, unique = true)
	private String name;

	@Future(message = "Election date must be in the future")
	@Column(nullable = false)
	private LocalDateTime date;

	@Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;

	@NotNull(message = "Election type is required")
	private String type;

	private String status; // Example: "Scheduled", "Completed", "Ongoing"

	@NotNull(message = "Creator information is required")
	private String createdBy;

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt;
}