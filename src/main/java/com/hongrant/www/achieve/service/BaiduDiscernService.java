package com.hongrant.www.achieve.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface BaiduDiscernService {
	/**
	 * 识别本地图片的文字
	 */
	public String checkFile(String path)throws URISyntaxException, IOException;
	
	/**
	 * @param url 在线图片url
	 * @return 识别结果，为json格式
	 */
	public String checkUrl(String url) throws IOException, URISyntaxException ;

}
