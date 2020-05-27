package com.hongrant.www.achieve.service;

import com.hongrant.www.achieve.comm.exception.ConfigurationException;

/**
 * 系统配置管理器,参数存到缓存中
 */
public interface SysConfigManager {

	/**
	 * 获取支付加签验签的密钥
	 * @return
	 * @throws ConfigurationException
	 */
	public String getSignatureKey() throws ConfigurationException;
	
	/**
	 * 获取通知URL
	 * @return
	 * @throws ConfigurationException
	 */
	public String getNotifyUrl() throws ConfigurationException;

	/**
	 * 根据配置名获取配置值
	 * 
	 * @param configKey
	 * @return
	 * @throws ConfigurationException
	 */
	public String getConfigValueByKey(String configKey) throws ConfigurationException;
	
}
