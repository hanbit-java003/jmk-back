package com.hanbit.there.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.SeoulService;
import com.hanbit.there.api.vo.NoticeVO;

@RestController
@RequestMapping("/api/seoul")
public class SeoulController {
	
	@Autowired
	private SeoulService seoulService;
	
	@RequestMapping("/notice")
	public List<NoticeVO> SeoulTable(){
		
		return seoulService.SeoulTable();
	}
	
}
