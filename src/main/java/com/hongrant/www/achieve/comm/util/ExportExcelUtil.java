package com.hongrant.www.achieve.comm.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcelUtil {

	/**
	 * 导出Excel
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容，可以修改为list  或者 map
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}

		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(0);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		//声明列对象
		HSSFCell cell = null;

		//创建标题
		for(int i=0;i<title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		//创建内容
		for(int i=0;i<values.length;i++){
			row = sheet.createRow(i + 1);
			for(int j=0;j<values[i].length;j++){
				//将内容按顺序赋给对应的列对象
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		return wb;
	}

	/**
	 * 导出学生成绩Excel
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容，可以修改为list  或者 map
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook getHSSFStudentAchievementWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}

		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(0);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		//声明列对象
		HSSFCell cell = null;

		//创建标题
		for(int i=0;i<title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		//创建内容
		for(int i=0;i<values.length;i++){
			row = sheet.createRow(i + 1);
			for(int j=0;j<values[i].length;j++){
				//将内容按顺序赋给对应的列对象
				//把学号列转为int，把分数类转为double
				if (j==2) {
					row.createCell(j).setCellValue(Integer.parseInt(values[i][j]));
				}else if (j==4||j==5||j==6||j==7||j==8||j==9||j==10||j==11||j==12||j==13) {
					row.createCell(j).setCellValue(Double.parseDouble(values[i][j]));
				} else {
					row.createCell(j).setCellValue(values[i][j]);
				}
			}
		}
		return wb;
	}

	/**
	 * 导出多表头Excel
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容，可以修改为list  或者 map
	 * @param wb HSSFWorkbook对象  
	 * @param rows 代表再多少行进行表头设置
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbookMoreTitile(String sheetName,List<String []> titles,List<Integer> rows,List<String [][]> values, HSSFWorkbook wb){
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFRow row = null;
		//声明列对象
		HSSFCell cell = null;
		Integer count = 0;
		Integer sum = 0;
		for (Integer integer : rows) {

			if (count==0) {
				integer = sum+=integer;
			} else {
				integer = sum+=integer;
			}
			//创建标题
			row = sheet.createRow(integer);
			String [] titile = titles.get(count);
			for (int i = 0; i < titile.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(titile[i]);
				cell.setCellStyle(style);
			}
			//创建内容
			String  [][] value = values.get(count);
			for(int i=0;i<value.length;i++){
				integer = integer+1;
				row = sheet.createRow(integer);
				for(int j=0;j<value[i].length;j++){
					//将内容按顺序赋给对应的列对象
					row.createCell(j).setCellValue(value[i][j]);
				}
			}
			count++;
		}
		return wb;
	}


	/**
	 * 导出当前报表Excel --单科老师概要
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容，可以修改为list  或者 map
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook table1(String sheetName,List<String[]> titles01,List<String[]> values, HSSFWorkbook wb,Integer realClassesNum){
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);//设置单元格宽度
		sheet.setDefaultRowHeight((short) 400);//设置单元高度
		// 第三步，设置单元格格式
		HSSFCellStyle headStyle = wb.createCellStyle(); //表头
		HSSFCellStyle titleStyle = wb.createCellStyle(); //标题
		HSSFCellStyle cellStyle = wb.createCellStyle(); //表格内容

		//表头样式
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		headStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚
		//拿到palette颜色板,转换成rgb颜色
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 218);


		HSSFFont font1 =wb.createFont();//字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font1.setFontHeightInPoints((short)11); //字号
		font1.setFontName("宋体");
		headStyle.setFont(font1);

		//标题样式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font2 =wb.createFont();//字体
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font2.setFontHeightInPoints((short)11); //字号
		font2.setFontName("宋体");
		titleStyle.setFont(font2);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚


		//表格内容样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font3 =wb.createFont();//字体
		font3.setFontHeightInPoints((short)11); //字号
		font3.setFontName("宋体");
		cellStyle.setFont(font2);

		//声明列对象
		HSSFCell cell = null;

		//创建表头
		HSSFRow row1 = sheet.createRow(0);//标题:单元测验-数学-2019/09/09 09:00:00
		row1.setHeightInPoints(20);//设置单元格高度
		int totalCellNum = titles01.get(5).length > titles01.get(6).length ? titles01.get(5).length : titles01.get(6).length;
		CellRangeAddress region = new CellRangeAddress(0, 1, 0 ,totalCellNum-1);//合并标题行
		sheet.addMergedRegion(region);
		cell = row1.createCell(0);
		cell.setCellValue(titles01.get(0)[0]);
		int classesNum =realClassesNum;//班级数量
		cell.setCellStyle(headStyle);

		//人员信息
		int rowCount2 = 3;//下标
		HSSFRow row2 = sheet.createRow(rowCount2);//人员信息标题
		row2.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(1).length;i++){
			cell = row2.createCell(i);
			cell.setCellValue(titles01.get(1)[i]);
			cell.setCellStyle(titleStyle);
		}
		int num = 1;
		for(int i=1;i<=classesNum;i++){//人员信息赋值
			HSSFRow	row0 = sheet.createRow(rowCount2+i);
			row0.setHeightInPoints(20);//设置单元格高度
			for(int j=0;j<titles01.get(1).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数信息
		int rowCount3 =rowCount2+classesNum+2;
		HSSFRow row3 = sheet.createRow(rowCount3);
		row3.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(2).length;i++){
			cell = row3.createCell(i);
			cell.setCellValue(titles01.get(2)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=classesNum;i++){//分数信息赋值
			HSSFRow	row0 = sheet.createRow(rowCount3+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(2).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数详情
		int rowCount4 = rowCount3+classesNum+2;
		HSSFRow row4 = sheet.createRow(rowCount4);
		row4.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(3).length;i++){
			cell = row4.createCell(i);
			cell.setCellValue(titles01.get(3)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=classesNum;i++){//分数详情赋值
			HSSFRow	row0 = sheet.createRow(rowCount4+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(3).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数段信息
		int rowCount5 = rowCount4+classesNum+2;
		HSSFRow row5 = sheet.createRow(rowCount5);
		row5.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(4).length;i++){
			cell = row5.createCell(i);
			cell.setCellValue(titles01.get(4)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=classesNum*2;i++){//分数段赋值
			HSSFRow	row0 = sheet.createRow(rowCount5+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(4).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//大题
		int rowCount6 = rowCount5+classesNum*2+2;
		HSSFRow row6 = sheet.createRow(rowCount6);
		row6.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(5).length;i++){
			cell = row6.createCell(i);
			cell.setCellValue(titles01.get(5)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=classesNum;i++){//大题赋值
			HSSFRow	row0 = sheet.createRow(rowCount6+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(5).length;j++){
				//将内容按顺序赋给对应的列对象
				if (j>=values.get(num).length) {
					cell =row0.createCell(j);
					cell.setCellValue(0);
					cell.setCellStyle(cellStyle);
					continue;
				}
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//排名前50%情况
		int rowCount7 = rowCount6+classesNum+2;
		HSSFRow row7 = sheet.createRow(rowCount7);
		row7.setHeightInPoints(20);//设置单元格高度

		for(int i=0;i<titles01.get(6).length;i++){
			cell = row7.createCell(i);
			cell.setCellValue(titles01.get(6)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		HSSFRow	row8 = sheet.createRow(rowCount7+1);//赋值
		row8.setHeightInPoints(20);
		for (int i = 0; i < titles01.get(6).length; i++) {
			cell = row8.createCell(i);
			cell.setCellValue(values.get(num)[i]);
			cell.setCellStyle(cellStyle);
		}

		return wb;
	}

	public static HSSFWorkbook table2(String sheetName, List<String> titles02, List<String[]> values,
			HSSFWorkbook wb) {
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);//设置单元格宽度
		sheet.setDefaultRowHeight((short) 400);//设置单元高度
		// 第三步，设置单元格格式
		HSSFCellStyle headStyle = wb.createCellStyle(); //表头
		HSSFCellStyle titleStyle = wb.createCellStyle(); //标题
		HSSFCellStyle cellStyle = wb.createCellStyle(); //表格内容

		//表头样式
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		headStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚
		//拿到palette颜色板,转换成rgb颜色
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 218);
		HSSFFont font1 =wb.createFont();//字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font1.setFontHeightInPoints((short)11); //字号
		font1.setFontName("宋体");
		headStyle.setFont(font1);

		//标题样式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font2 =wb.createFont();//字体
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font2.setFontHeightInPoints((short)11); //字号
		font2.setFontName("宋体");
		titleStyle.setFont(font2);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚

		//表格内容样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font3 =wb.createFont();//字体
		font3.setFontHeightInPoints((short)11); //字号
		font3.setFontName("宋体");
		cellStyle.setFont(font2);

		//声明列对象
		HSSFCell cell = null;

		//人员信息
		HSSFRow row1 = sheet.createRow(0);//标题：历史成绩	一班平均分	二班平均分	年级平均分
		row1.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles02.size();i++){
			cell = row1.createCell(i);
			cell.setCellValue(titles02.get(i));
			cell.setCellStyle(titleStyle);
		}
		if (values==null) {
			return wb;
		}
		for(int i=1;i<values.size();i++){//内容赋值
			HSSFRow	row0 = sheet.createRow(i);
			row0.setHeightInPoints(20);//设置单元格高度
			for (int j = 0; j < values.get(i).length; j++) {
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(i)[j]);//第一行是标题
				cell.setCellStyle(cellStyle);
			}

		}
		return wb;
	}

	/**
	 * 导出当前报表Excel --班主任概要
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容，可以修改为list  或者 map
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook table3(String sheetName,List<String[]> titles01,List<String[]> values, HSSFWorkbook wb,int itemNum1,int itemNum2){
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);//设置单元格宽度
		sheet.setDefaultRowHeight((short) 400);//设置单元高度
		// 第三步，设置单元格格式
		HSSFCellStyle headStyle = wb.createCellStyle(); //表头
		HSSFCellStyle titleStyle = wb.createCellStyle(); //标题
		HSSFCellStyle cellStyle = wb.createCellStyle(); //表格内容

		//表头样式
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		headStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚
		//拿到palette颜色板,转换成rgb颜色
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 218);
		HSSFFont font1 =wb.createFont();//字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font1.setFontHeightInPoints((short)11); //字号
		font1.setFontName("宋体");
		headStyle.setFont(font1);

		//标题样式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font2 =wb.createFont();//字体
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font2.setFontHeightInPoints((short)11); //字号
		font2.setFontName("宋体");
		titleStyle.setFont(font2);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚

		//表格内容样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font3 =wb.createFont();//字体
		font3.setFontHeightInPoints((short)11); //字号
		font3.setFontName("宋体");
		cellStyle.setFont(font2);

		//声明列对象
		HSSFCell cell = null;

		//创建表头
		HSSFRow row1 = sheet.createRow(0);//标题:单元测验-数学-2019/09/09 09:00:00
		row1.setHeightInPoints(20);//设置单元格高度
		int totalCellNum = titles01.get(4).length > titles01.get(5).length ? titles01.get(4).length : titles01.get(5).length;
		CellRangeAddress region = new CellRangeAddress(0, 1, 0 ,totalCellNum-1);//合并标题行
		sheet.addMergedRegion(region);
		cell = row1.createCell(0);
		cell.setCellValue(titles01.get(0)[0]);
		cell.setCellStyle(headStyle);

		//人员信息
		int rowCount2 = 3;//下标
		int num = 0;
		HSSFRow row2 = sheet.createRow(rowCount2);//人员信息标题
		row2.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(1).length;i++){
			cell = row2.createCell(i);
			cell.setCellValue(titles01.get(1)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		//人员信息赋值
		HSSFRow	row00 = sheet.createRow(rowCount2+1);
		row00.setHeightInPoints(20);//设置单元格高度
		for(int j=0;j<titles01.get(1).length;j++){
			//将内容按顺序赋给对应的列对象
			cell =row00.createCell(j);
			cell.setCellValue(values.get(num)[j]);
			cell.setCellStyle(cellStyle);
		}
		num++;

		//分数信息
		int rowCount3 =rowCount2+1+2;//6
		HSSFRow row3 = sheet.createRow(rowCount3);
		row3.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(2).length;i++){
			cell = row3.createCell(i);
			cell.setCellValue(titles01.get(2)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=itemNum1;i++){//分数信息赋值
			HSSFRow	row0 = sheet.createRow(rowCount3+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(2).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数详情
		int rowCount4 = rowCount3+itemNum1+2;//12
		HSSFRow row4 = sheet.createRow(rowCount4);
		row4.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(3).length;i++){
			cell = row4.createCell(i);
			cell.setCellValue(titles01.get(3)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=itemNum2;i++){//分数详情赋值
			HSSFRow	row0 = sheet.createRow(rowCount4+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(3).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数段信息
		int rowCount5 = rowCount4+itemNum2+2;//18
		HSSFRow row5 = sheet.createRow(rowCount5);
		row5.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(4).length;i++){
			cell = row5.createCell(i);
			cell.setCellValue(values.get(num)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=2;i++){//分数段赋值
			HSSFRow	row0 = sheet.createRow(rowCount5+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(4).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//排名前50%情况
		int rowCount6 = rowCount5+2+2;//22
		HSSFRow row7 = sheet.createRow(rowCount6);
		row7.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(5).length;i++){
			cell = row7.createCell(i);
			cell.setCellValue(titles01.get(5)[i]);
			cell.setCellStyle(titleStyle);
		}

		num++;
		HSSFRow	row8 = sheet.createRow(rowCount6+1);//赋值
		row8.setHeightInPoints(20);
		for (int i = 0; i < titles01.get(5).length; i++) {
			cell = row8.createCell(i);
			cell.setCellValue(values.get(num)[i]);
			cell.setCellStyle(cellStyle);
		}

		return wb;
	}

	/**
	 * 导出当前报表Excel --年级组长概要
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容，可以修改为list  或者 map
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook table4(String sheetName,List<String[]> titles01,List<String[]> values, HSSFWorkbook wb, Integer realClassesNum){
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);//设置单元格宽度
		sheet.setDefaultRowHeight((short) 400);//设置单元高度
		// 第三步，设置单元格格式
		HSSFCellStyle headStyle = wb.createCellStyle(); //表头
		HSSFCellStyle titleStyle = wb.createCellStyle(); //标题
		HSSFCellStyle cellStyle = wb.createCellStyle(); //表格内容

		//表头样式
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		headStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚
		//拿到palette颜色板,转换成rgb颜色
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 218);
		HSSFFont font1 =wb.createFont();//字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font1.setFontHeightInPoints((short)11); //字号
		font1.setFontName("宋体");
		headStyle.setFont(font1);

		//标题样式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font2 =wb.createFont();//字体
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font2.setFontHeightInPoints((short)11); //字号
		font2.setFontName("宋体");
		titleStyle.setFont(font2);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚

		//表格内容样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font3 =wb.createFont();//字体
		font3.setFontHeightInPoints((short)11); //字号
		font3.setFontName("宋体");
		cellStyle.setFont(font2);

		//声明列对象
		HSSFCell cell = null;

		//创建表头
		HSSFRow row1 = sheet.createRow(0);//标题:单元测验-数学-2019/09/09 09:00:00
		row1.setHeightInPoints(20);//设置单元格高度
		int totalCellNum = titles01.get(5).length > titles01.get(6).length ? titles01.get(5).length : titles01.get(6).length;
		CellRangeAddress region = new CellRangeAddress(0, 1, 0 ,totalCellNum-1);//合并标题行
		sheet.addMergedRegion(region);
		cell = row1.createCell(0);
		cell.setCellValue(titles01.get(0)[0]);
		cell.setCellStyle(headStyle);

		//分数信息
		int num = 0;
		int rowCount3 = 3;//下标
		HSSFRow row3 = sheet.createRow(rowCount3);
		row3.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(1).length;i++){
			cell = row3.createCell(i);
			cell.setCellValue(titles01.get(1)[i]);
			cell.setCellStyle(titleStyle);
		}

		//分数信息赋值
		HSSFRow	row0 = sheet.createRow(rowCount3+1);//4
		num++;
		row0.setHeightInPoints(20);
		for(int j=0;j<titles01.get(1).length;j++){
			//将内容按顺序赋给对应的列对象
			cell =row0.createCell(j);
			cell.setCellValue(values.get(num)[j]);
			cell.setCellStyle(cellStyle);
		}

		num++;
		//分数详情
		int rowCount4 = rowCount3+3;//6
		HSSFRow row4 = sheet.createRow(rowCount4);
		row4.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(2).length;i++){
			cell = row4.createCell(i);
			cell.setCellValue(titles01.get(2)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=realClassesNum+1;i++){//分数详情赋值
			HSSFRow	row00 = sheet.createRow(rowCount4+i);//8
			row00.setHeightInPoints(20);
			for(int j=0;j<titles01.get(2).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row00.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数段信息
		int rowCount5 = rowCount4+realClassesNum+1+2;//15
		HSSFRow row5 = sheet.createRow(rowCount5);
		row5.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(3).length;i++){
			cell = row5.createCell(i);
			cell.setCellValue(titles01.get(3)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=2;i++){//分数段赋值
			HSSFRow	row00 = sheet.createRow(rowCount5+i);
			row00.setHeightInPoints(20);
			for(int j=0;j<titles01.get(3).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row00.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//大题
		int rowCount6 = rowCount5+2+2;//19
		HSSFRow row6 = sheet.createRow(rowCount6);
		row6.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(4).length;i++){
			cell = row6.createCell(i);
			cell.setCellValue(titles01.get(4)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		//大题赋值
		HSSFRow	row00 = sheet.createRow(rowCount6+1);
		row00.setHeightInPoints(20);
		for(int j=0;j<titles01.get(4).length;j++){
			//将内容按顺序赋给对应的列对象
			if (j>=values.get(num).length) {
				cell =row00.createCell(j);
				cell.setCellValue(0);
				cell.setCellStyle(cellStyle);
				continue;
			}
			//将内容按顺序赋给对应的列对象
			cell =row00.createCell(j);
			cell.setCellValue(values.get(num)[j]);
			cell.setCellStyle(cellStyle);
		}
		num++;

		//各班平均分
		int rowCount7 = rowCount6+1+2;//22
		HSSFRow row7 = sheet.createRow(rowCount7);
		row7.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(5).length;i++){
			cell = row7.createCell(i);
			cell.setCellValue(titles01.get(5)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		//各班平均分
		HSSFRow	row01 = sheet.createRow(rowCount7+1);
		row01.setHeightInPoints(20);
		for(int j=0;j<titles01.get(5).length;j++){
			//将内容按顺序赋给对应的列对象
			cell =row01.createCell(j);
			cell.setCellValue(values.get(num)[j]);
			cell.setCellStyle(cellStyle);
		}

		num++;
		//排名前50%情况
		int rowCount8 = rowCount7+1+2;//25
		HSSFRow row8 = sheet.createRow(rowCount8);
		row8.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(6).length;i++){
			cell = row8.createCell(i);
			cell.setCellValue(titles01.get(6)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for (int j = 1; j <=3; j++) {
			HSSFRow	row9 = sheet.createRow(rowCount8+j);//大题赋值
			row9.setHeightInPoints(20);
			for (int i = 0; i < titles01.get(6).length; i++) {
				cell = row9.createCell(i);
				cell.setCellValue(values.get(num)[i]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		return wb;
	}

	/**
	 * 导出当前报表Excel --管理员视角期中、期末、统考概要
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容，可以修改为list  或者 map
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook table5(String sheetName,List<String[]> titles01,List<String[]> values, HSSFWorkbook wb,int itemNum1,int itemNum2){
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);//设置单元格宽度
		sheet.setDefaultRowHeight((short) 400);//设置单元高度
		// 第三步，设置单元格格式
		HSSFCellStyle headStyle = wb.createCellStyle(); //表头
		HSSFCellStyle titleStyle = wb.createCellStyle(); //标题
		HSSFCellStyle cellStyle = wb.createCellStyle(); //表格内容

		//表头样式
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		headStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚
		//拿到palette颜色板,转换成rgb颜色
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 218);
		HSSFFont font1 =wb.createFont();//字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font1.setFontHeightInPoints((short)11); //字号
		font1.setFontName("宋体");
		headStyle.setFont(font1);

		//标题样式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font2 =wb.createFont();//字体
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		font2.setFontHeightInPoints((short)11); //字号
		font2.setFontName("宋体");
		titleStyle.setFont(font2);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//添加前景色,内容看的清楚

		//表格内容样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		HSSFFont font3 =wb.createFont();//字体
		font3.setFontHeightInPoints((short)11); //字号
		font3.setFontName("宋体");
		cellStyle.setFont(font2);

		//声明列对象
		HSSFCell cell = null;

		//创建表头
		HSSFRow row1 = sheet.createRow(0);//标题:单元测验-数学-2019/09/09 09:00:00
		row1.setHeightInPoints(20);//设置单元格高度
		int totalCellNum = titles01.get(4).length > titles01.get(5).length ? titles01.get(4).length : titles01.get(5).length;
		CellRangeAddress region = new CellRangeAddress(0, 1, 0 ,totalCellNum-1);//合并标题行
		sheet.addMergedRegion(region);
		cell = row1.createCell(0);
		cell.setCellValue(titles01.get(0)[0]);
		cell.setCellStyle(headStyle);

		//分数信息
		int rowCount2 =3;
		int num = 0;
		HSSFRow row2 = sheet.createRow(rowCount2);
		row2.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(1).length;i++){
			cell = row2.createCell(i);
			cell.setCellValue(titles01.get(1)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=1;i++){//分数信息赋值7
			HSSFRow	row0 = sheet.createRow(rowCount2+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(1).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数详情
		int rowCount3 = rowCount2+1+2;//9
		HSSFRow row3 = sheet.createRow(rowCount3);
		row3.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(2).length;i++){
			cell = row3.createCell(i);
			cell.setCellValue(titles01.get(2)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=itemNum1;i++){//分数详情赋值
			HSSFRow	row0 = sheet.createRow(rowCount3+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(2).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//单科情况
		int rowCount4 = rowCount3+itemNum1+2;
		HSSFRow row4 = sheet.createRow(rowCount4);
		row4.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(3).length;i++){
			cell = row4.createCell(i);
			cell.setCellValue(titles01.get(3)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=itemNum2;i++){//单科情况赋值
			HSSFRow	row0 = sheet.createRow(rowCount4+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(3).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//分数段信息
		int rowCount5 = rowCount4+itemNum2+2;
		HSSFRow row5 = sheet.createRow(rowCount5);
		row5.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<titles01.get(4).length;i++){
			cell = row5.createCell(i);
			cell.setCellValue(values.get(num)[i]);
			cell.setCellStyle(titleStyle);
		}
		num++;
		for(int i=1;i<=2;i++){//分数段赋值
			HSSFRow	row0 = sheet.createRow(rowCount5+i);
			row0.setHeightInPoints(20);
			for(int j=0;j<titles01.get(4).length;j++){
				//将内容按顺序赋给对应的列对象
				cell =row0.createCell(j);
				cell.setCellValue(values.get(num)[j]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}

		//各班平均分
		int rowCount6 = rowCount5+2+2;
		HSSFRow row7 = sheet.createRow(rowCount6);
		row7.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<itemNum1;i++){
			cell = row7.createCell(i);
			cell.setCellValue(values.get(num)[i]);
			cell.setCellStyle(titleStyle);
		}

		num++;
		HSSFRow	row8 = sheet.createRow(rowCount6+1);//赋值
		row8.setHeightInPoints(20);
		for (int i = 0; i < itemNum1; i++) {
			cell = row8.createCell(i);
			cell.setCellValue(values.get(num)[i]);
			cell.setCellStyle(cellStyle);
		}
		num++;

		//排名前50%情况
		int rowCount7 = rowCount6+1+2;
		HSSFRow row9 = sheet.createRow(rowCount7);
		row9.setHeightInPoints(20);//设置单元格高度
		for(int i=0;i<itemNum1;i++){
			cell = row9.createCell(i);
			cell.setCellValue(values.get(num)[i]);
			cell.setCellStyle(titleStyle);
		}

		num++;
		for(int j=1;j<=3;j++){//分数段赋值
			HSSFRow	row10 = sheet.createRow(rowCount7+j);//赋值
			row10.setHeightInPoints(20);
			for (int i = 0; i < itemNum1; i++) {
				cell = row10.createCell(i);
				cell.setCellValue(values.get(num)[i]);
				cell.setCellStyle(cellStyle);
			}
			num++;
		}
		return wb;
	}
}
