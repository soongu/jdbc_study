package com.jdbc.basic.score.repository;

import com.jdbc.basic.score.domain.Score;

import java.util.Map;

public interface ScoreRepository {

    // 성적 정보 저장
    boolean save(Score score);

    // 성적 정보 삭제
    boolean remove(int stuNum);

    // 성적 정보 수정
    boolean modify(Score score);

    // 전체 성적 조회
    Map<Integer, Score> findAll();

    // 개별 성적 조회
    Score findOne(int stuNum);

    // 반 전체 평균 조회
    double getClassAverage();
}
