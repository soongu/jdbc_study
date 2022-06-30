package com.jdbc.basic.score.domain;

import lombok.*;

@Setter @Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 초기화 생성자
@ToString
@Builder(access = AccessLevel.PUBLIC)  // 객체 생성시 생성자 역할을 대신
// 데이터베이스 score테이블의 행데이터를 저장할 객체
public class Score {

    private int stuNum;
    private String stuName;
    private int kor;
    private int eng;
    private int math;
    private int total;
    private double average;

    // 총점, 평균을 계산하는 메서드
    public void calc() {
        this.total = kor + eng + math;
        this.average = Math.round((total / 3.0) * 100) / 100.0;
    }

}
