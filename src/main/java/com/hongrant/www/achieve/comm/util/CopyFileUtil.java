package com.hongrant.www.achieve.comm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 复制文件
 */
public class CopyFileUtil {
	
	/**
	 * 
	 * 复制单个文件
	 * 
	 * @param oldPath String 原文件路径 如：c:/fqf.txt
	 * @param newPath String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (!oldfile.exists()) {
				oldfile.createNewFile();//不存在，则创建文件
			}
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
			//System.out.println("文件已存入新地址");
		} catch (Exception e) {
			//System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 生成6位或10位随机数 param codeLength(多少位)
	 * @param codeLength 参数
	 * @return  String

	 */
	public static String createCode(int codeLength) {
		String code = "";
		for (int i = 0; i < codeLength; i++) {
			code += (int) (Math.random() * 9);
		}
		return code;
	}
}
