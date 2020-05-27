package com.hongrant.www.achieve.comm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 根据路径从服务器下载文件到指定路径下
 * @author zjm
 */
public class DownloadFile{

	public static void download(String urlString, String filename,String savePath) throws Exception {  
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection urlConnection = url.openConnection();
		// http的连接类
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
		//设置超时
		httpURLConnection.setConnectTimeout(1000*5);
		// 设置字符编码
        //设置请求方式，默认是GET
        httpURLConnection.setRequestMethod("GET");
        // 设置字符编码
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        httpURLConnection.connect();
		int fileLength = httpURLConnection.getContentLength();
		System.out.println("训练集大小：" + fileLength);
		// 输入流
		InputStream is = null;
		if (httpURLConnection.getResponseCode() == 200) {
			is = httpURLConnection.getInputStream();
		} else {
			is = httpURLConnection.getErrorStream();
		}
		 BufferedInputStream bin = new BufferedInputStream(is);
		// 2K的数据缓冲
		byte[] bs = new byte[2048];
		// 读取到的数据长度
		int size = 0;
		int len = 0;
		// 输出的文件流
		File sf=new File(savePath);
		if(!sf.exists()){
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
		// 开始读取
		while ((size = bin.read(bs)) != -1){
			len += size;
			os.write(bs, 0, size );
			System.out.println("训练集从云端下载了-------> " + len * 100 / fileLength + "%\n");
		}  
		// 完毕，关闭所有链接  
		os.close();
		bin.close();
	} 
}
