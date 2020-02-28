package com.sebster.weereld.hobbes.plugins.birthday;

import static com.sebster.telegram.botapi.data.TelegramChatType.GROUP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;

import com.sebster.commons.clock.ClockService;
import com.sebster.repository.api.Repository;
import com.sebster.repository.mem.InMemoryRepository;
import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramUser;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.telegram.botapi.test.TelegramServiceStub;
import com.sebster.telegram.botapi.test.TelegramStub;
import com.sebster.weereld.hobbes.people.Partner;
import com.sebster.weereld.hobbes.people.Person;

public class BirthdayPluginTest {

	private ClockService clockService;
	private TelegramStub telegram;
	private TelegramServiceStub telegramService;
	private TelegramUser hobbes;

	private Repository<Person> personRepository;
	private Repository<Partner> partnerRepository;
	private BirthdayService birthdayService;
	private BirthdayPlugin birthdayPlugin;

	@Before
	public void setUp() {
		clockService = new ClockService(Clock.fixed(Instant.parse("2020-02-27T23:02:31Z"), ZoneId.of("Europe/Amsterdam")));
		hobbes = TelegramUser.builder().id(1).bot(true).firstName("Hobbes").build();
		telegramService = new TelegramServiceStub(clockService);
		telegram = new TelegramStub(telegramService);
		telegram.setMe(hobbes);

		personRepository = new InMemoryRepository<>();
		partnerRepository = new InMemoryRepository<>();

		birthdayService = new BirthdayService(personRepository, partnerRepository);
		birthdayPlugin = new BirthdayPlugin(new BirthdayProperties(1L), birthdayService, clockService);
		birthdayPlugin.setTelegramService(telegramService);
		birthdayPlugin.setPersonRepository(personRepository);
	}

	@Test
	public void bday_of_known_person_is_correct() {
		personRepository.add(new Person("Sebster", 1, LocalDate.parse("1975-02-21"), "Europe/Amsterdam", "Utrecht", "NL"));

		TelegramChat chat = TelegramChat.builder().id(-10L).type(GROUP).build();
		telegram.createChat(chat);

		birthdayPlugin.visitTextMessage(
				TelegramTextMessage.builder()
						.messageId(3)
						.date(clockService.date())
						.text("bday sebster")
						.from(TelegramUser.builder().id(2).firstName("Joe").build())
						.chat(chat)
						.build()
		);

		TelegramTextMessage message = telegram.receiveMessage(TelegramTextMessage.class).orElseThrow();
		assertThat(message.getChat(), is(chat));
		assertThat(message.getText(), is("Sebster is geboren op 1975-02-21 en is 45 jaar oud."));
	}

}
