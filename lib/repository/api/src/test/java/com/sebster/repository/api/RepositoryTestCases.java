package com.sebster.repository.api;

import static com.sebster.repository.api.TestObjectSpecification.withId;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public abstract class RepositoryTestCases<T extends TestObject> {

	private Repository<T> repository;

	@Before
	public void setUpRepository() {
		this.repository = createRepository();
		addTestObjects();
	}

	@Test
	public void find_one_returns_correct_object() {
		assertThat(repository.findOne(withId(1)), is(Optional.of(createTestObject(1, "a", "b"))));
	}

	@Test
	public void find_one_of_non_existing_object_returns_empty_optional() {
		assertThat(repository.findOne(withId(2)), is(Optional.empty()));
	}

	@Test(expected = RuntimeException.class)
	public void get_one_of_non_existing_throws_exception() {
		repository.getOne(withId(2));
	}

	protected abstract Repository<T> createRepository();

	protected abstract T createTestObject(int id, String a, String b);

	private void addTestObjects() {
		repository.addAll(asList(
				createTestObject(1, "a", "b"),
				createTestObject(3, "e", "f"),
				createTestObject(4, "z", "z"),
				createTestObject(5, "z", null)
		));
	}

}
