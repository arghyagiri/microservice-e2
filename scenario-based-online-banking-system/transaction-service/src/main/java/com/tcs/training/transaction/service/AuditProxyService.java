package com.tcs.training.transaction.service;

import com.tcs.training.model.account.Transaction;
import com.tcs.training.model.audit.Audit;
import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Service
public class AuditProxyService {

	@Value("${proxy.service.audit.url:}")
	private String logAuditUrl;

	@Async
	public void createAuditLog(Transaction transaction) {
		RestTemplate restTemplate = new RestTemplate();
		Audit auditLog = Audit.builder()
			.transactionId(transaction.getTransactionId().toString())
			.receiverAccountNumber(transaction.getToAccountNumber())
			.senderAccountNumber(transaction.getFromAccountNumber())
			.transactionAmount(transaction.getAmount())
			.build();
		HttpEntity<Audit> auditHttpEntity = new HttpEntity<>(auditLog);
		ResponseEntity<Audit> response = restTemplate.exchange(logAuditUrl, HttpMethod.POST, auditHttpEntity,
				Audit.class);
		log.info("audit api response :: {}", response.getBody());
	}

}
