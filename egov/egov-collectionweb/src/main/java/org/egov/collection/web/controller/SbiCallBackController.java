package org.egov.collection.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SbiCallBackController {
	private static final Logger LOGGER = Logger.getLogger(SbiCallBackController.class);
	
	@RequestMapping("/public/sbi/push")
	@ResponseBody
	public String processPushResponce(HttpServletRequest request){
		LOGGER.info("=========== start  SbiCallBackController processPushResponce ========");
		LOGGER.info("request method "+request.getMethod());
		LOGGER.info("Attribute info");
		Enumeration<String> ae=request.getAttributeNames();
		while (ae.hasMoreElements()) {
			String s=ae.nextElement();
			LOGGER.info(s+" = "+request.getAttribute(s));
		}
		LOGGER.info("Parameter info");
		Enumeration<String> pe=request.getParameterNames();
		while (pe.hasMoreElements()) {
			String s=pe.nextElement();
			LOGGER.info(s+" = "+request.getParameter(s));
		}
		LOGGER.info("=========== end  SbiCallBackController processPushResponce ========");
		return "successfully received";
	}

}
