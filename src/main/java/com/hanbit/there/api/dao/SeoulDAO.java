package com.hanbit.there.api.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.there.api.vo.EventVO;
import com.hanbit.there.api.vo.ExhibtionVO;
import com.hanbit.there.api.vo.NoticeVO;
import com.hanbit.there.api.vo.PressReleaseVO;
import com.hanbit.there.api.vo.ReferenceRoomVO;

@Repository
public class SeoulDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<NoticeVO> SelectNotice(){
		return sqlSession.selectList("seoul.selectNotice");
	}
	
	public List<EventVO> SelectEvent(){
		return sqlSession.selectList("seoul.selectEvent");
	}
	
	public List<PressReleaseVO> SelectPressRelease(){
		return sqlSession.selectList("seoul.selectPressRelease");
	}
	
	public List<ReferenceRoomVO> SelectReferenceRoom(){
		return sqlSession.selectList("seoul.selectReferenceRoom");
	}
	
	public List<ExhibtionVO> SelectExhibition(){
		return sqlSession.selectList("seoul.selectExhibtion");
	}
	
}
