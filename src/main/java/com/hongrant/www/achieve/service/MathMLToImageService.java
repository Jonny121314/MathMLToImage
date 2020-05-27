package com.hongrant.www.achieve.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface MathMLToImageService {

	void MathMLToImage(Map<String, Object> params, HttpServletResponse response);

}
