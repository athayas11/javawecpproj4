package com.wecp;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wecp.controller.JwtAuthenticationController;
import com.wecp.controller.UserTransactionController;
import com.wecp.entities.UserTransaction;
import com.wecp.model.JwtRequest;
import com.wecp.model.JwtResponse;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestApp {
	@Autowired
	JwtAuthenticationController authenticationController;
	
	@Autowired
	UserTransactionController userTransactionController;
	
	@Test
	   void contextLoads(ApplicationContext context) {
		 assertThat(context).isNotNull();
	   }
	
	@Test
	  void testSuccessfulAuthentication(ApplicationContext context) {
		JwtRequest user = new JwtRequest("admin.user", "12345");
		try {
			 ResponseEntity<JwtResponse> res = (ResponseEntity<JwtResponse>) authenticationController.createAuthenticationToken(user);
			 JwtResponse r =  res.getBody();
			 String token = r.getToken();
			 assertNotNull(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertEquals(true, false);
		}
	  }
	
	@Test
	  void testWrongCredentials(ApplicationContext context) {
		JwtRequest user = new JwtRequest("admin.user", "123456");
		try {
			 ResponseEntity<JwtResponse> res = (ResponseEntity<JwtResponse>) authenticationController.createAuthenticationToken(user);
			 JwtResponse r =  res.getBody();
			 String token = r.getToken();
			 assertNull(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals(true, true);
		}
	  }
	
	@Test
	  void testCreditTransactionWithoutAuthorization(ApplicationContext context) {
		try {
			  SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			    SecurityContextHolder.setContext(ctx);
			    ctx.setAuthentication(new Authentication() {
			    	JwtRequest user = new JwtRequest("John", "12345");
					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "John";
					}
					
					@Override
					public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
						// TODO Auto-generated method stub
						//return true;
					}
					
					@Override
					public boolean isAuthenticated() {
						// TODO Auto-generated method stub
						return true;
					}
					
					@Override
					public Object getPrincipal() {
						// TODO Auto-generated method stub
						return "John";
					}
					
					@Override
					public Object getDetails() {
						// TODO Auto-generated method stub
						return user;
					}
					
					@Override
					public Object getCredentials() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Collection<? extends GrantedAuthority> getAuthorities() {
						// TODO Auto-generated method stub
						return new ArrayList<>();
					}
				});
			UserTransaction transaction = new UserTransaction();
			transaction.setUserId("John");
			transaction.setTransactionAmount(100d);
			transaction.setTransactionType("CREDIT");
			ResponseEntity<String> res = (ResponseEntity<String>) userTransactionController.transact(transaction);
			assertEquals(res.getBody().equals("success"), false);
		} catch (org.springframework.security.access.AccessDeniedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			assertEquals(true, true);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			assertEquals(true, false);
		}
	  }

}
