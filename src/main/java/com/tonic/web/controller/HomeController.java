package com.tonic.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tonic.web.model.service.WordService;
import com.tonic.web.model.vo.Word;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private WordService service;
	
	@RequestMapping("index")
	public String index() {
		System.out.println("index 요청");
		return "home.index";
	}
	
	@RequestMapping("test")
	public String test() {
		System.out.println("test요청");
		return "home.index";
	}
}
