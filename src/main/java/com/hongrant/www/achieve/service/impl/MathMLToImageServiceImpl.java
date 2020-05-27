package com.hongrant.www.achieve.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hongrant.www.achieve.comm.util.DeleteFileUtil;
import com.hongrant.www.achieve.comm.util.CmdInterceptor;
import com.hongrant.www.achieve.service.MathMLToImageService;

/**
 *MathML转换成图片
 *
 */
@Service
public class MathMLToImageServiceImpl implements MathMLToImageService {

	// jeuclid启动脚本在系统中的路径
	@Value("${jeuclidTool}")
	private String jeuclidTool;

	@Override
	public void MathMLToImage(Map<String, Object> params, HttpServletResponse response) {
		//String key = MapUtils.getString(params, "key");
		String mathml = MapUtils.getString(params, "mml");
		String mathmlTxtPath = null;
		String mathmlImageSavePath = null;
		try {
			mathmlTxtPath = System.getProperty("catalina.home")+"\\webapps\\mathmlTxt\\"+ UUID.randomUUID().toString()+".txt";
			saveAsFileWriter(mathml,mathmlTxtPath);//把mathml字符串存入txt文件
			File mathmlTxtFile = new File(mathmlTxtPath);
			String mathmlTxtFileName = mathmlTxtFile.getName();
			mathmlTxtFileName = mathmlTxtFileName.substring(0, mathmlTxtFileName.lastIndexOf("."));

			mathmlImageSavePath = System.getProperty("catalina.home")+"\\webapps\\mathmlImage\\"+ mathmlTxtFileName+".png";
			convert(mathmlTxtPath, mathmlImageSavePath,response);//MathML转换成图片
			//html页面响应图片
			File file = new File(mathmlImageSavePath);
			if (file.exists()) {
				downLoad(response, file);
			}
			DeleteFileUtil.deleteFile(mathmlTxtPath);//删除本地txt
			DeleteFileUtil.deleteFile(mathmlImageSavePath);//删除本地图片
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * html转pdf
	 *
	 * @param srcPath: html路径，可以是硬盘上的路径，也可以是网络路径
	 * @param destPath: pdf保存路径
	 * @return 转换成功返回true
	 */
	public boolean convert(String mathml, String mathmlImageSavePath, HttpServletResponse response) {
		File file = new File(mathmlImageSavePath);
		File parent = file.getParentFile();
		// 如果pdf保存路径不存在，则创建路径
		if (!parent.exists()) {
			parent.mkdirs();
		}
		StringBuilder cmd = new StringBuilder();

		cmd.append(jeuclidTool);
		cmd.append(" ");
		cmd.append(" \"");
		cmd.append(mathml);
		cmd.append("\" ");
		cmd.append(" ");
		cmd.append(mathmlImageSavePath);

		System.out.println(cmd.toString());
		boolean result = true;
		try {
			//生成图片
			Process proc = Runtime.getRuntime().exec(cmd.toString());
			CmdInterceptor error = new CmdInterceptor(proc.getErrorStream());
			CmdInterceptor output = new CmdInterceptor(proc.getInputStream());
			error.start();
			output.start();
			proc.waitFor();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 下载响应本地图片到页面
	 * @param response
	 * @param file
	 */
	private void downLoad(HttpServletResponse response, File file) {
		try {
			response.setContentType("image/png");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(file.getName().getBytes(), "ISO8859-1"));
			response.addHeader("Access-Control-Expose-Headers", "Content-Type,Content-Disposition");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void saveAsFileWriter(String content,String mathmlTxtPath) {
		try {
			File file = new File(mathmlTxtPath);
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
	        BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(  
	                new FileOutputStream(file), "utf-8"));  
	        writer.write(content);
	        writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
		}
	}
}
