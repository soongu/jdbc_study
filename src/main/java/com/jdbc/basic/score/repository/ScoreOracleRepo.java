package com.jdbc.basic.score.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.score.domain.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Oracle DBMS에 성적 정보를 CRUD하는 클래스
public class ScoreOracleRepo implements ScoreRepository {
    @Override
    public boolean save(Score score) {

        String sql = "INSERT INTO score " +
                "VALUES (seq_score.nextval, ?,?,?,?,?,?)";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getStuName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3, score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5, score.getTotal());
            pstmt.setDouble(6, score.getAverage());

            int result = pstmt.executeUpdate();

            if (result != 0) {
                conn.commit(); // 커밋 완료
                return true;
            } else {
                conn.rollback(); // 롤백
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    @Override
    public boolean remove(int stuNum) {

        String sql = "DELETE FROM score WHERE stu_num=?";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stuNum);

            int result = pstmt.executeUpdate();

            if (result != 0) {
                conn.commit(); // 커밋 완료
                return true;
            } else {
                conn.rollback(); // 롤백
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean modify(Score score) {
        String sql = "UPDATE score " +
                    " SET kor =?, eng = ?, math = ?, total = ?, average = ? " +
                    " WHERE stu_num = ?";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, score.getKor());
            pstmt.setInt(2, score.getEng());
            pstmt.setInt(3, score.getMath());
            pstmt.setInt(4, score.getTotal());
            pstmt.setDouble(5, score.getAverage());
            pstmt.setInt(6, score.getStuNum());

            int result = pstmt.executeUpdate();

            if (result != 0) {
                conn.commit(); // 커밋 완료
                return true;
            } else {
                conn.rollback(); // 롤백
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public Map<Integer, Score> findAll() {

        Map<Integer, Score> scoreMap = new HashMap<>();

        String sql = "SELECT * FROM score ORDER BY stu_num";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Score s = new Score(
                        rs.getInt("stu_num")
                        , rs.getString("stu_name")
                        , rs.getInt("kor")
                        , rs.getInt("eng")
                        , rs.getInt("math")
                        , rs.getInt("total")
                        , rs.getDouble("average")
                );
                scoreMap.put(s.getStuNum(), s);

            }
            return scoreMap;


        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    @Override
    public Score findOne(int stuNum) {

        String sql = "SELECT * FROM score WHERE stu_num = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, stuNum);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Score s = new Score(
                        rs.getInt("stu_num")
                        , rs.getString("stu_name")
                        , rs.getInt("kor")
                        , rs.getInt("eng")
                        , rs.getInt("math")
                        , rs.getInt("total")
                        , rs.getDouble("average")
                );
                return s;
            }

            return null;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public double getClassAverage() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT AVG(average) AS avg_cls\n")
                .append("FROM score");

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_cls");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;

    }
}
