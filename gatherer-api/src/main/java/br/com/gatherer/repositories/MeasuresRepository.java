package br.com.gatherer.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.gatherer.model.Measure;

@RepositoryRestResource(collectionResourceRel = "measures", path = "measures")
public interface MeasuresRepository extends PagingAndSortingRepository<Measure, String> {

	List<Measure> findByType(@Param("type") String type, Pageable p);

}
