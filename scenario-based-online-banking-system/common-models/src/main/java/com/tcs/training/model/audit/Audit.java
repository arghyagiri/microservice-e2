package com.tcs.training.model.audit;

import com.tcs.training.model.account.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Audit {

    private Long senderAccountNumber;

    private Long receiverAccountNumber;

    private BigDecimal transactionAmount;

    private TransactionStatus transactionStatus;

    private String transactionId;
}
