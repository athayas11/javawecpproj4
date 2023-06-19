package com.wecp.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wecp.entities.UserTransaction;

public interface UserTransactionRepository extends CrudRepository<UserTransaction, Long> {

	
	 public List<UserTransaction> findByUserId(String userId);
}
