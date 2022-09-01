package com.tonic.web.model.dao;

import com.tonic.web.model.vo.Word;

public interface WordDAO {
	
	
	// 영단어 추가
	/**
	 * @param word 추가할 단어
	 */
	public void insertWord(Word word);
	
	// 중복 확인
	/**
	 * @param word 중복체크할 단어
	 * @return 중복결과가 있으면 true, 없으면 false
	 */
	public boolean checkOverLap(Word word);
	
	// 영단어 삭제
	public void deleteWord(Word word);
	
	// 영단어 검색
	
	/**
	 * @param keyword 검색할 단어
	 * @return 검색에 성공하면 Word 인스턴스 반환, 검색에 실패하거나 연결에 실패하면 null 반환
	 */
	public Word selectWord(String keyword);
}
