package com.sebster.weereld.hobbes.people;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends CrudRepository<Partner, PartnerId> {

	@Override
	List<Partner> findAll();

}
