package com.tonic.web.controller.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tonic.web.model.service.WordService;
import com.tonic.web.model.vo.Word;

@RestController("apiHomeController")
@RequestMapping("/api/word/")
public class HomeController {
	@Autowired
	private WordService service;
	
	@RequestMapping("select")
	public Word search(@RequestBody HashMap<String, String> input) {
		String selectKeyword = input.get("keyword");
		System.out.println(selectKeyword);
		Word word = service.selectWord(selectKeyword);
		System.out.println(word);
		if(word != null) {
			return word;
		}
		System.out.println("검색 결과 없음");
		return null;
	}
}
