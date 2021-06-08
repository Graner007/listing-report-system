package com.codecool.report.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner scanner = new Scanner(System.in);
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_RESET = "\u001B[0m";

    public static int getIntInput() {
        while (true) {
            try {
                System.out.print("Hello");
                int num = scanner.nextInt();
                int menuOptionNumber = ViewMenu.getMenuOptionsSize();
                if (num <= menuOptionNumber && num > 0)
                    return num;
                System.out.println(TEXT_RED + "Input number must be range of 1 and " + menuOptionNumber + "!" + TEXT_RESET);
                continue;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(TEXT_RED + "Input must be a number!" + TEXT_RESET);
                continue;
            }
        }
    }
}
