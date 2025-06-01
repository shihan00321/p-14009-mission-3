package com.back.domain.wiseSaying.controller;

import com.back.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {

    private final Scanner scanner;
    private final WiseSayingService service = new WiseSayingService();

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void registerWiseSaying() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();
        int lastId = service.registerWiseSaying(content, author);
        System.out.println(lastId + "번 명언이 등록되었습니다.");
    }

    public void updateWiseSaying(int updateId) {
        try {
            WiseSaying existingWiseSaying = service.findWiseSaying(updateId);
            System.out.printf("명언(기존) : %s\n", existingWiseSaying.getContent());
            System.out.print("명언 : ");
            String newContent = scanner.nextLine().trim();

            System.out.printf("작가(기존) : %s\n", existingWiseSaying.getAuthor());
            System.out.print("작가 : ");
            String newAuthor = scanner.nextLine().trim();

            service.updateWiseSaying(updateId, newContent, newAuthor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteWiseSaying(int removeId) {
        try {
            service.deleteWiseSaying(removeId);
            System.out.printf("%d번 명언이 삭제되었습니다.%n", removeId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        List<WiseSaying> wiseSayingList = service.findWiseSayingList();
        wiseSayingList.forEach(System.out::println);
    }

    public void build() {
        service.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
