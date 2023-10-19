package com.tcs.training.audit.entity;

import com.tcs.training.model.account.TransactionStatus;
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
@Table(name = "AUDIT")
public class Audit implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_sequence")
	@SequenceGenerator(name = "audit_sequence", allocationSize = 100)
	Long auditId;

	Long senderAccountNumber;

	Long receiverAccountNumber;

	BigDecimal transactionAmount;

	TransactionStatus transactionStatus;

	String transactionId;

}
