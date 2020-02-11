package com.sebster.weereld.hobbes.people;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.JpaRepository;

@Component
public class PersonJpaRepository extends JpaRepository<Person> {

	public PersonJpaRepository() {
		super(Person.class);
	}

}
