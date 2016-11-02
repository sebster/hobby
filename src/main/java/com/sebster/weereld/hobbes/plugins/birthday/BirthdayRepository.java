package com.sebster.weereld.hobbes.plugins.birthday;

import static java.util.stream.Collectors.toCollection;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sebster.weereld.hobbes.people.Partner;
import com.sebster.weereld.hobbes.people.PartnerRepository;
import com.sebster.weereld.hobbes.people.Person;
import com.sebster.weereld.hobbes.people.PersonRepository;

@Repository
public class BirthdayRepository {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	public Set<Birthday> findAll() {
		Set<Birthday> bdays = new TreeSet<>();
		for (Person person : personRepository.findAll()) {
			person.birthDate().ifPresent(birthDate -> {
				bdays.add(new Birthday(person.nick(), birthDate));
			});
		}
		for (Partner partner : partnerRepository.findByDateNotNull()) {
			bdays.add(new Birthday(partner.nick1() + " & " + partner.nick2(), partner.date()));
		}
		return bdays;
	}

	public Optional<Birthday> findByName(String name) {
		return filter(bday -> bday.hasName(name)).findAny();
	}

	public Optional<Birthday> findByNames(String name1, String name2) {
		return filter(bday -> bday.hasName(name1 + " & " + name2) || bday.hasName(name2 + " & " + name1)).findAny();
	}

	public Set<Birthday> findByDate(LocalDate date) {
		int month = date.getMonthValue(), day = date.getDayOfMonth();
		return filterToSet(bday -> bday.month() == month && bday.day() == day && !date.isBefore(bday.date()));
	}

	public Set<Birthday> findByMonthAndDay(int month, int day) {
		return filterToSet(bday -> bday.month() == month && bday.day() == day);
	}

	public Set<Birthday> findByMonth(int month) {
		return filterToSet(bday -> bday.month() == month);
	}

	public Set<Birthday> findByYear(int year) {
		return filterToSet(bday -> bday.year() == year);
	}

	private Stream<Birthday> filter(Predicate<? super Birthday> predicate) {
		return findAll().stream().filter(predicate).sorted();
	}

	private Set<Birthday> filterToSet(Predicate<? super Birthday> predicate) {
		return filter(predicate).collect(toCollection(TreeSet::new));
	}

}
