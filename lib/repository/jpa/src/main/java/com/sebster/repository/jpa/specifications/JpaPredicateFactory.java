package com.sebster.repository.jpa.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaPredicateFactory {

	private final String charsToEscape;

	public Predicate contains(CriteriaBuilder cb, Expression<String> expression, String substring) {
		String pattern = "%" + escape(substring, '!') + "%";
		return cb.like(expression, pattern, '!');
	}

	public Predicate containsIgnoreCase(CriteriaBuilder cb, Expression<String> expression, String substring) {
		String pattern = "%" + escape(substring.toLowerCase(), '!') + "%";
		return cb.like(cb.lower(expression), pattern, '!');
	}

	private String escape(String string, char escapeChar) {
		StringBuilder escaped = new StringBuilder();
		for (char ch : string.toCharArray()) {
			if (charsToEscape == null || charsToEscape.indexOf(ch) >= 0) {
				escaped.append(escapeChar);
			}
			escaped.append(ch);
		}
		return escaped.toString();
	}

}
