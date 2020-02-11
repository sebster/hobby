package com.sebster.weereld.hobbes.plugins.birthday;

import static com.sebster.weereld.hobbes.people.PartnerSpecification.hasDate;
import static com.sebster.weereld.hobbes.people.PersonSpecification.hasBirthDate;
import static java.util.stream.Collectors.toCollection;

import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sebster.repository.api.Repository;
import com.sebster.weereld.hobbes.people.Partner;
import com.sebster.weereld.hobbes.people.Person;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class BirthdayService {

	private final @NonNull Repository<Person> personRepository;
	private final @NonNull Repository<Partner> partnerRepository;

	public Stream<Birthday> findAll(Predicate<Birthday> specification) {
		return Stream.concat(
				personRepository.findAll(hasBirthDate()).map(Birthday::of),
				partnerRepository.findAll(hasDate()).map(Birthday::of)
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
