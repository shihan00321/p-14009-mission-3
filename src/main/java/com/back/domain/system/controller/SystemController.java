package com.back.domain.system.controller;

import java.util.Scanner;

public class SystemController {
    private final Scanner scanner;

    public SystemController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exit() {
        scanner.close();
        System.out.println("프로그램을 종료합니다.");
    }
}
