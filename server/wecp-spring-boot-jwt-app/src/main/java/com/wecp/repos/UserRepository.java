package com.wecp.repos;

import org.springframework.data.repository.CrudRepository;

import com.wecp.entities.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    
   public User findByUserId(String item);
   public List<User> findAll();



}