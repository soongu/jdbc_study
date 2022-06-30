package com.jdbc.basic.score.view;

import com.jdbc.basic.score.controller.ScoreController;
import com.jdbc.basic.score.domain.Score;

import java.util.List;
import java.util.Scanner;

public class ScoreMenu {

    private final ScoreController controller;
    private final Scanner sc;

    public ScoreMenu() {
        controller = new ScoreController();
        sc = new Scanner(System.in);
    }

    public void mainMenu() {

        while (true) {
            System.out.println("\n======= 성적 관리 프로그램 ========");
            System.out.println("# 1. 성적 정보 입력");
            System.out.println("# 2. 성적 전체 조회");
            System.out.println("# 3. 성적 개별 조회");
            System.out.println("# 4. 성적 정보 수정");
            System.out.println("# 5. 성적 정보 삭제");
            System.out.println("# 9. 끝내기");

            int menu = inputN("\n메뉴 입력: ");

            switch (menu) {
                case 1:
                    insertMenu();
                    break;
                case 2:
                    findAllMenu();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 9:
                    System.out.println("\n# 프로그램을 종료합니다.");
                    System.exit(0);
                    return;
                default:
                    System.out.println("\n# 메뉴를 다시 입력하세요.");
            }
        }


    }

    // 2번 메뉴
    private void findAllMenu() {

        List<Score> students = controller.findAllStudents();

        System.out.printf("\n=============== 모든 성적 정보 (반 평균: %.2f점) ================\n"
                                    , controller.calcClassAverage());
        System.out.printf("%5s%5s%5s%5s%5s%5s%5s\n"
                            , "학번", "이름", "국어", "영어", "수학", "총점", "평균");
        System.out.println("----------------------------------------------");

        for (Score s : students) {
            System.out.printf("%5d %5s%5d  %5d  %5d  %4d   %.2f\n"
                                , s.getStuNum(), s.getStuName(), s.getKor()
                                , s.getEng(), s.getMath(), s.getTotal()
                                , s.getAverage());
        }

    }

    // 1번 메뉴
    private void insertMenu() {

        System.out.println("\n# 성적 정보 입력을 시작합니다.");
        System.out.print("- 이름: ");
        String name = sc.next();

        int kor = inputN("- 국어: ");
        int eng = inputN("- 영어: ");
        int math = inputN("- 수학: ");

        Score score = new Score();
        score.setStuName(name);
        score.setKor(kor);
        score.setEng(eng);
        score.setMath(math);
        score.calc();

        controller.insertStudent(score);

        System.out.printf("\n# %s님의 성적 정보가 저장되었습니다.\n", name);

    }

    // 숫자입력 메서드
    private int inputN(String msg) {
        int n;
        while (true) {
            try {
                System.out.print(msg);
                n = sc.nextInt();
                break;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("# 정수로만 입력하세요");
            }
        }
        return n;
    }
}
