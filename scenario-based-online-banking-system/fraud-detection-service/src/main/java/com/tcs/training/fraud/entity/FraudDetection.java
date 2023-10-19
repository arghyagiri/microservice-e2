package com.tcs.training.fraud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FRAUD_ANALYSIS")
public class FraudDetection implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fraud_sequence")
	@SequenceGenerator(name = "fraud_sequence", allocationSize = 100)
	private Long fraudId;

	private String transactionDetails;

	private UUID transactionId;

	private Character suspiciousFlag;

}
