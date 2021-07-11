package com.atos.demo.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.atos.demo.model.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, String> {
}
