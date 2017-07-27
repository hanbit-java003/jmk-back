package com.hanbit.there.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.SeoulDAO;
import com.hanbit.there.api.vo.NoticeVO;

@Service
public class SeoulService {
	
	@Autowired
	private SeoulDAO seoulDAO;
	
	public List<NoticeVO> SeoulTable(){
		
		return seoulDAO.SelectTable();
	}
	
	
}
