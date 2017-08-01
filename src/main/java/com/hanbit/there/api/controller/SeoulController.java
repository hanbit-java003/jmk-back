package com.hanbit.there.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.SeoulService;
import com.hanbit.there.api.vo.EventVO;
import com.hanbit.there.api.vo.NoticeVO;
import com.hanbit.there.api.vo.PressReleaseVO;
import com.hanbit.there.api.vo.ReferenceRoomVO;

@RestController
@RequestMapping("/api/seoul")
public class SeoulController {
	
	@Autowired
	private SeoulService seoulService;
	
	@RequestMapping("/notice")
	public List<NoticeVO> SeoulNotice(){
		
		return seoulService.SeoulNotice();
	}
	
	@RequestMapping("/event")
	public List<EventVO> SeoulEvent(){
		
		return seoulService.SeoulEvent();
	}
	
	@RequestMapping("/pressRelease")
	public List<PressReleaseVO> SeoulPressRelease(){
		
		return seoulService.SeoulPressRelease();
	}
	
	@RequestMapping("/referenceRoom")
	public List<ReferenceRoomVO> SeoulReferenceRoom(){
		
		return seoulService.SeoulReferenceRoom();
	}
	
}
