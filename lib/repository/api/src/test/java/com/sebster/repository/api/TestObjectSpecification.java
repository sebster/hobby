package com.sebster.repository.api;

import java.util.Objects;

import com.sebster.repository.api.specifications.Specification;
import lombok.Value;

public interface TestObjectSpecification extends Specification<TestObject> {

	static WithId withId(int id) {
		return new WithId(id);
	}

	@Value
	class WithId implements TestObjectSpecification {
		int id;

		@Override
		public boolean isSatisfiedBy(TestObject object) {
			return Objects.equals(object.getId(), id);
		}
	}

}
