package com.sebster.weereld.hobbes.plugins.plato.subscription;

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
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Getter
@EqualsAndHashCode(of = "chatId")
@ToString
public class PlatoSubscription {

	@Id
	private long chatId;

	@Embedded
	@Setter
	private PlatoSchedule schedule;

	public static PlatoSubscription platoSubscription(long chatId, @NonNull PlatoSchedule schedule) {
		return new PlatoSubscription(chatId, schedule);
	}

	public static final Property<PlatoSubscription, Long> CHAT_ID =
			property(PlatoSubscription.class, "chatId", PlatoSubscription::getChatId);

}
