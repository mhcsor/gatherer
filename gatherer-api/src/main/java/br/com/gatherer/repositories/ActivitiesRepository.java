package br.com.gatherer.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.gatherer.model.Activity;

@RepositoryRestResource(collectionResourceRel = "activities", path = "activities")
public interface ActivitiesRepository extends PagingAndSortingRepository<Activity, String> {

	List<Activity> findByLastUpdate(@Param(value = "timestamp") Long lastUpdate);
	
	
}
