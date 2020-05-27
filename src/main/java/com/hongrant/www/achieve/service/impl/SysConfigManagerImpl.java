package com.hongrant.www.achieve.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongrant.www.achieve.comm.exception.ConfigurationException;
import com.hongrant.www.achieve.mapper.SysConfigMapper;
import com.hongrant.www.achieve.pojo.SysConfigVO;
import com.hongrant.www.achieve.service.SysConfigManager;


@Service
public class SysConfigManagerImpl implements SysConfigManager, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(SysConfigManagerImpl.class);
	
	//系统配置缓存到该map中
	private static final Map<String, String> CONFIG_MAP = new ConcurrentHashMap<String, String>();
	
	@Autowired	
	private SysConfigMapper sysConfigMapper;
	
	public String getSignatureKey() throws ConfigurationException {
		return getConfigValueByKey("signature_key");
	}
	
	public String getNotifyUrl() throws ConfigurationException {
		return getConfigValueByKey("payment_notifyUrl");
	}
	
	public String getConfigValueByKey(String configKey) throws ConfigurationException {
		if(!CONFIG_MAP.containsKey(configKey)) {
			String errMsg = "系统错误：没有名称为[" + configKey + "]的配置";
			logger.error(errMsg);
			throw new ConfigurationException(errMsg);
		}
		String configValue = CONFIG_MAP.get(configKey);
		return configValue;
	}
	
	public void afterPropertiesSet() throws Exception {
		//1. 加载配置
		List<SysConfigVO> sysConfigs = sysConfigMapper.loadAllConfig();
		for (SysConfigVO vo : sysConfigs) {
			CONFIG_MAP.put(vo.getParamName(), vo.getParamValue());
		}
	}

}
