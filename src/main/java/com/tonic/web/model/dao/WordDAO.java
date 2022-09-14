package com.tonic.web.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tonic.web.model.vo.Word;

public interface WordDAO {
	
	
	/** 영단어 추가 DAO
	 * @param eng, mean, part
	 * @return insert 성공시 true
	 */
	public int insertEng(String eng);
	
	/**
	 * @param eng
	 * @param mean
	 * @param part
	 * @return insert 성공시 1반환
	 */
	public int insertMean(String eng, String mean, String part);
	
	/** 중복 확인 DAO
	 * @param word 중복체크할 단어
	 * @return 중복결과가 있으면 true, 없으면 false
	 */
	public boolean checkOverLap(String eng);
	
	// 영단어 삭제
	public void deleteWord(Word word);
	
	
	/** 영단어 검색 DAO
	 * @param keyword 검색할 단어
	 * @return 검색에 성공하면 Word 인스턴스 반환, 검색에 실패하거나 연결에 실패하면 null 반환
	 */
	public Word selectWord(String keyword);
	
	/** 모든 영단어 검색 DAO
	 * @return wordList
	 */
	public List<Word> selectAllWord();
	
	/** 모든 영단어의 eng 검색 DAO
	 * @return engList
	 */
	public List<String> selectAllEng();
	
	/** eng의 mean 검색 DAO
	 * @param eng 
	 * @return eng의 meanHash 반환
	 */
	public Map<String, ArrayList<String>> selectMean(String keyword);
}
