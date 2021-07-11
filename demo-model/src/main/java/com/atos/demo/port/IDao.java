package com.atos.demo.port;

import java.util.Optional;

import com.atos.demo.model.User;

/**
 * A interface (Port in hexagonal architecture) that an adapter must immplement.
 */
public interface IDao {

	/** Get a user by key. */
	Optional<User> getUser(String login);
	/** Save a user (duplicate case not yet managed). */
	User saveUser(User user);
	
}
