package com.tonic.web.model.vo;

import java.util.ArrayList;
import java.util.HashMap;

public class Word {
	private String eng;			// 영단어
	private HashMap<String, ArrayList<String>> meanHash = new HashMap<>(); // 뜻
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
	
	public HashMap<String, ArrayList<String>> getMeanHsah() {
		return meanHash;
	}
	public void setMeanHash(String part, String mean) {
		if(meanHash.containsKey(part)) {						// 이미 해당 품사 명으로 데이터가 있으면
			meanHash.get(part).add(mean);	// ArrayList에 뜻만 추가
		} else {											// 해당 품사 명으로 처음 등록하면
			meanHash.put(part, new ArrayList<String>());		// ArrayList를 새로 만들고
			meanHash.get(part).add(mean);
		}
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
