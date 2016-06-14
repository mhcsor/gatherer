package br.com.gatherer.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.gatherer.model.Device;

@RepositoryRestResource(collectionResourceRel = "devices", path = "devices")
public interface DevicesRepository extends PagingAndSortingRepository<Device, String> {

}
