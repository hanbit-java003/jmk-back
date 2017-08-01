package com.hanbit.there.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.SeoulDAO;
import com.hanbit.there.api.vo.EventVO;
import com.hanbit.there.api.vo.NoticeVO;
import com.hanbit.there.api.vo.PressReleaseVO;
import com.hanbit.there.api.vo.ReferenceRoomVO;

@Service
public class SeoulService {

	@Autowired
	private SeoulDAO seoulDAO;

	public List<NoticeVO> SeoulNotice(){

		return seoulDAO.SelectNotice();
	}
	public List<EventVO> SeoulEvent(){

		return seoulDAO.SelectEvent();
	}
	public List<PressReleaseVO> SeoulPressRelease(){

		return seoulDAO.SelectPressRelease();
	}
	public List<ReferenceRoomVO> SeoulReferenceRoom(){

		return seoulDAO.SelectReferenceRoom();
	}


}
