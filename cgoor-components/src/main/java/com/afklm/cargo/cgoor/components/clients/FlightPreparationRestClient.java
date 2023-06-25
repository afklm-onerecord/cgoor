package com.afklm.cargo.cgoor.components.clients;

import javax.inject.Inject;

import org.sklsft.commons.rest.client.RestClient;
import org.springframework.stereotype.Component;

import com.afklm.cargo.cgocore.api.interfaces.core.FlightPreparationCoreService;
import com.afklm.cargo.cgocore.api.model.core.UldOnFlightCoreForm;
import com.afklm.cargo.cgocore.api.model.core.validation.ValidationReport;

@Component
public class FlightPreparationRestClient implements FlightPreparationCoreService {
	
	@Inject
	private RestClient client;


	@Override
	public ValidationReport registerUldOnFlight(UldOnFlightCoreForm form) {
		return client.postForObject(FlightPreparationCoreService.POST_ULD_URL, form, ValidationReport.class);
	}

}
