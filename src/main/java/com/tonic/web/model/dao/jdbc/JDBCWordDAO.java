package com.tonic.web.model.dao.jdbc;

import static com.tonic.web.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonic.web.model.dao.WordDAO;
import com.tonic.web.model.vo.Word;

@Service
public class JDBCWordDAO implements WordDAO{
	PreparedStatement pstmt;
	ResultSet rs;
	Properties prop;
	
	public JDBCWordDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("extend-query.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 영단어 추가 DAO
	 * @return 추가된 행 갯수 
	 */
	@Override
	public int insertEng(Connection conn, String eng) throws Exception{
		final String SQL = prop.getProperty("insertEng");
		
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, eng);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/** 영단어 뜻 추가 DAO
	 *	@return 추가된 행 갯수
	 */
	@Override
	public int insertMean(Connection conn, String eng, String mean, String part) throws Exception{
		System.out.println("insertMean 호출");
		final String SQL = prop.getProperty("insertMean");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, part);
			pstmt.setString(2, mean);
			pstmt.setString(3, eng);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/**
	 * 영단어 중복 체크
	 * @param word 중복체크할 단어
	 * @return 중복결과가 있으면 true, 없으면 false
	 */
	@Override
	public boolean engDupCheck(Connection conn, String eng) throws Exception {
		final String SQL = prop.getProperty("engDupCheck");

		boolean result = false;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, eng);
			rs = pstmt.executeQuery();
			if(rs.next()) {	// 다음 result가 있으면 true == 이미 있는 단어 == 중복
				result = true;
			} 
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	@Override
	public Word selectWord(Connection conn, String keyword) throws Exception{
		final String SQL = prop.getProperty("selectWord"); 

		Word word = null;
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(word == null) {
					word = new Word();
					word.setEng(keyword);
				}
				String key = rs.getString("pos");
				word.setMeanHash(key, rs.getString("mean"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}

		return word;
	}
	
//	@Override
//	public List<Word> selectAllWord() {
//		final String SQL = "SELECT eng, pos, mean, accurateCount, wrongCount FROM t_eng e INNER JOIN t_mean m ON m.eng_fk = e.eng";
//		List<Word> wordList = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = dataSource.getConnection();
//			pstmt = conn.prepareStatement(SQL);
//			rs = pstmt.executeQuery();
//			
//			HashMap<String, ArrayList<String>> meanHash = new HashMap<>();
//			while(rs.next()) {
//				String key = rs.getString("pos");
//				if(meanHash.containsKey(key)) {						// 이미 해당 품사 명으로 데이터가 있으면
//					meanHash.get(key).add(rs.getString("mean"));	// ArrayList에 뜻만 추가
//				} else {											// 해당 품사 명으로 처음 등록하면
//					meanHash.put(key, new ArrayList<String>());		// ArrayList를 새로 만들고
//					meanHash.get(key).add(rs.getString("mean"));
//				}
//				
//			
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(rs != null) rs.close();
//				if(pstmt != null) pstmt.close();
//				if(conn != null) conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return wordList;
//	}
	
	/** keyword에 맞는 뜻 반환 DAO
	 * @param conn
	 * @param keyword
	 * @return meanHash
	 */
	
	/** 문자열 유사도 검사 (0 ~ 1)
	 * @param s1
	 * @param s2
	 * @return s1, s2를 비교해서 유사도 반환
	 */
	
	
}
