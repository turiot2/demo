package com.atos.demo.dao2;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DBMaker;
import org.springframework.stereotype.Service;

import com.atos.demo.model.User;
import com.atos.demo.port.IDao;

/**
 * A implementation of an adapter to persist data in a MapDB store.
 */
@Service
public class DaoMapdb implements IDao {

	@SuppressWarnings("rawtypes")
	private final ConcurrentMap myMap;

	/** Constructor creating a singleton storage.
	 * if persisted on disk should be closed at bean PreDestroy phase.
	 */
	public DaoMapdb() {
		var db = DBMaker.memoryDB().make();
		myMap = db.hashMap("myMap").createOrOpen();
	}
	
	@Override
	public Optional<User> getUser(String login) {
		var user = (User) myMap.get(login);
		return Optional.ofNullable(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User saveUser(User user) {
		myMap.put(user.getLogin(), user);
		return user;
	}

}
