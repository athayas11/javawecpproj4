package com.wecp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserTransactionController {
	UserTransactionRepository userTransactionRepository;

	UserRepository userRepository;
	
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	public ResponseEntity<?> transact(@RequestBody UserTransaction transaction)
			throws Exception {

			if(transaction.getTransactionType().equals("CREDIT")) {
				
			}
			else if(transaction.getTransactionType().equals("DEBIT")){
				
			}

		Map<String, String> data = new HashMap<>();
		data.put("success", "Transaction performed successfully");
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
