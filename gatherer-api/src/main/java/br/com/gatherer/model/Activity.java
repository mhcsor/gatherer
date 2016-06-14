package br.com.gatherer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection="activities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {

	@Id
	private String id;
	
	@DBRef
	private Device device;
	
	private Long lastUpdate;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "Activity [device=" + device + ", lastUpdate=" + lastUpdate + "]";
	}
	
}
