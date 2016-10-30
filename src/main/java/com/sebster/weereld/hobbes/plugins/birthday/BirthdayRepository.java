package com.sebster.weereld.hobbes.plugins.birthday;

import static java.util.stream.Collectors.toCollection;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

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
		List<Person> persons = personRepository.findAll();
		for (Person person : persons) {
			if (person.birthDate().isPresent()) {
				bdays.add(new Birthday(person.nick(), person.birthDate().get()));
			}
		}
		List<Partner> partners = partnerRepository.findByDateNotNull();
		for (Partner partner : partners) {
			bdays.add(new Birthday(partner.nick1() + " & " + partner.nick2(), partner.date()));
		}
		return bdays;
	}

	public Optional<Birthday> findByName(String name) {
		return filter(findAll(), bday -> bday.name().equalsIgnoreCase(name)).stream().findAny();
	}

	public Set<Birthday> findByDate(LocalDate date) {
		int month = date.getMonthValue(), day = date.getDayOfMonth();
		return filter(findAll(), bday -> bday.month() == month && bday.day() == day && !date.isBefore(bday.date()));
	}

	public Set<Birthday> findByMonthAndDay(int month, int day) {
		return filter(findAll(), bday -> bday.month() == month && bday.day() == day);
	}

	public Set<Birthday> findByMonth(int month) {
		return filter(findAll(), bday -> bday.month() == month);
	}

	public Set<Birthday> findByYear(int year) {
		return filter(findAll(), bday -> bday.year() == year);
	}

	private Set<Birthday> filter(Collection<Birthday> birthdays, Predicate<? super Birthday> predicate) {
		return birthdays.stream().filter(predicate).collect(toCollection(TreeSet::new));
	}

}
