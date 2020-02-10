package com.sebster.repository.jpa.specifications;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "repository.jpa.predicates")
@Data
@NoArgsConstructor
public class JpaPredicateProperties {

	Like like = new Like();

	@Data
	@NoArgsConstructor
	public static class Like {

		/** Default is null, in which case all characters are escaped. */
		String charsToEscape;

	}

}
