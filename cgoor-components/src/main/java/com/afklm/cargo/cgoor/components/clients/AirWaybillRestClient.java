package com.afklm.cargo.cgoor.components.clients;

import javax.inject.Inject;

import org.sklsft.commons.rest.client.RestClient;
import org.springframework.stereotype.Component;

import com.afklm.cargo.cgocore.api.interfaces.core.AirWaybillCoreService;
import com.afklm.cargo.cgocore.api.model.core.AirWaybillCoreForm;
import com.afklm.cargo.cgocore.api.model.core.validation.ValidationReport;

@Component
public class AirWaybillRestClient implements AirWaybillCoreService {
	
	@Inject
	private RestClient client;

	@Override
	public ValidationReport register(AirWaybillCoreForm form) {
		return client.postForObject(AirWaybillCoreService.POST_URL, form, ValidationReport.class);
	}

}
