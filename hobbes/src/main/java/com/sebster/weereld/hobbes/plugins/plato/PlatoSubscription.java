package com.sebster.weereld.hobbes.plugins.plato;

import static com.sebster.repository.api.properties.Property.property;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.sebster.repository.api.properties.Property;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@EqualsAndHashCode(of = { "chatId" })
@AllArgsConstructor(access = PRIVATE)
public class PlatoSubscription {

	@Id
	private long chatId;

	@Embedded
	private PlatoSchedule schedule;

	public void setSchedule(PlatoSchedule schedule) {
		this.schedule = schedule;
	}

	static PlatoSubscription platoSubscription(long chatId, PlatoSchedule schedule) {
		return new PlatoSubscription(chatId, schedule);
	}

	public static final Property<PlatoSubscription, Long> CHAT_ID =
			property(PlatoSubscription.class, "chatId", PlatoSubscription::getChatId);

}
