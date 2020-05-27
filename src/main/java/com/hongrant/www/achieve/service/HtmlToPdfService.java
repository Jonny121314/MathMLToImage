package com.hongrant.www.achieve.service;

import java.util.Map;

import com.hongrant.www.achieve.comm.util.JsonResult;

public interface HtmlToPdfService {

	JsonResult<Object> HtmlToPdf(Map<String, Object> params);

}
