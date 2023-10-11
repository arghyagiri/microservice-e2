package com.tcs.training.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STUDENT", uniqueConstraints = {
		@UniqueConstraint(name = "UC_STUDENT", columnNames = { "name", "course_id" }) })
public class Student implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
	@SequenceGenerator(name = "student_sequence", allocationSize = 100)
	@Column(name = "student_id")
	private Long studentId;

	@Column(name = "name")
	private String name;

	@Column(name = "course_id")
	private Long courseId;

}
