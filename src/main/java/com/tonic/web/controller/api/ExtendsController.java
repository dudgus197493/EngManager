package com.tonic.web.controller.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tonic.web.model.service.WordService;
import com.tonic.web.model.vo.Word;

@RestController("apiHomeController")
@RequestMapping("/api/word/")
public class ExtendsController {
	@Autowired
	private WordService service;
	
	@RequestMapping("insert")
	public int insert(@RequestBody HashMap<String, String> input) {
		// service insert 메서드 호출
		int result = 0;
		String eng = input.get("eng");
		String mean = input.get("mean");
		String part = input.get("part");
		System.out.printf("eng : %s / mean : %s / part : %s", eng, mean, part);
		try {
			result = service.insertWord(eng, mean, part);
		}catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@RequestMapping("select")
	public Word select(@RequestBody HashMap<String, String> input) {
		String selectKeyword = input.get("keyword");
		Word word = null;
		System.out.println(selectKeyword);
		try {
			word = service.selectWord(selectKeyword);
			
		}catch(Exception e) {
			System.out.println("검색중 예외 발생");
			e.printStackTrace();
		}
		System.out.println(word);
		return word;
	}
	
	@RequestMapping("selectall/word")
	public List<Word> selectAllWord() {
		List<Word> list = service.selectAllWord();
		System.out.println(list);
		return list;
	}
	
	
	@RequestMapping("selectall/eng") 
	public List<String> selectAllEng() {
		List<String> list =service.selectAllEng();
		System.out.println(list);
		return list;
	}
}
