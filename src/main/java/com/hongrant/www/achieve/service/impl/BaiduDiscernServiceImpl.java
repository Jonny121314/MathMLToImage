package com.hongrant.www.achieve.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hongrant.www.achieve.comm.util.Base64Utils;
import com.hongrant.www.achieve.service.BaiduDiscernService;
import com.hongrant.www.achieve.service.SysConfigManager;

/**
 * baiduAPI图像文字识别
 */
@Service
public class BaiduDiscernServiceImpl implements BaiduDiscernService {

	@Autowired
	private SysConfigManager config;

	// 获取百度识别的token的接口地址
	public final static String access_token_url = "https://aip.baidubce.com/oauth/2.0/token?";

	private static final String POST_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=";

	

	/**
	 * 识别本地图片的文字
	 *
	 * @param path 本地图片地址
	 * @return 识别结果，为json格式
	 * @throws URISyntaxException URI打开异常
	 * @throws IOException        io流异常
	 */
	public String checkFile(String path) throws URISyntaxException, IOException {
		File file = new File(path);
		if (!file.exists()) {
			throw new NullPointerException("图片不存在");
		}
		String image = Base64Utils.ImageToBase64ByLocal(path);
		String param = "image=" + image;
		return post(param);
	}

	/**
	 * @param url 图片url
	 * @return 识别结果，为json格式
	 */
	public String checkUrl(String url) throws IOException, URISyntaxException {
		String param = "url=" + url;
		return post(param);
	}

	/**
	 * 通过传递参数：url和image进行文字识别
	 *
	 * @param param 区分是url还是image识别
	 * @return 识别结果
	 * @throws URISyntaxException URI打开异常
	 * @throws IOException        IO流异常
	 */
	private String post(String param) throws URISyntaxException, IOException {
		CloseableHttpClient httpClient = null;
		try {
			String apiKey = config.getConfigValueByKey("baidu_apiKey");
			String secretKey = config.getConfigValueByKey("baidu_secretKey");
			String postUrlString = POST_URL + getAccessToken(apiKey,secretKey);
			//开始搭建post请求
			httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost();
			URI url = new URI(postUrlString);
			post.setURI(url);
			//设置请求头，请求头必须为application/x-www-form-urlencoded，因为是传递一个很长的字符串，不能分段发送
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			StringEntity entity = new StringEntity(param);
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String str;
				/*读取服务器返回过来的json字符串数据*/
				str = EntityUtils.toString(response.getEntity());
				System.out.println(str);
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	/**
	 * 获取API访问token
	 * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
	 * @param apiKey - 百度云官网获取的 API Key
	 * @param secretKey - 百度云官网获取的 Securet Key
	 * @return assess_token 示例："24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
	 */
	private String getAccessToken(String apiKey, String secretKey) {
		// 获取token地址
		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		String getAccessTokenUrl = authHost
				// 1. grant_type为固定参数
				+ "grant_type=client_credentials"
				// 2. 官网获取的 API Key
				+ "&client_id=" + apiKey
				// 3. 官网获取的 Secret Key
				+ "&client_secret=" + secretKey;
		try {
			URL realUrl = new URL(getAccessTokenUrl);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
			// 定义 BufferedReader输入流来读取URL的响应
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			/** 返回结果示例*/
			System.err.println("result:" + result);
			JSONObject jsonObject = JSONObject.parseObject(result.toString());
			return jsonObject.getString("access_token");
		} catch (Exception e) {
			System.err.printf("获取token失败！");
			e.printStackTrace(System.err);
		}
		return null;
	}

}
