package com.hongrant.www.achieve.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongrant.www.achieve.comm.util.ParameterUtils;
import com.hongrant.www.achieve.service.MathMLToImageService;

/**
 *MathML转换成图片
 *
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RequestMapping("/client")
public class MathMLToImageController {

	@Autowired
	private MathMLToImageService mathMLToImageService;


	/**
	 *MathML转换成图片
	 * @return
	 */
	@GetMapping("/MathMLToImage")
	public void MathMLToImage(HttpServletResponse response) {
		Map<String, Object> params = ParameterUtils.getParameterMap();
		mathMLToImageService.MathMLToImage(params,response);

	}
}
