package com.hongrant.www.achieve.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface GenerateHtmlMapper {

	String getSubjectIdByName(@Param("subjectId")Integer subjectId);

	String getGradeIdByName(@Param("gradeId")Integer gradeId);

	List<Map<String, Object>> getQuestionListByList(@Param("list") List<Integer> questionIdList);

	List<Map<String, Object>> getTopicListByList(@Param("list") List<String> questionIdList);

	List<Map<String, Object>> getQuestionListByListAndTopicId(@Param("list")List<String> questionIdList,@Param("topicId") Integer basicTopicId);

}
