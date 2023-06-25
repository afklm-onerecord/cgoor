package com.afklm.cargo.cgoor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.cargo.cgocore.api.model.core.AirWaybillCoreForm;
import com.afklm.cargo.cgocore.api.model.core.validation.ValidationReport;
import com.afklm.cargo.cgoor.components.clients.AirWaybillRestClient;

@Component
public class LoService {
	
	@Autowired
	private AirWaybillRestClient client;
	
	public ValidationReport receive(String body) {
		
		String origin = getOrigin(body);
		String sestination = getDestination(body);
		String airWaybillNumber = "057-97502974";
		String forwarder = "DB Schenker San Fransisco";
	
		
		AirWaybillCoreForm form = new AirWaybillCoreForm();
		form.setAirWaybillNumber(airWaybillNumber);
		form.setOriginIataCode(origin);
		form.setDestinationIataCode(sestination);
		form.setForwarderName(forwarder);
		
		return client.register(form);
	}

	private String getDestination(String body) {
		return "PAR";
	}

	private String getOrigin(String body) {
		return "SFO";
	}

}
