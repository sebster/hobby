package com.sebster.weereld.hobbes.plugins.earlybird;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EarlyBirdRepository extends CrudRepository<EarlyBird, EarlyBirdId> {

	@Query("select eb from EarlyBird eb, Person p where eb.nick=p.nick and p.telegramUserId=:userId and eb.date=:date")
	Optional<EarlyBird> findByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDate date);

	Optional<EarlyBird> findFirstByDateOrderByWakeUpTime(LocalDate vroegeVogelDate);

	List<EarlyBird> findFirst7ByWinnerTrueOrderByDateDesc();

}
