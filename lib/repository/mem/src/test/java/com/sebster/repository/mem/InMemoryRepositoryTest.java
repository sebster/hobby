package com.sebster.repository.mem;

import com.sebster.repository.api.Repository;
import com.sebster.repository.api.RepositoryTestCases;
import com.sebster.repository.api.SimpleTestObject;

public class InMemoryRepositoryTest extends RepositoryTestCases<SimpleTestObject> {

	@Override
	protected Repository<SimpleTestObject> createRepository() {
		return new InMemoryRepository<>();
	}

	@Override
	protected SimpleTestObject createTestObject(int id, String a, String b) {
		return new SimpleTestObject(id, a, b);
	}

}