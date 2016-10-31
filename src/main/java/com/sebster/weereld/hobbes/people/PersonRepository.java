package com.sebster.weereld.hobbes.people;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {

	@Override
	List<Person> findAll();

	Optional<Person> findByNickIgnoreCase(String nick);

	Optional<Person> findByTelegramUserId(int userId);

	List<Person> findByTelegramUserIdIsNotNull();

}
