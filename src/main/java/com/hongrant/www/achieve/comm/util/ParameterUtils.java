package com.hongrant.www.achieve.comm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;


import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.net.MediaType;
import com.hongrant.www.achieve.comm.util.JSONConvert;
import com.hongrant.www.achieve.comm.util.StrTrim;


public class ParameterUtils {
	
	private static Logger log = LoggerFactory.getLogger(ParameterUtils.class);

	public static List<String> headerKeys = Lists.newArrayList(
			"sourceTypeCode", "sid", "token", "clientId", "_TOKEN", "TOKEN",
			"$RefererId", "$Referer");

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameterMap() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String url = request.getRequestURI();
		Object po = request.getAttribute("parametersMap");
		if (po == null) {
			Map<String, Object> parameterMap = getParameterMap(request);
			parameterMap.put("accessUrl", url);
			String token = MapUtils.getString(parameterMap, "token");
			if (StringUtils.isBlank(token)) {
				parameterMap.put("token", MapUtils.getString(parameterMap, "_TOKEN", 
						MapUtils.getString(parameterMap, "TOKEN")));
			}
			String referer = MapUtils.getString(parameterMap, "$Referer", "");
			if (StringUtils.isNotBlank(referer) && referer.indexOf("http") > -1) {
				StringBuffer contextPath = new StringBuffer(request.getScheme());
				contextPath.append("://");
				contextPath.append(request.getServerName());
				contextPath.append(request.getServerPort() == 80 ? "" : request
						.getServerPort());
				log.error("contextPath:" + contextPath);
				log.error("referer:" + referer);
				referer = referer.substring(contextPath.toString().length());
				parameterMap.put("$Referer", referer);
			}
			for (String key : parameterMap.keySet()) {//为防止前端传null字符串过来
				if (parameterMap.get(key)==null||parameterMap.get(key).equals("null")) {
					parameterMap.put(key, null);
				}
			}
			request.setAttribute("parametersMap", parameterMap);
			return StrTrim.mapTrim(parameterMap);
			//return parameterMap;
		} else {
			return StrTrim.mapTrim(((Map<String, Object>) po));
			//return (Map<String, Object>) po;
		}
	}

	public static Map<String, Object> getParameterMapOnlyPermission() {
		Map<String, Object> parameterMap = getParameterMap();
		Map<String, Object> permissionMap = Maps.newHashMap();
		if (MapUtils.isNotEmpty(parameterMap)) {
			for (String key : headerKeys) {
				if (parameterMap.containsKey(key)) {
					permissionMap.put(key, parameterMap.get(key));
				}
			}
		}
		return permissionMap;
	}

	private static Map<String, Object> getParameterMap(
			final ServletRequest request) {
		final Map<String, Object> parameterMap = WebUtils.getParametersStartingWith(request, null);
		parameterMap.put("method", ((HttpServletRequest) request).getMethod());
		if (isReadJson(request)) {
			try (InputStream is = request.getInputStream()) {
				String jsonString = IOUtils.toString(is, StringUtils.defaultString(
						request.getCharacterEncoding(), "UTF-8"));
				
				if (StringUtils.isNotBlank(jsonString)) {
					@SuppressWarnings("unchecked")
					Map<String, Object> jsonMap = new JSONConvert<Map<String, Object>>()
							.convert(Map.class, jsonString);

					if (MapUtils.isNotEmpty(jsonMap)) {
						parameterMap.putAll(jsonMap);
					}
				}
			} 
			catch (final IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (!CollectionUtils.isEmpty(headerKeys)
				&& request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			for (String headerKey : headerKeys) {
				if (parameterMap.containsKey(headerKey)) {
					continue;
				}
				parameterMap.put(headerKey, httpServletRequest.getHeader(headerKey));
			}
		}

		return parameterMap;
	}

	public static boolean isReadJson(final ServletRequest request) {
		return StringUtils.startsWith(request.getContentType(),
				MediaType.ANY_APPLICATION_TYPE.type());
	}

	/**
	 * 重写StringEscapeUtils.escapeHtml()方法，避免过滤中文
	 *
	 * @param s
	 * @return
	 */
	private static String cleanHtml(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		String safeHtml = Jsoup.clean(s, Whitelist.relaxed());
		return safeHtml;
	}

	public static void cleanHtmlForMap(Map<String, Object> params) {
		if (MapUtils.isEmpty(params)) {
			return;
		}
		for (Entry<String, Object> entry : params.entrySet()) {
			Object entryVal = entry.getValue();
			if (entryVal != null) {
				if (entryVal instanceof String) {
					params.put(entry.getKey(), cleanHtml(entryVal.toString()));
				} else if (entryVal instanceof String[]) {
					String[] entryValueArr = (String[]) entryVal;
					for (int k = 0; k < entryValueArr.length; k++) {
						entryValueArr[k] = cleanHtml(entryValueArr[k]);
					}
					params.put(entry.getKey(), entryValueArr);
				} else {
					// 其他对象时，转成json字符串后对json字符串进行清理，如果前后不一致则置空
					String srcJson = JSONUtils.toJSONString(entryVal);
					String cleanedJson = cleanHtml(srcJson);
					if (!StringUtils.equals(srcJson, cleanedJson)) {
						params.put(entry.getKey(), null);
					}
				}
			}
		}
	}
}
