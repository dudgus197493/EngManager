package com.tonic.web.model.service.jdbc;

import static com.tonic.web.common.JDBCTemplate.close;
import static com.tonic.web.common.JDBCTemplate.commit;
import static com.tonic.web.common.JDBCTemplate.getConnection;
import static com.tonic.web.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonic.web.model.dao.WordDAO;
import com.tonic.web.model.service.WordService;
import com.tonic.web.model.vo.Word;

@Service
public class JDBCWordService implements WordService{
	@Autowired
	private WordDAO wordDAO;
	
	
	/** 새로운 단어 추가 DAO
	 * @param eng, mean, part
	 * @return 정상적으로 추가 성공 시 true
	 */
	@Override
	public int insertWord(String eng, String mean, String part) throws Exception {

		// 중복이면 리턴 0
		Connection conn = getConnection();
		int result = 0;
		try {
			// 중복확인
			if(!wordDAO.engDupCheck(conn, eng)) {		// 중복이 아니면
				result += wordDAO.insertEng(conn, eng); // 영단어 추가
				result += wordDAO.insertMean(conn, eng, mean, part);
			} else {
				if(!meanDupCheck(wordDAO.selectWord(conn, eng), part, mean)) { // 중복이 아니면
					result += wordDAO.insertMean(conn, eng, mean, part);	
				}
			}
			
			if(result >= 1) {							// insert 성공 시 COMMIT
				commit(conn);
			}
		}catch(Exception e) {
			rollback(conn);								// 하나라도 오류 생길 시 롤백
			e.printStackTrace();
			throw new Exception("영단어 추가중 예외 발생");
		} finally {
			close(conn);
		}

		return result;
	}

	@Override
	public List<Word> selectAllWord() {
//		List<Word> wordList =  wordDAO.selectAllWord();
		return null;
	}
	
	@Override public List<String> selectAllEng() {
//		return wordDAO.selectAllEng(); 
		return null;
	}

	@Override
	public Word selectWord(String keyword) throws Exception {
		Connection conn = getConnection();
		
		Word word = wordDAO.selectWord(conn, keyword);
		
		close(conn);
		
		return word;
	}
	
	/** 영단어 뜻 중복검사
	 * @param word
	 * @param part
	 * @param mean
	 * @return 중복이면 true
	 */
	private boolean meanDupCheck(Word word, String part, String mean) {
		Map<String, ArrayList<String>> meanHash = word.getMeanHash();
		boolean flag = false; // 중복이면 true, 아니면 false;
		if(!meanHash.isEmpty()) {				// meanHash 가 비어있지 않으면
			// meanHash 돌면서 파라미터 mean과 문자열 유사성 검사
			
			for(String key : meanHash.keySet()) {
				if(!key.equals(part)) {				// 품사가 다르면
					continue;						// 다음 품사로 넘어감
				} 
				for(String m : meanHash.get(key)) {
					if(getSimilarity(m, mean) > 0.6) {	// 유사성 검사 수치가 일정수치 이상이면 flag = true; break;
						flag = true;
						break;
					}
				}
				if(flag == true) break;
			}
		}
		return flag;
	}
	
	private double getSimilarity(String s1, String s2) {
		s1 = s1.replace(" ", "");		// 각 문자열 공백 제거
		s2 = s2.replace(" ", "");
		
		String longer = s1, shorter = s2;
		
		if (s1.length() < s2.length()) {
			longer = s2; 
			shorter = s1;
		}
		
		int longerLength = longer.length();
		if (longerLength == 0) return 1.0;
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
	}
	private int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		int[] costs = new int[s2.length() + 1];
		
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0) {
					costs[j] = j;
				} else {
					if (j > 0) {
						int newValue = costs[j - 1];
						
						if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						}
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0) costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}
}
