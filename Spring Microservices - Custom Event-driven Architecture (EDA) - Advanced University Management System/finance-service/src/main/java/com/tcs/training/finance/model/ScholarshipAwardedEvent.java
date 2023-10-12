package com.tcs.training.finance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScholarshipAwardedEvent {

	Long studentId;

	BigDecimal scholarshipAmount;

}
