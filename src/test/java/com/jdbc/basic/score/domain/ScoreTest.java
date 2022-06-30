package com.jdbc.basic.score.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void lombokTest() {
        Score s = new Score(1, "김철수", 99, 88, 11, 300, 40);
        s.setTotal(222);

        System.out.println(s.getStuName());

        assertEquals(s.getStuName(), "김철수");

    }

    @Test
    void lombokBuilderTest() {

        Score park = new Score.ScoreBuilder()
                            .stuNum(2)
                            .stuName("박영희")
                            .math(92)
                            .eng(100)
                            .build();

        System.out.println(park);


    }

}