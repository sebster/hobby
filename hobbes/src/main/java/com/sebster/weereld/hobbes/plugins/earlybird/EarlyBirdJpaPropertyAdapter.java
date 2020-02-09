package com.sebster.weereld.hobbes.plugins.earlybird;

import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.DATE;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.NICK;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.WAKE_UP_TIME;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.WINNER;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird_.date;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird_.nick;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird_.wakeUpTime;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird_.winner;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.properties.AbstractJpaPropertyAdapter;

@Component
public class EarlyBirdJpaPropertyAdapter extends AbstractJpaPropertyAdapter<EarlyBird> {

	public EarlyBirdJpaPropertyAdapter() {
		map(NICK, nick);
		map(DATE, date);
		map(WAKE_UP_TIME, wakeUpTime);
		map(WINNER, winner);
	}

}
