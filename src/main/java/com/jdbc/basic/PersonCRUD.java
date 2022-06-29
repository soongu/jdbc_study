package com.jdbc.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonCRUD {

    // 내부 클래스 선언
    public static class Person {
        private String ssn; // 주민번호
        private String name; // 이름
        private int age; // 나이

        public Person(String ssn, String name, int age) {
            this.ssn = ssn;
            this.name = name;
            this.age = age;
        }

        public String getSsn() {
            return ssn;
        }

        public void setSsn(String ssn) {
            this.ssn = ssn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "ssn='" + ssn + '\'' +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }// end inner class


    // 메서드 선언

    // 사람 객체를 생성해서 반환하는 팩토리메서드 선언
    public static Person makePerson(String ssn, String name, int age) {
        return new Person(ssn, name, age);
    }

    // INSERT 메서드
    public static boolean save(Person p) {
        // 1. SQL구문을 작성
        String sql = "INSERT INTO person " +
                        "(ssn, person_name, age) " +
                    "VALUES (?, ?, ?)";

        // 2. DB에 접속
        try(Connection conn = Connect.makeConnection()) {

            // 3. SQL실행을 위한 객체 PreparedStatement 생성
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. SQL에  ? 값 채우기
            pstmt.setString(1, p.getSsn());
            pstmt.setString(2, p.getName());
            pstmt.setInt(3, p.getAge());

            // 5. 실행 명령 내리기
            // - INSERT, UPDATE, DELETE : executeUpdate()
            // - SELECT : executeQuery()

            // executeUpdate는 삽입이 실패하면 0을 리턴
            int resultNumber = pstmt.executeUpdate();

            return resultNumber != 0;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // DELETE 메서드
    public static boolean remove(String ssn) {
        // 1. SQL구문을 작성
        String sql = "DELETE FROM person WHERE ssn=?";

        // 2. DB에 접속
        try(Connection conn = Connect.makeConnection()) {

            // 3. SQL실행을 위한 객체 PreparedStatement 생성
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. SQL에  ? 값 채우기
            pstmt.setString(1, ssn);

            // 5. 실행 명령 내리기
            // - INSERT, UPDATE, DELETE : executeUpdate()
            // - SELECT : executeQuery()

            // executeUpdate는 삭제가 실패하면 0을 리턴
            int resultNumber = pstmt.executeUpdate();

            return resultNumber != 0;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // UPDATE 메서드
    public static boolean modify(String name, String ssn) {
        // 1. SQL구문을 작성
        String sql = "UPDATE person " +
                     "SET person_name = ? " +
                     "WHERE ssn = ?";

        // 2. DB에 접속
        try(Connection conn = Connect.makeConnection()) {

            // 3. SQL실행을 위한 객체 PreparedStatement 생성
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. SQL에  ? 값 채우기
            pstmt.setString(1, name);
            pstmt.setString(2, ssn);

            // 5. 실행 명령 내리기
            // - INSERT, UPDATE, DELETE : executeUpdate()
            // - SELECT : executeQuery()

            // executeUpdate는 삭제가 실패하면 0을 리턴
            int resultNumber = pstmt.executeUpdate();

            return resultNumber != 0;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }


    // SELECT ALL
    public static List<Person> findAll() {

        List<Person> personList = new ArrayList<>();

        //1. SQL 작성
        String sql = "SELECT * FROM person";

        //2. DB 연결
        try (Connection conn = Connect.makeConnection()) {

            // 3. SQL실행을 위한 pstmt객체 생성
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. ?값 채우기

            // 5. SQL 실행 - SELECT실행시 executeQuery()

            // ResultSet : 조회 결과 나타나는 2차원의 표
            ResultSet rs = pstmt.executeQuery();

            // ResultSet을 순회해서 소비
            // a -  next() 메서드를 통해 행들을 순서대로 지목
            // b -  getter메서드를 통해 열데이터를 추출
            while (rs.next()) {
                String ssn = rs.getString("ssn");
                String name = rs.getString("person_name");
                int age = rs.getInt("age");

                Person p = makePerson(ssn, name, age);
                personList.add(p);
            }
            return personList;


        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // 빈 리스트 반환
        }
    }


}// end class
