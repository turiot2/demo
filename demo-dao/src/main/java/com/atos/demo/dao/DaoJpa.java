package com.atos.demo.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.atos.demo.model.User;
import com.atos.demo.port.DaoException;
import com.atos.demo.port.IDao;

/**
 * A implementation of an adapter to persist data in a relational database via JPA.
 */
@Service
public class DaoJpa implements IDao {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Optional<User> getUser(String login) {
		try {
			return userRepository.findById(login);
		}
		catch (DataAccessException e) {
			throw new DaoException("error while getting user", e);
		}
	}

	@Override
	public User saveUser(User user) {
		try {
			return userRepository.save(user);
		}
		catch (DataAccessException e) {
			throw new DaoException("error while saving user", e);
		}
	}

}
