package com.hanbit.there.api.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.there.api.vo.NoticeVO;

@Repository
public class SeoulDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<NoticeVO> SelectTable(){
		return sqlSession.selectList("seoul.selectNotice");
	}
	
}
