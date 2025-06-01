package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;
import java.util.Scanner;

public class App {

    private final Scanner scanner = new Scanner(System.in);
    private final WiseSayingController wiseSayingController = new WiseSayingController(scanner);
    private final SystemController systemController = new SystemController(scanner);

    public void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();
            CommandManager manager = new CommandManager(cmd);
            switch (manager.getActionName()) {
                case "종료" -> {
                    systemController.exit();
                    return;
                }
                case "등록" -> wiseSayingController.registerWiseSaying();
                case "목록" -> wiseSayingController.showList();
                case "삭제" -> wiseSayingController.deleteWiseSaying(manager.getParamAsInt("id", -1));
                case "수정" -> wiseSayingController.updateWiseSaying(manager.getParamAsInt("id", -1));
                case "빌드" -> wiseSayingController.build();
                default -> System.out.println("등록되지 않은 명령어입니다.");
            }
        }
    }
}