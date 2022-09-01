package com.tonic.web.model.dao.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonic.web.model.dao.WordDAO;
import com.tonic.web.model.vo.Word;

@Service
public class JDBCWordDAO implements WordDAO{
	
	@Autowired
	private DataSource dataSource;

	/**
	 * @param word
	 */
	@Override
	public void insertWord(Word word) {
		final String QUERY = "INSERT INTO t_word (eng, mean) VALUES (?, ?)";
		String eng = word.getEng();
		HashMap<String, ArrayList<String>> mean = word.getMeanList();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(QUERY);
			pstmt.setString(1, eng);
//			pstmt.setString(2, mean);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 영단어 중복 체크
	 * @param word 중복체크할 단어
	 * @return 중복결과가 있으면 true, 없으면 false
	 */
	@Override
	public boolean checkOverLap(Word word) {
		final String QUERY = "SELECT * FROM t_word WHERE eng = ?";
		String eng = word.getEng();
		
		try {
			Connection conn = dataSource.getConnection();	
			PreparedStatement pstmt = conn.prepareStatement(QUERY);
			pstmt.setString(1, eng);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {	// 다음 result가 있으면 true == 이미 있는 단어 == 중복
				return true;
			} else {
				return false;
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void deleteWord(Word word) {
		
	}
	
	@Override
	public Word selectWord(String keyword) {
		final String QUERY = "SELECT eng, pos, mean, accurateCount, wrongCount FROM t_eng e INNER JOIN t_mean m ON m.eng_fk = e.eng WHERE e.eng = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HashMap<String, ArrayList<String>> meanHash = new HashMap<>();
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(QUERY);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String key = rs.getString("pos");
				if(meanHash.containsKey(key)) {						// 이미 해당 품사 명으로 데이터가 있으면
					meanHash.get(key).add(rs.getString("mean"));	// ArrayList에 뜻만 추가
				} else {											// 해당 품사 명으로 처음 등록하면
					meanHash.put(key, new ArrayList<String>());		// ArrayList를 새로 만들고
					meanHash.get(key).add(rs.getString("mean"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(meanHash.isEmpty()) {									// 검색 결과가 비어있으면
			return null;											// null 반환
		} else {
			return new Word(keyword, meanHash);						// 검색정보를 바탕으로 Word객체 생성해서 반환
		}
	}
	
//	@Override
//	public Word selectWord(String keyword) {
//		final String QUERY = "SELECT * FROM t_word WHERE eng = ?";		// 쿼리
//		Word word = null;
//		try {
//			Connection conn = dataSource.getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(QUERY);
//			pstmt.setString(1, keyword);
//			ResultSet rs = pstmt.executeQuery();
//
//			if(rs.next()) {
//				word = new Word(rs.getString("eng"), rs.getString("mean"));
//				pstmt.close();
//				conn.close();
//				rs.close();
//			}
//		} catch(Exception e) {
//			
//		} 
//		if(word != null) {
//			return word;
//		} else {
//			return null;
//		} 
//	}
	 
}
