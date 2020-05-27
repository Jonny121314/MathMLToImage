package com.hongrant.www.achieve.service.impl;

import java.io.File;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hongrant.www.achieve.comm.util.CmdInterceptor;
import com.hongrant.www.achieve.comm.util.JsonResult;
import com.hongrant.www.achieve.service.HtmlToPdfService;

@Service
public class HtmlToPdfServiceImpl implements HtmlToPdfService{

	// wkhtmltopdf在系统中的路径
	@Value("${wkhtmltopdfTool}")
	private String toPdfTool;

	@Override
	public JsonResult<Object> HtmlToPdf(Map<String, Object> params) {
		String htmlUrl = MapUtils.getString(params, "url");
		//String htmlUrl = "C:\\Users\\xiaomi\\Desktop\\apache-tomcat-8.5.41\\webapps\\htmlFile\\20200331174459416712.html";
		File file = new File(htmlUrl);
		String htmlname = file.getName();
		htmlname = htmlname.substring(0,htmlname.lastIndexOf("."));
		String pdfSavePath = System.getProperty("catalina.home")+"\\webapps\\pdfFile\\"+ htmlname+".pdf";
		Boolean result = convert(htmlUrl, pdfSavePath);
		if (result) {
			return new JsonResult<Object>("试卷html转换pdf成功");
		}
			return new JsonResult<Object>("试卷html转换pdf失败");
	}

	/**
	 * html转pdf
	 *
	 * @param srcPath: html路径，可以是硬盘上的路径，也可以是网络路径
	 * @param destPath: pdf保存路径
	 * @return 转换成功返回true
	 */
	public boolean convert(String srcPath, String pdfSavePath) {
		File file = new File(pdfSavePath);
		File parent = file.getParentFile();
		// 如果pdf保存路径不存在，则创建路径
		if (!parent.exists()) {
			parent.mkdirs();
		}
		StringBuilder cmd = new StringBuilder();
		/*        if (System.getProperty("os.name").indexOf("Windows") == -1) {
            // 非windows 系统
            toPdfTool = Consts.WEB.CONVERSION_PLUGSTOOL_PATH_LINUX;
        }*/
		cmd.append(toPdfTool);
		cmd.append(" ");
		cmd.append(" \"");
		cmd.append(srcPath);
		cmd.append("\" ");
		cmd.append(" ");
		cmd.append(pdfSavePath);

		System.out.println(cmd.toString());
		boolean result = true;
		try {
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
}
