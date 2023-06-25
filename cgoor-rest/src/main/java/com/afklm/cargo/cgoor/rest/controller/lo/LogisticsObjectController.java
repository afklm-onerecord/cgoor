package com.afklm.cargo.cgoor.rest.controller.lo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.sklsft.commons.text.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afklm.cargo.cgocore.api.model.core.validation.ValidationReport;
import com.afklm.cargo.cgoor.services.LoService;

@Controller
public class LogisticsObjectController {
	
	@Autowired
	private JsonSerializer jsonSerializer;
	
	@Autowired
	private LoService service;

	@RequestMapping(value = "/logistics-object", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
	public void receiveLo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		receiveObject(request, response);
	}

	private void receiveObject(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		String body = IOUtils.toString(request.getInputStream(), "UTF-8");
		
		ValidationReport result = service.receive(body);

		sendResponse(response, result);
	}
	

	private void sendResponse(HttpServletResponse response, ValidationReport report) throws IOException {
		PrintWriter wr = response.getWriter();
	    response.setStatus(HttpServletResponse.SC_OK);
	    wr.append(jsonSerializer.serialize(report));
		wr.flush();
		wr.close();
	}
	
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody String load() {
		return "hello";
	}
}
