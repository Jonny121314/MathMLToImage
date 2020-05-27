package com.hongrant.www.achieve.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongrant.www.achieve.comm.util.JsonResult;
import com.hongrant.www.achieve.comm.util.ParameterUtils;
import com.hongrant.www.achieve.service.HtmlToPdfService;

/**
 * html页面转为pdf
 * @author xiaomi
 *
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RequestMapping("/client")
public class HtmlToPdfController {
	
	@Autowired
	private HtmlToPdfService htmlToPdfService;

	/**
	 *html页面转为pdf
	 * @return
	 */
	@GetMapping("/HtmlToPdf")
	public JsonResult<Object> HtmlToPdf() {
		Map<String, Object> params = ParameterUtils.getParameterMap();
			return htmlToPdfService.HtmlToPdf(params);
	
	}
}
