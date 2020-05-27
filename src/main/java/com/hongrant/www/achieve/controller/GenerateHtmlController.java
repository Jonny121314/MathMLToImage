package com.hongrant.www.achieve.controller;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongrant.www.achieve.comm.util.JsonResult;
import com.hongrant.www.achieve.comm.util.ParameterUtils;
import com.hongrant.www.achieve.service.GenerateHtmlService;

/**
 * 生成html页面
 * @author xiaomi
 *
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RequestMapping("/client")
public class GenerateHtmlController {
	
	@Autowired
	private GenerateHtmlService generateHtmlService;
	
	/**
	 *根据试题id，生成html页面
	 * @return
	 */
	@GetMapping("/GenerateHtml")
	public JsonResult<Object> GenerateHtml() {
		Map<String, Object> params = ParameterUtils.getParameterMap();
		if (MapUtils.getInteger(params, "type")==1) {//学生试卷：空白试卷+答案末尾
			return generateHtmlService.GenerateStudentHtml(params);
		}else if (MapUtils.getInteger(params, "type")==2) {//老师试卷：空白试卷+答案题末
			return generateHtmlService.GenerateTeacherHtml(params);
		}else {//生成空白试卷
			return generateHtmlService.GenerateHtml(params);
		}
	}
	
}
