package br.com.gatherer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="devices")
public class Device {

	@Id
	private String id;
	
	private String name;
	
	private String description;
	
	private Long since;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSince() {
		return since;
	}

	public void setSince(Long since) {
		this.since = since;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
