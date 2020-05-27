package com.hongrant.www.achieve.comm.exception;

/**
 * Description: 配置异常<br/>
 *
 * @author chen.heng
 * @date: 2018年5月27日
 */
public class ConfigurationException extends Exception {

	private static final long serialVersionUID = -7742047193448470505L;

	public ConfigurationException() {
		super();
	}
	
	public ConfigurationException(String msg) {
		super(msg);
	}
	
	public ConfigurationException(Throwable t) {
		super(t);
	}
	
	public ConfigurationException( String msg, Throwable t) {
		super( msg, t);
	}
}
