package com.hongrant.www.achieve.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongrant.www.achieve.comm.util.JsonResult;
import com.hongrant.www.achieve.comm.util.NumberCastUtil;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.util.StringUtil;
import com.hongrant.www.achieve.comm.util.CopyFileUtil;
import com.hongrant.www.achieve.comm.util.DateUtils;
import com.hongrant.www.achieve.mapper.GenerateHtmlMapper;
import com.hongrant.www.achieve.service.GenerateHtmlService;

@Service
public class GenerateHtmlServiceImpl implements GenerateHtmlService {

	@Autowired
	private GenerateHtmlMapper generateHtmlMapper;

	/**
	 * 生成空白试卷
	 */
	@Override
	public JsonResult<Object> GenerateHtml(Map<String, Object> params) {

		//用于存储html字符串
		StringBuilder stringHtml = new StringBuilder();
		PrintStream printStream =null;
		try{
			String savePath=System.getProperty("catalina.home")+"\\webapps\\htmlFile";
			String htmlSavePath = getNewHtml(savePath);

			//打开文件
			printStream = new PrintStream(new FileOutputStream(htmlSavePath));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** 添加head meta*/
		String head = getHead();

		/** 添加试卷名称*/
		Map<String, Object> titleParam = new HashMap<String, Object>();
		String headTitle = getHeadTitle(titleParam);
		String paper_detail_hd = GetPaperDetailHd(headTitle);

		String paper_list_item = GetPaperListItem(params);

		//输入HTML文件内容
		stringHtml.append("<!DOCTYPE html>\r\n<html lang=\"zh-CN\">\r\n");//html开始
		stringHtml.append(head);//头部内容
		stringHtml.append("<body style=\"zoom: 1;\">\r\n");//body开始
		stringHtml.append(paper_detail_hd);
		stringHtml.append("    <div class=\"paper_detail_bd\">\r\n");//试题开始

		stringHtml.append(paper_list_item);//小题题目

		stringHtml.append("    </div>\r\n");//试题结束
		stringHtml.append("</body>\r\n</html>\r\n");//body结束，html结束
		try{
			//将HTML文件内容写入文件中
			printStream.println(stringHtml.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult<Object>("试卷生成成功");
	}

	/**
	 * 复制模板html到新的html
	 * @param modelHtml 模板地址
	 * @param savePath 保存文件夹
	 * @return
	 */
	private String getHeadTitle(Map<String, Object> params) {
		//		Integer gradeId = MapUtils.getInteger(params, "grade");
		//		Integer subjectId = MapUtils.getInteger(params, "subject");
		//		String subject = generateHtmlMapper.getSubjectIdByName(subjectId);
		//		String grade = generateHtmlMapper.getGradeIdByName(gradeId);
		String subject = "数学";
		String grade = "高中";
		String nowDate = DateUtils.getCurrentDate(DateUtils.DATE_FORMAT);
		String paperTitle = nowDate + grade + subject + "试卷";
		return paperTitle;
	}

	/**
	 * 复制模板html到新的html
	 * @param modelHtml 模板地址
	 * @param savePath 保存文件夹
	 * @return
	 * @throws IOException 
	 */
	private String getNewHtml(String savePath) throws IOException {
		File htmlFile = new File(savePath);
		if (!htmlFile.exists()) {
			htmlFile.mkdirs();
		}
		String today = DateUtils.getCurrentDate("yyyyMMddHHmmss");
		String random= CopyFileUtil.createCode(6);
		String htmlNum=today+random;//生成订单号
		String htmlSavePath = savePath +"//"+ htmlNum +".html";
		File oldfile = new File(htmlSavePath);
		if (!oldfile.exists()) {
			oldfile.createNewFile();//不存在，则创建文件
		}
		return htmlSavePath;
	}


	/**
	 * body的主体paper_list_item
	 * @param params 
	 * @return
	 */
	private String GetPaperListItem(Map<String, Object> params) {
		StringBuilder stringHtml = new StringBuilder();

		String questionIdString = MapUtils.getString(params, "question");
		String[] questionIdArray = questionIdString.split(",");
		List<String> questionIdList = Arrays.asList(questionIdArray);
		List<Map<String, Object>> topicList = generateHtmlMapper.getTopicListByList(questionIdList);//题型数量
		if (topicList.size()!=0) {
			for (int i = 0; i < topicList.size(); i++) {
				stringHtml.append("        <div class=\"paper_list_item\">");//大题开始
				String upcaseTitle = "            <p>UpcaseNumber、TopicName（共QuestionSize题，共0分）</p>";
				String upcaseNumber = NumberCastUtil.ToConvtZH(i+1);//阿拉伯数字转大写
				upcaseTitle = upcaseTitle.replace("UpcaseNumber", upcaseNumber);//1
				Map<String, Object> topicMap = topicList.get(i);
				String topicName = MapUtils.getString(topicMap, "topicName");
				upcaseTitle = upcaseTitle.replace("TopicName", topicName);//2
				Integer basicTopicId = MapUtils.getInteger(topicMap, "topicId");
				List<Map<String, Object>> questionList = generateHtmlMapper.getQuestionListByListAndTopicId(questionIdList,basicTopicId);//题型对应的小题
				upcaseTitle = upcaseTitle.replace("QuestionSize", questionList.size()+"");//3
				stringHtml.append(upcaseTitle);//大题标题;一、单选题（共3题，共0分）
				for (int j = 0; j < questionList.size(); j++) {//遍历小题
					String paper_list_item = "            <ul>\r\n" + 
							"                <li>\r\n" + 
							"                    <div class=\"paper_con\">\r\n" + 
							"                        <div class=\"paper_title\">\r\n" + 
							"                            <span>SmallNum.&nbsp;</span>\r\n" + 
							"                            <div class=\"title-right\">QuestionTitle</div>\r\n" + 
							"                        </div>\r\n" + 
							"                        <div class=\"paper_title_options\">\r\n" + 
							"SmallQuestionOptions"+
							"                        </div>                     \r\n" + 
							"SmallQuestionSub"+
							"                    </div>\r\n" + 
							"                </li>\r\n" + 
							"            </ul>\r\n";
					Map<String, Object> questionMap = questionList.get(j);
					paper_list_item = paper_list_item.replace("SmallNum", (j+1)+"");
					paper_list_item = paper_list_item.replace("QuestionTitle", questionMap.get("title").toString());
					String options = MapUtils.getString(questionMap, "options");
					String subQuestions = MapUtils.getString(questionMap, "subQuestions");
					if (StringUtil.isEmpty(options)) {
						paper_list_item = paper_list_item.replace("SmallQuestionOptions", "");
					} else {
						StringBuilder smallQuestionOptions = new StringBuilder();
						List<Map<String, Object>> optionsList = (List<Map<String, Object>>) JSONArray.parse(options);
						Map<String, Object> optionsMap = optionsList.get(0);
						for (int k = 0; k < optionsMap.size(); k++) {
							String option = "                            <div style=\"width: 224px;\"><span>EnglishNum.</span> <span>OptionContext</span></div>\r\n";
							String englishNum = getEnglishNum(k);
							String optionContext = MapUtils.getString(optionsMap, englishNum);
							option = option.replace("EnglishNum", englishNum);
							option = option.replace("OptionContext", optionContext);
							smallQuestionOptions.append(option);
						}
						paper_list_item = paper_list_item.replace("SmallQuestionOptions",smallQuestionOptions);
					}

					if (StringUtil.isEmpty(subQuestions)) {
						paper_list_item = paper_list_item.replace("SmallQuestionSub", "");
					} else {
						StringBuilder smallQuestionSub = new StringBuilder();
						List<Map<String, Object>> subList = (List<Map<String, Object>>) JSONArray.parse(subQuestions);
						for (int k = 0; k < subList.size(); k++) {
							String sub = "                        <div class=\"paper_sub_title\"><span class=\"sort\">SubTitleNum</span>Sub_Question_Title</div>";
							Map<String, Object> subMap = subList.get(k);
							Integer titleNum = MapUtils.getInteger(subMap, "question_sort");
							String subTitleNum = "("+titleNum+")";
							sub = sub.replace("SubTitleNum", subTitleNum);
							String subContext = MapUtils.getString(subMap, "title");
							sub = sub.replace("Sub_Question_Title", subContext);
							smallQuestionSub.append(sub);
						}
						paper_list_item = paper_list_item.replace("SmallQuestionSub",smallQuestionSub);
					}
					stringHtml.append(paper_list_item);
				}
				stringHtml.append("        </div>\r\n");//大题结束
			}
		}
		return stringHtml.toString();
	}

	private String getEnglishNum(int k) {
		String englishNum = null;
		if (k==0) {
			englishNum = "A";
		}else if (k==1) {
			englishNum = "B";
		}else if (k==2) {
			englishNum = "C";
		}else if (k==3) {
			englishNum = "D";
		}else if (k==4) {
			englishNum = "E";
		}else if (k==5) {
			englishNum = "F";
		}else {
			englishNum = "G";
		}
		return englishNum;
	}

	/**
	 * body的头部paper_detail_hd
	 * @return
	 */
	private String GetPaperDetailHd(String headTitle) {
		String paper_detail_hd = "    <div class=\"paper_detail_hd\">\r\n" + 
				"        <div class=\"paper_hd_left\">\r\n" + 
				"            <h3>HeadTitle</h3>\r\n" + 
				"        </div>\r\n" + 
				"    </div>\r\n";
		paper_detail_hd = paper_detail_hd.replace("HeadTitle", headTitle);

		return paper_detail_hd;
	}

	/**
	 * head
	 * @return
	 */
	private String getHead() {
		String headMeta = "<head>\r\n" + 
				"	<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\"/>\r\n" + 
				"	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"/>    \r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/comm.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/comm_page.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/bootstrap.min.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/iconfont.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/version_book_links.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/auth.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/collect_paper.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/paper_detail.css\">\r\n" + 
				"    <link media=\"all\" type=\"text/css\" rel=\"stylesheet\" href=\"./css/download_dialog.css\">\r\n" + 
				"</head>\r\n";

		return headMeta;
	}


	/**
	 * 学生试卷：空白试卷+答案末尾
	 */
	@Override
	public JsonResult<Object> GenerateStudentHtml(Map<String, Object> params) {
		//用于存储html字符串
		StringBuilder stringHtml = new StringBuilder();
		PrintStream printStream =null;
		try{
			String savePath=System.getProperty("catalina.home")+"\\webapps\\htmlFile";
			String htmlSavePath = getNewHtml(savePath);

			//打开文件
			printStream = new PrintStream(new FileOutputStream(htmlSavePath));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** 添加head meta*/
		String head = getHead();

		/** 添加试卷名称*/
		Map<String, Object> titleParam = new HashMap<String, Object>();
		String headTitle = getHeadTitle(titleParam);
		String paper_detail_hd = GetPaperDetailHd(headTitle);

		String paper_list_item = GetStudentPaperListItem(params);

		//输入HTML文件内容
		stringHtml.append("<!DOCTYPE html>\r\n<html lang=\"zh-CN\">\r\n");//html开始
		stringHtml.append(head);//头部内容
		stringHtml.append("<body style=\"zoom: 1;\">\r\n");//body开始
		stringHtml.append(paper_detail_hd);
		stringHtml.append("    <div class=\"paper_detail_bd\">\r\n");//试题开始

		stringHtml.append(paper_list_item);//小题题目

		stringHtml.append("    </div>\r\n");//试题结束
		stringHtml.append("</body>\r\n</html>\r\n");//body结束，html结束
		try{
			//将HTML文件内容写入文件中
			printStream.println(stringHtml.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult<Object>("学生试卷生成成功");
	}

	/**
	 * 学生试卷：空白试卷+答案末尾
	 */
	private String GetStudentPaperListItem(Map<String, Object> params) {
		StringBuilder stringHtml = new StringBuilder();

		String questionIdString = MapUtils.getString(params, "question");
		String[] questionIdArray = questionIdString.split(",");
		List<String> questionIdList = Arrays.asList(questionIdArray);
		List<Map<String, Object>> topicList = generateHtmlMapper.getTopicListByList(questionIdList);//题型数量
		if (topicList.size()!=0) {
			for (int i = 0; i < topicList.size(); i++) {
				stringHtml.append("        <div class=\"paper_list_item\">\r\n");//大题开始
				String upcaseTitle = "            <p>UpcaseNumber、TopicName（共QuestionSize题，共0分）</p>\r\n";
				String upcaseNumber = NumberCastUtil.ToConvtZH(i+1);//阿拉伯数字转大写
				upcaseTitle = upcaseTitle.replace("UpcaseNumber", upcaseNumber);//1
				Map<String, Object> topicMap = topicList.get(i);
				String topicName = MapUtils.getString(topicMap, "topicName");
				upcaseTitle = upcaseTitle.replace("TopicName", topicName);//2
				Integer basicTopicId = MapUtils.getInteger(topicMap, "topicId");
				List<Map<String, Object>> questionList = generateHtmlMapper.getQuestionListByListAndTopicId(questionIdList,basicTopicId);//题型对应的小题
				upcaseTitle = upcaseTitle.replace("QuestionSize", questionList.size()+"");//3
				stringHtml.append(upcaseTitle);//大题标题;一、单选题（共3题，共0分）
				for (int j = 0; j < questionList.size(); j++) {//遍历小题
					String paper_list_item = "            <ul>\r\n" + 
							"                <li>\r\n" + 
							"                    <div class=\"paper_con\">\r\n" + 
							"                        <div class=\"paper_title\">\r\n" + 
							"                            <span>SmallNum.&nbsp;</span>\r\n" + 
							"                            <div class=\"title-right\">QuestionTitle</div>\r\n" + 
							"                        </div>\r\n" + 
							"                        <div class=\"paper_title_options\">\r\n" + 
							"SmallQuestionOptions"+
							"                        </div>                     \r\n" + 
							"SmallQuestionSub"+
							"                    </div>\r\n" + 
							"                </li>\r\n" + 
							"            </ul>\r\n";
					Map<String, Object> questionMap = questionList.get(j);
					paper_list_item = paper_list_item.replace("SmallNum", (j+1)+"");
					paper_list_item = paper_list_item.replace("QuestionTitle", questionMap.get("title").toString());
					String options = MapUtils.getString(questionMap, "options");
					String subQuestions = MapUtils.getString(questionMap, "subQuestions");
					if (StringUtil.isEmpty(options)) {
						paper_list_item = paper_list_item.replace("SmallQuestionOptions", "");
					} else {
						StringBuilder smallQuestionOptions = new StringBuilder();
						List<Map<String, Object>> optionsList = (List<Map<String, Object>>) JSONArray.parse(options);
						Map<String, Object> optionsMap = optionsList.get(0);
						for (int k = 0; k < optionsMap.size(); k++) {
							String option = "                            <div style=\"width: 224px;\"><span>EnglishNum.</span> <span>OptionContext</span></div>\r\n";
							String englishNum = getEnglishNum(k);
							String optionContext = MapUtils.getString(optionsMap, englishNum);
							option = option.replace("EnglishNum", englishNum);
							option = option.replace("OptionContext", optionContext);
							smallQuestionOptions.append(option);
						}
						paper_list_item = paper_list_item.replace("SmallQuestionOptions",smallQuestionOptions);
					}

					if (StringUtil.isEmpty(subQuestions)) {
						paper_list_item = paper_list_item.replace("SmallQuestionSub", "");
					} else {
						StringBuilder smallQuestionSub = new StringBuilder();
						List<Map<String, Object>> subList = (List<Map<String, Object>>) JSONArray.parse(subQuestions);
						for (int k = 0; k < subList.size(); k++) {
							String sub = "                        <div class=\"paper_sub_title\"><span class=\"sort\">SubTitleNum</span>Sub_Question_Title</div>\r\n";
							Map<String, Object> subMap = subList.get(k);
							Integer titleNum = MapUtils.getInteger(subMap, "question_sort");
							String subTitleNum = "("+titleNum+")";
							sub = sub.replace("SubTitleNum", subTitleNum);
							String subContext = MapUtils.getString(subMap, "title");
							sub = sub.replace("Sub_Question_Title", subContext);
							smallQuestionSub.append(sub);
						}
						paper_list_item = paper_list_item.replace("SmallQuestionSub",smallQuestionSub);
					}
					stringHtml.append(paper_list_item);
				}
				stringHtml.append("        </div>\r\n");//大题结束
			}
		}
		
		//末尾答案
		if (topicList.size()!=0) {
			for (int i = 0; i < topicList.size(); i++) {
				stringHtml.append("        <div class=\"paper_list_item\">\r\n");//大题开始
				String upcaseTitle = "            <p>UpcaseNumber、TopicName答案（共QuestionSize题）</p>\r\n";
				String upcaseNumber = NumberCastUtil.ToConvtZH(i+1);//阿拉伯数字转大写
				upcaseTitle = upcaseTitle.replace("UpcaseNumber", upcaseNumber);//1
				Map<String, Object> topicMap = topicList.get(i);
				String topicName = MapUtils.getString(topicMap, "topicName");
				upcaseTitle = upcaseTitle.replace("TopicName", topicName);//2
				Integer basicTopicId = MapUtils.getInteger(topicMap, "topicId");
				List<Map<String, Object>> questionList = generateHtmlMapper.getQuestionListByListAndTopicId(questionIdList,basicTopicId);//题型对应的小题
				upcaseTitle = upcaseTitle.replace("QuestionSize", questionList.size()+"");//3
				stringHtml.append(upcaseTitle);//大题标题;一、单选题（共3题，共0分）
				for (int j = 0; j < questionList.size(); j++) {//遍历小题
					String paper_list_item = "            <ul>\r\n" + 
							"                <li>\r\n" + 
							"                    <div class=\"paper_con\">\r\n" + 
							"                        <div class=\"paper_title\">\r\n" + 
							"                            <span>SmallNum.&nbsp;</span>\r\n" + 
							"                            <div class=\"title-right\"></div>\r\n" + 
							"                        </div>\r\n" + 
							"                        <div class=\"paper_title_options\">\r\n" + 
							"                        </div>                     \r\n" + 
							"AnswerAnalyze"+
							"                    </div>\r\n" + 
							"                </li>\r\n" + 
							"            </ul>\r\n";
					Map<String, Object> questionMap = questionList.get(j);
					paper_list_item = paper_list_item.replace("SmallNum", (j+1)+"");
					paper_list_item = paper_list_item.replace("QuestionTitle", questionMap.get("title").toString());





					//答案
					String answer = MapUtils.getString(questionMap, "answer")!=null?MapUtils.getString(questionMap, "answer"):"";
					String analysis = MapUtils.getString(questionMap, "analysis")!=null?MapUtils.getString(questionMap, "analysis"):"";
					String centre = MapUtils.getString(questionMap, "centre")!=null?MapUtils.getString(questionMap, "centre"):"";
					String answerAnalyze = "                            <div class=\"paper-analyize\">\r\n" + 
							"                                            <div class=\"paper-analyize-item\">\r\n" + 
							"                                                    <span class=\"paper-analyize-left\">【答案】</span>\r\n" + 
							"                                                    <div class=\"paper-analyize-right paper-answer\">AnswerContext</div>\r\n" + 
							"                                                </div>\r\n" + 
							"                                            <div class=\"paper-analyize-item\">\r\n" + 
							"                                                <span class=\"paper-analyize-left\">【考点】</span>\r\n" + 
							"                                                <div class=\"paper-analyize-right paper-centre\">CentreContext</div>\r\n" + 
							"                                            </div>\r\n" + 
							"                                            <div class=\"paper-analyize-item\">\r\n" + 
							"                                                <span class=\"paper-analyize-left\">【解析】</span>\r\n" + 
							"                                                <div class=\"paper-analyize-right paper-analysis\">AnalyzeContext</div>\r\n" + 
							"                                            </div>\r\n" + 
							"                                    </div>\r\n";
				
					if (StringUtil.isEmpty(answer)) {
						answerAnalyze = answerAnalyze.replace("AnswerContext", "");
					}else {
						List<String> answerList = (List<String>) JSONArray.parse(answer);
						StringBuilder answerContextBuilder = new StringBuilder();
						if (answerList.size()==1) {
							String answerContext = answerList.get(0);
							answerContextBuilder.append(answerContext);
						} else {
							for (int k = 0; k < answerList.size(); k++) {
								String answerContext = "("+(k+1)+")&nbsp"+answerList.get(k);
								answerContextBuilder.append(answerContext);
							}
						}

						answerAnalyze = answerAnalyze.replace("AnswerContext", answerContextBuilder);
					}
					
					answerAnalyze = answerAnalyze.replace("CentreContext", centre);
					answerAnalyze = answerAnalyze.replace("AnalyzeContext", analysis);
					paper_list_item = paper_list_item.replace("AnswerAnalyze",answerAnalyze);

					stringHtml.append(paper_list_item);
				}
				stringHtml.append("        </div>\r\n");//大题结束
			}
		}
		return stringHtml.toString();
	}

	/**
	 * 生成老师试卷
	 * @param params
	 * @return
	 */
	@Override
	public JsonResult<Object> GenerateTeacherHtml(Map<String, Object> params) {
		//用于存储html字符串
		StringBuilder stringHtml = new StringBuilder();
		PrintStream printStream =null;
		try{
			String savePath=System.getProperty("catalina.home")+"\\webapps\\htmlFile";
			String htmlSavePath = getNewHtml(savePath);

			//打开文件
			printStream = new PrintStream(new FileOutputStream(htmlSavePath));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/** 添加head meta*/
		String head = getHead();

		/** 添加试卷名称*/
		Map<String, Object> titleParam = new HashMap<String, Object>();
		String headTitle = getHeadTitle(titleParam);
		String paper_detail_hd = GetPaperDetailHd(headTitle);

		String paper_list_item = GetTeacherPaperListItem(params);

		//输入HTML文件内容
		stringHtml.append("<!DOCTYPE html>\r\n<html lang=\"zh-CN\">\r\n");//html开始
		stringHtml.append(head);//头部内容
		stringHtml.append("<body style=\"zoom: 1;\">\r\n");//body开始
		stringHtml.append(paper_detail_hd);
		stringHtml.append("    <div class=\"paper_detail_bd\">\r\n");//试题开始

		stringHtml.append(paper_list_item);//小题题目

		stringHtml.append("    </div>\r\n");//试题结束
		stringHtml.append("</body>\r\n</html>\r\n");//body结束，html结束
		try{
			//将HTML文件内容写入文件中
			printStream.println(stringHtml.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult<Object>("老师用卷生成成功");
	}

	/**
	 * 生成老师试卷
	 * @param params
	 * @return
	 */
	private String GetTeacherPaperListItem(Map<String, Object> params) {
		StringBuilder stringHtml = new StringBuilder();

		String questionIdString = MapUtils.getString(params, "question");
		String[] questionIdArray = questionIdString.split(",");
		List<String> questionIdList = Arrays.asList(questionIdArray);
		List<Map<String, Object>> topicList = generateHtmlMapper.getTopicListByList(questionIdList);//题型数量
		if (topicList.size()!=0) {
			for (int i = 0; i < topicList.size(); i++) {
				stringHtml.append("        <div class=\"paper_list_item\">\r\n");//大题开始
				String upcaseTitle = "            <p>UpcaseNumber、TopicName（共QuestionSize题，共0分）</p>\r\n";
				String upcaseNumber = NumberCastUtil.ToConvtZH(i+1);//阿拉伯数字转大写
				upcaseTitle = upcaseTitle.replace("UpcaseNumber", upcaseNumber);//1
				Map<String, Object> topicMap = topicList.get(i);
				String topicName = MapUtils.getString(topicMap, "topicName");
				upcaseTitle = upcaseTitle.replace("TopicName", topicName);//2
				Integer basicTopicId = MapUtils.getInteger(topicMap, "topicId");
				List<Map<String, Object>> questionList = generateHtmlMapper.getQuestionListByListAndTopicId(questionIdList,basicTopicId);//题型对应的小题
				upcaseTitle = upcaseTitle.replace("QuestionSize", questionList.size()+"");//3
				stringHtml.append(upcaseTitle);//大题标题;一、单选题（共3题，共0分）
				for (int j = 0; j < questionList.size(); j++) {//遍历小题
					String paper_list_item = "            <ul>\r\n" + 
							"                <li>\r\n" + 
							"                    <div class=\"paper_con\">\r\n" + 
							"                        <div class=\"paper_title\">\r\n" + 
							"                            <span>SmallNum.&nbsp;</span>\r\n" + 
							"                            <div class=\"title-right\">QuestionTitle</div>\r\n" + 
							"                        </div>\r\n" + 
							"                        <div class=\"paper_title_options\">\r\n" + 
							"SmallQuestionOptions"+
							"                        </div>                     \r\n" + 
							"SmallQuestionSub"+
							"AnswerAnalyze"+
							"                    </div>\r\n" + 
							"                </li>\r\n" + 
							"            </ul>\r\n";
					Map<String, Object> questionMap = questionList.get(j);
					paper_list_item = paper_list_item.replace("SmallNum", (j+1)+"");
					paper_list_item = paper_list_item.replace("QuestionTitle", questionMap.get("title").toString());
					String options = MapUtils.getString(questionMap, "options");
					String subQuestions = MapUtils.getString(questionMap, "subQuestions");
					if (StringUtil.isEmpty(options)) {
						paper_list_item = paper_list_item.replace("SmallQuestionOptions", "");
					} else {
						StringBuilder smallQuestionOptions = new StringBuilder();
						List<Map<String, Object>> optionsList = (List<Map<String, Object>>) JSONArray.parse(options);
						Map<String, Object> optionsMap = optionsList.get(0);
						for (int k = 0; k < optionsMap.size(); k++) {
							String option = "                            <div style=\"width: 224px;\"><span>EnglishNum.</span> <span>OptionContext</span></div>\r\n";
							String englishNum = getEnglishNum(k);
							String optionContext = MapUtils.getString(optionsMap, englishNum);
							option = option.replace("EnglishNum", englishNum);
							option = option.replace("OptionContext", optionContext);
							smallQuestionOptions.append(option);
						}
						paper_list_item = paper_list_item.replace("SmallQuestionOptions",smallQuestionOptions);
					}

					if (StringUtil.isEmpty(subQuestions)) {
						paper_list_item = paper_list_item.replace("SmallQuestionSub", "");
					} else {
						StringBuilder smallQuestionSub = new StringBuilder();
						List<Map<String, Object>> subList = (List<Map<String, Object>>) JSONArray.parse(subQuestions);
						for (int k = 0; k < subList.size(); k++) {
							String sub = "                        <div class=\"paper_sub_title\"><span class=\"sort\">SubTitleNum</span>Sub_Question_Title</div>\r\n";
							Map<String, Object> subMap = subList.get(k);
							Integer titleNum = MapUtils.getInteger(subMap, "question_sort");
							String subTitleNum = "("+titleNum+")";
							sub = sub.replace("SubTitleNum", subTitleNum);
							String subContext = MapUtils.getString(subMap, "title");
							sub = sub.replace("Sub_Question_Title", subContext);
							smallQuestionSub.append(sub);
						}
						paper_list_item = paper_list_item.replace("SmallQuestionSub",smallQuestionSub);
					}

					//答案
					String answer = MapUtils.getString(questionMap, "answer")!=null?MapUtils.getString(questionMap, "answer"):"";
					String analysis = MapUtils.getString(questionMap, "analysis")!=null?MapUtils.getString(questionMap, "analysis"):"";
					String centre = MapUtils.getString(questionMap, "centre")!=null?MapUtils.getString(questionMap, "centre"):"";
					String answerAnalyze = "                            <div class=\"paper-analyize\">\r\n" + 
							"                                            <div class=\"paper-analyize-item\">\r\n" + 
							"                                                    <span class=\"paper-analyize-left\">【答案】</span>\r\n" + 
							"                                                    <div class=\"paper-analyize-right paper-answer\">AnswerContext</div>\r\n" + 
							"                                                </div>\r\n" + 
							"                                            <div class=\"paper-analyize-item\">\r\n" + 
							"                                                <span class=\"paper-analyize-left\">【考点】</span>\r\n" + 
							"                                                <div class=\"paper-analyize-right paper-centre\">CentreContext</div>\r\n" + 
							"                                            </div>\r\n" + 
							"                                            <div class=\"paper-analyize-item\">\r\n" + 
							"                                                <span class=\"paper-analyize-left\">【解析】</span>\r\n" + 
							"                                                <div class=\"paper-analyize-right paper-analysis\">AnalyzeContext</div>\r\n" + 
							"                                            </div>\r\n" + 
							"                                    </div>\r\n";
				
					if (StringUtil.isEmpty(answer)) {
						answerAnalyze = answerAnalyze.replace("AnswerContext", "");
					}else {
						List<String> answerList = (List<String>) JSONArray.parse(answer);
						StringBuilder answerContextBuilder = new StringBuilder();
						if (answerList.size()==1) {
							String answerContext = answerList.get(0);
							answerContextBuilder.append(answerContext);
						} else {
							for (int k = 0; k < answerList.size(); k++) {
								String answerContext = "("+(k+1)+")&nbsp"+answerList.get(k);
								answerContextBuilder.append(answerContext);
							}
						}

						answerAnalyze = answerAnalyze.replace("AnswerContext", answerContextBuilder);
					}
					
					answerAnalyze = answerAnalyze.replace("CentreContext", centre);
					answerAnalyze = answerAnalyze.replace("AnalyzeContext", analysis);
					paper_list_item = paper_list_item.replace("AnswerAnalyze",answerAnalyze);

					stringHtml.append(paper_list_item);
				}
				stringHtml.append("        </div>\r\n");//大题结束
			}
		}
		return stringHtml.toString();
	}
}