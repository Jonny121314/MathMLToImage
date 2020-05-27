package com.hongrant.www.achieve.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.hongrant.www.achieve.pojo.SysConfigVO;

@MapperScan
public interface SysConfigMapper {

	/**
	 * 加载所有系统配置项目
	 * @return
	 */
	public List<SysConfigVO> loadAllConfig();
}
