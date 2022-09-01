package com.tonic.web.model.service;

import java.util.ArrayList;

import com.tonic.web.model.vo.Word;

public interface WordService {
	
	// 영단어 추가
	/**
	 * @param word
	 * @return true면 영단어 추가 성공, false면 실패
	 */
	public boolean insertWord(Word word);
	
	// 영단어 삭제
	public void deleteWord();
	
	// 영단어 정보 수정
	public void updateWord();
	
	// 영단어 조회 (키워드)
	/**
	 * @param keyword keyword 검색할 단어
	 * @return 검색에 성공하면 Word 인스턴스 반환, 검색에 실패하거나 연결에 실패하면 null 반환
	 */
	public Word selectWord(String keyword);
	
	// 모든 영단어 조회
	public ArrayList<Word> selectAllWord();
}
