package com.hongrant.www.achieve.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongrant.www.achieve.comm.util.JsonResult;
import com.hongrant.www.achieve.comm.util.ParameterUtils;
import com.hongrant.www.achieve.service.BaiduDiscernService;

/**
 * 百度图片文字识别
 * @author jonny
 *
 */
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RequestMapping("/client")
public class BaiduDiscernController {
	
	@Autowired
	private BaiduDiscernService baiduDiscernService;
	
	/**
	 * 百度api本地图片文字识别
	 * @return
	 */
	@PostMapping("/baidu/path")
	public JsonResult<Object> getDiscernByPath() {
		Map<String, Object> params = ParameterUtils.getParameterMap();
		String path = MapUtils.getString(params, "path");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String pathResult = baiduDiscernService.checkFile(path);
			result.put("result", pathResult);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return new JsonResult<Object>(result);
	}
	
	/**
	 * 百度api在线图片文字识别
	 * @return
	 */
	@PostMapping("/baidu/url")
	public JsonResult<Object> getDiscernByUrl() {
		Map<String, Object> params = ParameterUtils.getParameterMap();
		String path = MapUtils.getString(params, "path");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String pathResult = baiduDiscernService.checkUrl(path);
			result.put("result", pathResult);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return new JsonResult<Object>(result);
	}

}
