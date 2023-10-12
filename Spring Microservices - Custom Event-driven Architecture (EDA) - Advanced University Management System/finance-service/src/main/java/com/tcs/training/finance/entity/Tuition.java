package com.tcs.training.finance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TUITION", uniqueConstraints = { @UniqueConstraint(name = "UC_TUITION", columnNames = { "course_id" }) })
public class Tuition implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tuition_sequence")
	@SequenceGenerator(name = "tuition_sequence", allocationSize = 100)
	@Column(name = "tuition_id")
	private Long tuitionId;

	@Column(name = "course_id")
	private Long courseId;

	@Column(name = "fee_amount")
	private BigDecimal feeAmount;

}
