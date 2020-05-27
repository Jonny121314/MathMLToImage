package com.hongrant.www.achieve.service;

import java.util.Map;

import com.hongrant.www.achieve.comm.util.JsonResult;

public interface GenerateHtmlService {

	JsonResult<Object> GenerateHtml(Map<String, Object> params);

	JsonResult<Object> GenerateStudentHtml(Map<String, Object> params);

	JsonResult<Object> GenerateTeacherHtml(Map<String, Object> params);

}
