package com.tonic.web.model.dao.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * @param eng
	 * @return Insert 성공 시 true 반환
	 */ 
	@Override
	public int insertEng(String eng) {
		final String SQL = "INSERT INTO t_eng (eng) VALUES (?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, eng);
			
			result = pstmt.executeUpdate();
			
			if(result == 1) conn.commit();
			else 			conn.rollback();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public int insertMean(String eng, String mean, String part) {
		System.out.println("insertMean 호출");
		final String SQL = "INSERT INTO t_mean (pos, mean, eng_fk) VALUES (?, ?, ?);";
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, part);
			pstmt.setString(2, mean);
			pstmt.setString(3, eng);
			
			result = pstmt.executeUpdate();
			if(result == 1) conn.commit();
			else 			conn.rollback();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * 영단어 중복 체크
	 * @param word 중복체크할 단어
	 * @return 중복결과가 있으면 true, 없으면 false
	 */
	@Override
	public boolean checkOverLap(String eng) {
		final String SQL = "SELECT * FROM t_eng WHERE eng = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			conn = dataSource.getConnection();	
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, eng);
			rs = pstmt.executeQuery();
			if(rs.next()) {	// 다음 result가 있으면 true == 이미 있는 단어 == 중복
				result = true;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close(); 
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void deleteWord(Word word) {}
	
	
	@Override
	public List<String> selectAllEng() {
		final String SQL = "SELECT ENG FROM T_ENG";
		List<String> engList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				engList.add(rs.getString("ENG"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return engList;
	}
	
	@Override
	public Word selectWord(String keyword) {
		final String SQL = "SELECT eng, pos, mean, accurateCount, wrongCount FROM t_eng e INNER JOIN t_mean m ON m.eng_fk = e.eng WHERE e.eng = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Word word = null;
		
		HashMap<String, ArrayList<String>> meanHash = new HashMap<>();
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String key = rs.getString("pos");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(!meanHash.isEmpty()) {									// 검색 결과가 비어있지 않으면
			word = new Word(keyword, meanHash);						// Word객체생성
		}			 
		return word;
	}
	
	@Override
	public List<Word> selectAllWord() {
		final String SQL = "SELECT eng, pos, mean, accurateCount, wrongCount FROM t_eng e INNER JOIN t_mean m ON m.eng_fk = e.eng";
		List<Word> wordList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			HashMap<String, ArrayList<String>> meanHash = new HashMap<>();
			while(rs.next()) {
				String key = rs.getString("pos");
				if(meanHash.containsKey(key)) {						// 이미 해당 품사 명으로 데이터가 있으면
					meanHash.get(key).add(rs.getString("mean"));	// ArrayList에 뜻만 추가
				} else {											// 해당 품사 명으로 처음 등록하면
					meanHash.put(key, new ArrayList<String>());		// ArrayList를 새로 만들고
					meanHash.get(key).add(rs.getString("mean"));
				}
				
			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return wordList;
	}
	
	@Override
	public Map<String, ArrayList<String>> selectMean(String keyword) {
		final String SQL = "SELECT * FROM t_mean WHERE eng_fk = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Map<String, ArrayList<String>> meanHash = new HashMap<>();
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String key = rs.getString("pos");
				if(meanHash.containsKey(key)) {		// 키 값이 이미 존재하면
					meanHash.get(key).add(rs.getString("mean"));
				} else {							// 키 값을 처음 등록하면
					meanHash.put(key, new ArrayList<String>());
					meanHash.get(key).add(rs.getString("mean"));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return meanHash;
	}
}
