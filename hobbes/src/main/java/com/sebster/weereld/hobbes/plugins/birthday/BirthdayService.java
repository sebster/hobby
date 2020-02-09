package com.sebster.weereld.hobbes.plugins.birthday;

import static java.util.stream.Collectors.toCollection;

import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sebster.weereld.hobbes.people.Partner;
import com.sebster.weereld.hobbes.people.PartnerRepository;
import com.sebster.weereld.hobbes.people.Person;
import com.sebster.weereld.hobbes.people.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class BirthdayService {

	private final @NonNull PersonRepository personRepository;
	private final @NonNull PartnerRepository partnerRepository;

	public Stream<Birthday> findAll(Predicate<Birthday> specification) {
		return Stream.concat(
				personRepository.findAll().stream().filter(Person::hasBirthDate).map(Birthday::of),
				partnerRepository.findAll().stream().filter(Partner::hasDate).map(Birthday::of)
		).filter(specification);
	}

	public NavigableSet<Birthday> birthdays(Predicate<Birthday> birthdayPredicate) {
		return findAll(birthdayPredicate).collect(toCollection(TreeSet::new));
	}

	public Optional<Birthday> birthday(Predicate<Birthday> specification) {
		NavigableSet<Birthday> birthdays = birthdays(specification);
		if (birthdays.size() > 1) {
			throw new IllegalStateException("Incorrect result set size; " + birthdays.size());
		}
		return Optional.ofNullable(birthdays.pollFirst());
	}

}
