package com.jdbc.basic.score.controller;

import com.jdbc.basic.score.domain.Score;
import com.jdbc.basic.score.repository.ScoreOracleRepo;
import com.jdbc.basic.score.repository.ScoreRepository;

import java.util.HashMap;
import java.util.Map;

// 데이터들을 전처리 후처리하고 메모리에 저장 관리하는 클래스
public class ScoreController {

    // 성적 정보가 저장될 맵 ( k: 학번, v: 성적정보 )
    private static final Map<Integer, Score> scoreMap;

    // ScoreRepository에 의존성 관계를 가진다.
    private final ScoreRepository repository;

    public ScoreController() {
        this.repository = new ScoreOracleRepo();
    }

    static {
        scoreMap = new HashMap<>();
    }



    // 학생 성적정보 입력 기능
    public void insertStudent(Score score) {
        // 메모리에 저장
        scoreMap.put(score.getStuNum(), score);
        // DB에 저장 명령
        repository.save(score);
    }

}
