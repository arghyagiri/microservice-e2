package com.tcs.training.account.repository;

import com.tcs.training.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByAccountNumber(Long accountNumber);

}
