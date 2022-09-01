package com.tonic.web.model.service.jdbc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonic.web.model.dao.WordDAO;
import com.tonic.web.model.service.WordService;
import com.tonic.web.model.vo.Word;

@Service
public class JDBCWordService implements WordService{
	@Autowired
	private WordDAO wordDAO;
	
	
	@Override
	public boolean insertWord(Word word) {
		// 중복 확인
		boolean isOverLap = wordDAO.checkOverLap(word);
		if(isOverLap) {		// 영단어가 중복O
			return false;
		} else {			// 영단어가 중복X
			wordDAO.insertWord(word);
			return true;
		}
		
	}

	@Override
	public void deleteWord() {

		
	}

	@Override
	public void updateWord() {

		
	}

	@Override
	public ArrayList<Word> selectAllWord() {

		return null;
	}

	@Override
	public Word selectWord(String keyword) {
		Word word = wordDAO.selectWord(keyword);
		if(word != null) {
			return word;
		}
		return null;
	}
	
}
