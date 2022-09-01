package com.tonic.web.model.vo;

import java.util.ArrayList;
import java.util.HashMap;

public class Word {
	private String eng;			// 영단어
	private HashMap<String, ArrayList<String>> meanHash; // 뜻
	private int accurateCount; 	// 정답 횟수
	private int wrongCount;		// 틀린 횟수

	public Word(String eng, HashMap<String, ArrayList<String>> meanHash) {
		this.eng = eng;
		this.meanHash = meanHash;
	}
	
	public String getEng() {
		return eng;
	}
	
	public void setEng(String eng) {
		this.eng = eng;
	}
	
	public HashMap<String, ArrayList<String>> getMeanList() {
		return meanHash;
	}
	public void setMeanList(HashMap<String, ArrayList<String>> meanHash) {
		this.meanHash = meanHash;
	}
	
	public int getAccurateCount() {
		return accurateCount;
	}
	public void setAccurateCount(int accurateCount) {
		this.accurateCount = accurateCount;
	}
	
	public int getWrongCount() {
		return wrongCount;
	}
	public void setWrongCount(int wrongCount) {
		this.wrongCount = wrongCount;
	}

	@Override
	public String toString() {
		return "Word [eng=" + eng + ", meanHash=" + meanHash + ", accurateCount=" + accurateCount + ", wrongCount="
				+ wrongCount + "]";
	}
}
